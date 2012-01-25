package ca.hamann.mapgen.gui.genscreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import ca.hamann.mapgen.persistence.load.TectonicMapReader;
import ca.hamann.mapgen.persistence.save.TectonicMapWriter;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class FileMenu extends JMenu {

	private GeneratorScreen screen;

	private JMenuItem newMap;
	private JMenuItem saveImage;
	private JMenuItem saveMap;
	private JMenuItem loadMap;

	public FileMenu(GeneratorScreen screen) {
		super("File");
		this.screen = screen;
		initItems();
		initLoadSave();
	}

	private void initItems() {

		newMap = new JMenuItem("New Map");

		newMap.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				NewMapDialog newMapDialog = new NewMapDialog(screen,
						"Configure...", false);

				newMapDialog.setVisible(true);
			}
		});

		saveImage = new JMenuItem("Save Image");

		saveImage.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				SaveImageDialog saveDialog = new SaveImageDialog(screen,
						"Save Image as..", true);

				saveDialog.setVisible(true);

				boolean accepted = saveDialog.getAccepted();

				String filename = saveDialog.getFilename();
				String imageType = saveDialog.getImageType();

				if (accepted) {

					String extension = "." + imageType;

					if (!filename.endsWith(extension)) {
						filename = filename + extension;
					}

					File file = new File(filename);

					try {
						ImageIO.write(screen.getImage(), imageType, file);
					} catch (IOException ioe) {
						throw new RuntimeException(ioe);
					}
				}

			}
		});

		add(newMap);
		add(saveImage);

	}

	public void initLoadSave() {
		saveMap = new JMenuItem("Save Map");
		saveMap.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser("Save Map as..");

				int result = chooser.showSaveDialog(screen);

				if (result == JFileChooser.APPROVE_OPTION) {

					File file = chooser.getSelectedFile();

					String filename = file.getAbsolutePath();
					String extension = ".map";

					if (!filename.endsWith(extension)) {
						filename = filename + extension;
						file = new File(filename);
					}

					FileWriter fw = null;

					try {
						screen.setProgressText("Saving...");
						fw = new FileWriter(file);

						TectonicMap tectMap = screen.getTectonicMap();
						TectonicMapWriter writer = new TectonicMapWriter();
						writer.write(fw, tectMap);

						fw.close();
						screen.setProgressText("Saving... done.");
					} catch (IOException ioe) {
						throw new RuntimeException(ioe);
					}

				}

			}
		});

		loadMap = new JMenuItem("Load Map");
		loadMap.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser("Load map..");

				int result = chooser.showOpenDialog(screen);

				if (result == JFileChooser.APPROVE_OPTION) {

					File file = chooser.getSelectedFile();

					String filename = file.getAbsolutePath();
					String extension = ".map";

					if (!filename.endsWith(extension)) {
						filename = filename + extension;
						file = new File(filename);
					}

					FileReader fr = null;

					try {
						screen.setProgressText("Loading...");
						fr = new FileReader(file);

						TectonicMapReader reader = new TectonicMapReader();
						System.out.println("Loading...");
						TectonicMap map = reader.read(fr);
						screen.setTectonicMap(map);
						screen.setCurrentColourerMap(map);
						System.out.println("...done");
						fr.close();
						screen.updateImage();
						screen.setProgressText("Loading...done.");
					} catch (IOException ioe) {
						throw new RuntimeException(ioe);
					}

				}

			}
		});

		add(saveMap);
		add(loadMap);
	}

}
