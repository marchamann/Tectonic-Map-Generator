package ca.hamann.mapgen.gui.genscreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import ca.hamann.mapgen.containers.LocationIterator;
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
						fw = new FileWriter(file);

						TectonicMap tectMap = screen.getTectonicMap();
						LocationIterator iterator = screen.getGrid().iterator();
						while (iterator.hasNext()) {

							fw.write(Integer.toString(tectMap
									.getElevation(iterator.next())) + "\n");
						}

						fw.close();
					} catch (IOException ioe) {
						throw new RuntimeException(ioe);
					}

				}

			}
		});

		loadMap = new JMenuItem("Load Map");

		add(saveMap);
		add(loadMap);
	}

}
