package ca.hamann.mapgen.gui.genscreen.mapactions;

import ca.hamann.mapgen.Rotater;
import ca.hamann.mapgen.gui.MapProcessor;
import ca.hamann.mapgen.gui.genscreen.GeneratorScreen;

public class RotateAction extends MapAction {

	private boolean east;

	public RotateAction(GeneratorScreen screen, boolean east) {
		super(screen);
		this.east = east;
	}

	@Override
	public int getIterations() {
		return 1;
	}

	@Override
	public MapProcessor getNewProcessor() {
		return new Rotater(screen.getTectonicMap(), east);
	}

}
