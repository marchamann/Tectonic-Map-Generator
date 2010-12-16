package ca.hamann.mapgen.gui.genscreen.mapactions;

import ca.hamann.mapgen.gui.MapProcessor;
import ca.hamann.mapgen.gui.genscreen.GeneratorScreen;

public class DriftAction extends MapAction {

	public DriftAction(GeneratorScreen screen) {
		super(screen);
	}

	@Override
	public int getIterations() {
		return screen.getIterations();
	}

	@Override
	public MapProcessor getNewProcessor() {
		return screen.getDrifter();
	}

}
