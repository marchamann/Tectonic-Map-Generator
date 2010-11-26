package ca.hamann.mapgen.tectonic;

import java.util.Iterator;

import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.direction.DirectionSequence;
import ca.hamann.mapgen.neighbours.TectonicNeighbourhoods;
import ca.hamann.mapgen.sinusoidal.BasicMover;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.sinusoidal.SinusoidalMapStorage;
import ca.hamann.mapgen.sinusoidal.SynchronousLongitudeMover;

public class TectonicMap {

	private MapConfiguration config;

	private SinusoidalMapStorage plateMap, collisionMap, elevationMap,
			accummulatedWaterMap, flowMap;

	private TectonicPlates plates;

	private SynchronousLongitudeMover mover;

	public TectonicMap(MapConfiguration config) {
		this.config = config;
		SinusoidalGrid grid = config.getGrid();
		plateMap = new SinusoidalMapStorage(grid);
		collisionMap = new SinusoidalMapStorage(grid);
		elevationMap = new SinusoidalMapStorage(grid);
		accummulatedWaterMap = new SinusoidalMapStorage(grid);
		flowMap = new SinusoidalMapStorage(grid);

		plates = new TectonicPlates(config.getPlateCount());
		mover = new SynchronousLongitudeMover(grid);
	}

	public int getCollisions(SinusoidalLocation loc) {
		return collisionMap.getValue(loc);
	}

	public int getPlateIndex(SinusoidalLocation loc) {
		return plateMap.getValue(loc);
	}

	public void setPlateIndex(SinusoidalLocation loc, int plateIndex) {
		int currentValue = plateMap.getValue(loc);
		if (currentValue > 0) {
			plates.decrementCountOfPlate(currentValue);
		}
		plates.incrementCountOfPlate(plateIndex);
		plateMap.putValue(loc, plateIndex);
	}

	public void setCollisions(SinusoidalLocation loc, int collisions) {
		collisionMap.putValue(loc, collisions);
	}

	public int getElevation(SinusoidalLocation loc) {
		return elevationMap.getValue(loc);
	}

	public void setElevation(SinusoidalLocation loc, int elevation) {
		if (elevation < 0) {
			elevation = 0;
		}
		elevationMap.putValue(loc, elevation);
	}

	public SinusoidalGrid getGrid() {
		return config.getGrid();
	}

	public int countFilledPlateLocations() {
		return plateMap.nonZeroCount();
	}

	public int getBaseLandElevation() {
		return config.getBaseLandElevation();
	}

	public int getBaseSeaElevation() {
		return config.getBaseSeaElevation();
	}

	public void setBaseLandElevation(int i) {
		config.setBaseLandElevation(i);
	}

	public void setBaseSeaElevation(int i) {
		config.setBaseSeaElevation(i);
	}

	public void setBaseElevation(SinusoidalLocation loc) {
		int plateIndex = getPlateIndex(loc);
		int baseElevation = getBaseSeaElevation();

		if (plates.isLandPlate(plateIndex)) {
			baseElevation = getBaseLandElevation();
		}

		setElevation(loc, baseElevation + getCollisions(loc));
	}

	public void generateBaseElevations() {
		LocationIterator iterator = getGrid().iterator();

		while (iterator.hasNext()) {
			setBaseElevation(iterator.next());
		}
	}

	public SinusoidalLocation moveNorth(SinusoidalLocation loc) {
		return mover.moveNorth(loc);
	}

	public SinusoidalLocation moveSouth(SinusoidalLocation loc) {
		return mover.moveSouth(loc);
	}

	public TectonicPlates getPlates() {
		return plates;
	}

	public void setPlates(TectonicPlates plates) {
		this.plates = plates;
		Iterator iterator = plates.iterator();

		while (iterator.hasNext()) {
			Plate plate = (Plate) iterator.next();
			plate.setCount(0);
		}
	}

	public TectonicValues getTectonicValues(SinusoidalLocation loc) {
		TectonicValues result = new TectonicValues();

		result.setPlateIndex(getPlateIndex(loc));
		result.setCollisions(getCollisions(loc));
		result.setElevation(getElevation(loc));

		return result;
	}

	public void setTectonicValues(SinusoidalLocation loc, TectonicValues values) {

		setPlateIndex(loc, values.getPlateIndex());
		setCollisions(loc, values.getCollisions());
		setElevation(loc, values.getElevation());
	}

	public int lessDensePlate(int fromPlate, int toPlate) {
		return getPlates().lessDensePlate(fromPlate, toPlate);
	}

	public void setDirectionSequence(DirectionSequence sequence, int plateIndex) {
		getPlates().setDirectionSequence(sequence, plateIndex);
	}

	public DirectionSequence getDirectionSequence(int plateIndex) {
		return getPlates().getDirectionSequence(plateIndex);
	}

	public MapConfiguration getConfiguration() {
		return config;
	}

	public int getAccummulatedWater(SinusoidalLocation loc) {
		return accummulatedWaterMap.getValue(loc);
	}

	public void setAccummulatedWater(SinusoidalLocation loc, int value) {
		accummulatedWaterMap.putValue(loc, value);
	}

	public int getFlow(SinusoidalLocation loc) {
		return flowMap.getValue(loc);
	}

	public void setFlow(SinusoidalLocation loc, int value) {
		flowMap.putValue(loc, value);
	}

	public SynchronousLongitudeMover getMover() {
		return mover;
	}

	public void setMover(SynchronousLongitudeMover mover) {
		this.mover = mover;
	}

	public BasicMover getBasicMover() {
		return config.getGrid().getMover();
	}

	public TectonicNeighbourhoods getNeighbourhoods() {
		return new TectonicNeighbourhoods(this);
	}

	public int getSeaLevel() {
		return config.getSeaLevel();
	}

}
