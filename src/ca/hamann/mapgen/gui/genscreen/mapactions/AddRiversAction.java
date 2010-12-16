package ca.hamann.mapgen.gui.genscreen.mapactions;

import ca.hamann.mapgen.drainage.DrainageProcessor;
import ca.hamann.mapgen.gui.MapProcessor;
import ca.hamann.mapgen.gui.genscreen.GeneratorScreen;

public class AddRiversAction extends MapAction {

	public AddRiversAction(GeneratorScreen screen) {
		super(screen);
	}

	@Override
	public int getIterations() {
		return screen.getIterations();
	}

	@Override
	public MapProcessor getNewProcessor() {
		return new DrainageProcessor(screen.getTectonicMap());
	}

}
