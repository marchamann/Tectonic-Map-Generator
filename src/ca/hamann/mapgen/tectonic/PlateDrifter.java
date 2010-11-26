package ca.hamann.mapgen.tectonic;

import java.util.Iterator;

import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.direction.DirectionSequence;
import ca.hamann.mapgen.direction.MapDirection;
import ca.hamann.mapgen.gui.MapProcessor;
import ca.hamann.mapgen.gui.genscreen.GeneratorScreen;
import ca.hamann.mapgen.gui.genscreen.MapProcess;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class PlateDrifter implements MapProcessor {

	private MapConfiguration config;

	protected TectonicMap fromMap, toMap;
	protected SinusoidalGrid grid;

	public PlateDrifter(TectonicMap fromMap) {
		this.fromMap = fromMap;
		config = fromMap.getConfiguration();
		grid = fromMap.getGrid();
		initializeNewToMap();
	}

	public int moveTectonicLocation(SinusoidalLocation fromLoc,
			SinusoidalLocation toLoc) {

		TectonicValues fromValues = fromMap.getTectonicValues(fromLoc);
		TectonicValues toValues = toMap.getTectonicValues(toLoc);
		TectonicValues newValues = fromValues;

		int toPlate = toValues.getPlateIndex();
		int fromPlate = fromValues.getPlateIndex();
		int collisionIncrement = 0;

		if (toPlate == 0 || toPlate == fromPlate) {
			// do nothing extra
		} else {
			collisionIncrement = config.getCollisionIncrement();
			if (toPlate == fromMap.lessDensePlate(fromPlate, toPlate)) {
				newValues = toValues;
			}
		}

		int oldCollisions = newValues.getCollisions();
		int newCollisions = oldCollisions + collisionIncrement;

		newValues.setCollisions(newCollisions);

		int newElevation = newValues.getElevation() + collisionIncrement;
		newValues.setElevation(newElevation);

		toMap.setTectonicValues(toLoc, newValues);

		return fromPlate;

	}

	public int getPlateIndex(SinusoidalLocation loc) {
		return toMap.getPlateIndex(loc);
	}

	public int getCollisions(SinusoidalLocation loc) {
		return toMap.getCollisions(loc);
	}

	public void initializeNewToMap() {
		toMap = new TectonicMap(fromMap.getConfiguration());
		toMap.setPlates(fromMap.getPlates());
		toMap.setMover(fromMap.getMover());
	}

	public void setDirectionSequence(DirectionSequence sequence, int plateIndex) {
		fromMap.setDirectionSequence(sequence, plateIndex);
	}

	public void driftLocation(SinusoidalLocation loc) {
		int plateIndex = fromMap.getPlateIndex(loc);

		MapDirection direction = getCurrentDirection(plateIndex);

		moveTectonicLocation(loc, direction.move(grid.getMover(), loc));

	}

	protected MapDirection getCurrentDirection(int plateIndex) {
		if (plateIndex == 0) {
			return MapDirection.IDENTITY;
		}
		DirectionSequence sequence = getDirectionSequence(plateIndex);
		return sequence.getCurrentDirection();
	}

	private DirectionSequence getDirectionSequence(int plateIndex) {
		if (plateIndex == -1) {
			return new DirectionSequence();
		}
		return fromMap.getDirectionSequence(plateIndex);
	}

	public void setCollisions(SinusoidalLocation loc, int collisions) {
		toMap.setCollisions(loc, collisions);
	}

	public void makeNewMapOld() {
		fromMap = toMap;
		initializeNewToMap();
	}

	public void setPlateIndex(SinusoidalLocation loc, int plateIndex) {
		toMap.setPlateIndex(loc, plateIndex);
	}

	public TectonicMap driftMap() {

		LattitudeDrifter lattDrifter = new LattitudeDrifter(this);

		int maxY = grid.getMaxY();

		for (int i = maxY; i >= -maxY; i--) {
			lattDrifter.moveLattitude(i);
		}

		fillEmptyLocations();

		Eroder eroder = new Eroder(toMap);

		toMap = eroder.erodeMap();

		splitPlatesIfNecessary(toMap);

		return toMap;
	}

	private void splitPlatesIfNecessary(TectonicMap tectMap) {
		TectonicPlates plates = tectMap.getPlates();
		Iterator iterator = plates.iterator();
		while (iterator.hasNext()) {
			Plate plate = (Plate) iterator.next();
			if (plate.getCount() == 0) {
				PlateSplitter splitter = new PlateSplitter(tectMap);
				splitter.splitPlate(plates.getBiggestPlate(), plate.getIndex());
				// System.out.println("Splitting plate: " +
				// plates.getBiggestPlate());
			}

		}

	}

	public void fillEmptyLocations() {
		EmptyLocationFiller filler = new EmptyLocationFiller(toMap);
		filler.fillEmptyLocations();
	}

	public TectonicMap processMap() {
		return driftMap();
	}

	public void afterProcess(GeneratorScreen screen) {
		makeNewMapOld();
	}

	public String getProcessName() {
		return "Drifting";
	}

	public void setProcess(MapProcess process) {
	}

	public TectonicMap getToMap() {
		return toMap;
	}

}
