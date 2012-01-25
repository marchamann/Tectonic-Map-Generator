package ca.hamann.mapgen.gui.genscreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.gui.colourers.LocationColourer;
import ca.hamann.mapgen.gui.colourers.PlateColourer;
import ca.hamann.mapgen.gui.colourers.RiversColourer;
import ca.hamann.mapgen.gui.genscreen.panels.ExecutionPanel;
import ca.hamann.mapgen.gui.projections.Projection;
import ca.hamann.mapgen.gui.projections.SinusoidalSeparatedProjection;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import ca.hamann.mapgen.tectonic.PlateDrifter;
import ca.hamann.mapgen.tectonic.PlateGenerator;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class GeneratorScreen extends JFrame {
	int iterations = 1;

	private Projection currentProjection;

	private JLabel imageHolder;
	private JLabel statusBar;

	private LocationColourer currentColourer;

	private TectonicMap tectMap;

	private MapConfiguration config = new MapConfiguration(48);

	private DisplayModesMenu displayModesMenu;
	private ExecutionPanel executionPanel;

	private BufferedImage image;

	public GeneratorScreen() throws HeadlessException {
		super("Tectonic Map Generator");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setCurrentColourer(new RiversColourer(tectMap));
		setCurrentProjection(new SinusoidalSeparatedProjection(config.getGrid()));

		init();

		initImage();

		pack();

		setLocationRelativeTo(null);
	}

	private void initImage() {
		SinusoidalGrid grid = config.getGrid();
		int width = grid.getWidth();
		int height = grid.getHeight();

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				image.setRGB(i, j, Color.LIGHT_GRAY.getRGB());
			}

		imageHolder.setIcon(new ImageIcon(image));
	}

	public void initMaps(MapConfiguration config) {
		PlateGenerator gen = new PlateGenerator(config);

		MapProcess plateProcess = new MapProcess(this);

		activatePlatesMode();

		plateProcess.setIterations(1);
		plateProcess.setProcessor(gen);
		plateProcess.start();
	}

	public PlateDrifter getDrifter() {
		return new PlateDrifter(tectMap);
	}

	public void updateImage() {

		if (tectMap != null) {
			image = tectMap.getGrid().convertToImage(getCurrentColourer(),
					getCurrentProjection());
		}

		imageHolder.setIcon(new ImageIcon(image));
		imageHolder.repaint();
	}

	public Projection getCurrentProjection() {
		return currentProjection;
	}

	private void init() {
		setJMenuBar(createMenuBar());

		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.add(createMainPanel(), "North");
		c.add(createStatusBarPanel(), "South");
	}

	private JPanel createMainPanel() {
		JPanel controls = initControls();

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		imageHolder = new JLabel();
		imageHolder.setHorizontalAlignment(JLabel.CENTER);

		mainPanel.add(imageHolder, "North");
		mainPanel.add(controls, "South");
		return mainPanel;
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		displayModesMenu = new DisplayModesMenu(this);

		menuBar.add(new FileMenu(this));
		menuBar.add(displayModesMenu);
		menuBar.add(new ProjectionsMenu(this));
		return menuBar;
	}

	private JPanel createStatusBarPanel() {
		JPanel statusBarPanel = new JPanel();
		statusBar = new JLabel("Started");
		statusBar.setHorizontalAlignment(JLabel.LEFT);
		Font font = statusBar.getFont().deriveFont(Font.PLAIN);
		statusBar.setFont(font);
		statusBarPanel.add(statusBar);
		statusBarPanel.setBorder(LineBorder.createGrayLineBorder());
		return statusBarPanel;
	}

	private JPanel initControls() {
		JPanel controls = new JPanel();

		executionPanel = new ExecutionPanel(this);
		executionPanel.setEnableControls(false);

		controls.add(executionPanel);

		return controls;
	}

	public void setCurrentColourer(LocationColourer currentColourer) {
		this.currentColourer = currentColourer;
	}

	private LocationColourer getCurrentColourer() {
			return currentColourer;
	}

	public void setCurrentColourerMap(TectonicMap tectMap) {
		currentColourer.setTectonicMap(tectMap);
	}

	public void setProgressText(String progressMessage) {
		statusBar.setText(progressMessage);
	}

	public void setTectonicMap(TectonicMap map) {
		tectMap = map;
	}

	public MapProcess getNewMapProcess() {
		return new MapProcess(this);
	}

	public TectonicMap getTectonicMap() {
		return tectMap;
	}

	public void setEnabledControls(boolean enabled) {
		executionPanel.setEnableControls(enabled);
	}

	public void setCurrentProjection(Projection projection) {
		currentProjection = projection;
	}

	protected SinusoidalGrid getGrid() {
		return config.getGrid();
	}

	public BufferedImage getImage() {
		return image;
	}

	public void activateRiverMode() {
		setCurrentColourer(new RiversColourer(tectMap));
		displayModesMenu.activateRiverMode();
	}

	public void activatePlatesMode() {
		setCurrentColourer(new PlateColourer(tectMap));
		displayModesMenu.activatePlateMode();
	}

	public MapConfiguration getMapConfiguration() {
		return config;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

}
