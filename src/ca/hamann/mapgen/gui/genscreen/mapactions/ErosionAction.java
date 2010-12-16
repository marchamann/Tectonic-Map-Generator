package ca.hamann.mapgen.gui.genscreen.mapactions;

import ca.hamann.mapgen.gui.MapProcessor;
import ca.hamann.mapgen.gui.genscreen.GeneratorScreen;
import ca.hamann.mapgen.tectonic.Eroder;

public class ErosionAction extends MapAction {

	public ErosionAction(GeneratorScreen screen) {
		super(screen);
	}

	@Override
	public int getIterations() {
		return screen.getIterations();
	}

	@Override
	public MapProcessor getNewProcessor() {
		return new Eroder(screen.getTectonicMap());
	}

}
