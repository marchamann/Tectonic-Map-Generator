package ca.hamann.mapgen.gui.genscreen.mapactions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ca.hamann.mapgen.gui.MapProcessor;
import ca.hamann.mapgen.gui.genscreen.GeneratorScreen;
import ca.hamann.mapgen.gui.genscreen.MapProcess;

public abstract class MapAction implements ActionListener {

	protected GeneratorScreen screen;

	public MapAction(GeneratorScreen screen) {
		this.screen = screen;
	}

	public void actionPerformed(ActionEvent e) {
		screen.setEnabledControls(false);

		MapProcess process = screen.getNewMapProcess();
		process.setProcessor(getNewProcessor());
		process.setIterations(getIterations());
		process.start();
	}

	public abstract int getIterations();

	public abstract MapProcessor getNewProcessor();
}
