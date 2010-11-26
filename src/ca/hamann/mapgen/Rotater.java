package ca.hamann.mapgen;

import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.gui.MapProcessor;
import ca.hamann.mapgen.gui.genscreen.GeneratorScreen;
import ca.hamann.mapgen.gui.genscreen.MapProcess;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class Rotater implements MapProcessor {

	private boolean east;
	private TectonicMap fromMap;

	public Rotater(TectonicMap tectMap, boolean east) {
		this.fromMap = tectMap;
		this.east = east;
	}

	public TectonicMap processMap() {
		SinusoidalGrid baseMap = fromMap.getGrid();
		TectonicMap toMap = new TectonicMap(fromMap.getConfiguration());
		toMap.setPlates(fromMap.getPlates());

		LocationIterator iterator = baseMap.iterator();

		while (iterator.hasNext()) {
			SinusoidalLocation fromLoc, toLoc;
			fromLoc = iterator.next();

			int spear = fromLoc.getSpear();

			spear = baseMap.moveHorizontalOneSpear(spear, east);

			toLoc = baseMap.getLocation(spear, fromLoc.getY(), fromLoc.getX());

			toMap.setTectonicValues(toLoc, fromMap.getTectonicValues(fromLoc));
			toMap.setAccummulatedWater(toLoc,
					fromMap.getAccummulatedWater(fromLoc));
			toMap.setFlow(toLoc, fromMap.getFlow(fromLoc));
		}

		fromMap = toMap;

		return toMap;
	}

	public String getProcessName() {
		return "Rotating";
	}

	public void setProcess(MapProcess process) {
	}

	public void afterProcess(GeneratorScreen screen) {
	}

}
