package ca.hamann.mapgen.gui.genscreen;

import javax.swing.SwingUtilities;

import ca.hamann.mapgen.gui.MapProcessor;
import ca.hamann.mapgen.gui.SwingWorker;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class MapProcess extends SwingWorker {
	private GeneratorScreen screen;
	private MapProcessor processor;
	private int iterations;

	private int progressCount;

	private TectonicMap tectMap;

	public MapProcess(GeneratorScreen screen) {
		super();
		this.screen = screen;
	}

	public Object construct() {
		updateGui();
		for (int i = 1; i <= iterations; i++) {
			tectMap = processor.processMap();
			processor.afterProcess(screen);

			progressCount = i;

			updateGui();
		}
		return null;
	}

	public void updateGui() {
		try {
			SwingUtilities.invokeAndWait(refreshView());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Runnable refreshView() {
		return new Runnable() {
			public void run() {
				if (tectMap != null) {
					screen.setTectonicMap(tectMap);
					screen.setCurrentColourerMap(tectMap);
					screen.getCurrentProjection().setGrid(tectMap.getGrid());
				}
				screen.setProgressText(processor.getProcessName()
						+ progressString());
				screen.updateImage();
			}

		};
	}

	private String progressString() {
		String string = "";
		if (iterations > 1) {
			string = ": " + progressCount;
		}
		return string;
	}

	public void setIterations(int i) {
		iterations = i;
	}

	public void finished() {
		screen.setProgressText("Done!");
		screen.setEnabledControls(true);
	}

	public void setProcessor(MapProcessor processor) {
		this.processor = processor;
		processor.setProcess(this);
	}

	public void setTectonicMap(TectonicMap tectMap) {
		this.tectMap = tectMap;
	}

}
