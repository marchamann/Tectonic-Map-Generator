package ca.hamann.mapgen.tectonic;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.containers.LocationList;
import ca.hamann.mapgen.gui.genscreen.MapProcess;
import ca.hamann.mapgen.neighbours.TectonicNeighbourhoods;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class PlateSpreader {

	private MapConfiguration config;
	private MapProcess process;
	private TectonicMap tectMap;
	private TectonicNeighbourhoods neighbourhoods;
	private Map<Integer, LocationList> edgeSets;
	private Random floodFillRandom;
	private boolean isLazy = false;

	public PlateSpreader(TectonicMap tectMap) {
		this.tectMap = tectMap;
		config = tectMap.getConfiguration();
		neighbourhoods = tectMap.getNeighbourhoods();
		initializeEdgeSets();
		floodFillRandom = config.getRandom();
	}

	private void initializeEdgeSets() {
		int plateCount = config.getPlateCount();

		edgeSets = new TreeMap<Integer, LocationList>();

		for (int i = 1; i < plateCount; i++) {
			edgeSets.put(new Integer(i), new LocationList());
		}

	}

	public LocationList getNeighbourSet(int plateIndex) {
		Integer index = new Integer(plateIndex);
		LocationList result = edgeSets.get(index);

		if (result == null) {
			result = new LocationList();
			edgeSets.put(index, result);
		}

		return result;
	}

	public SinusoidalLocation spreadPlateByOne(int plateIndex) {
		LocationList neighbourSet = getNeighbourSet(plateIndex);

		if (neighbourSet.isEmpty()) {
			return null;
		}

		SinusoidalLocation loc = getNextRandomNeighbour(neighbourSet);

		if (isLocationFree(loc)) {
			addLocationToPlate(loc, plateIndex);
			addNeighboursToSet(neighbourSet, loc);
			return loc;
		}

		if (isLazy) {
			return loc;
		}
		return spreadPlateByOne(plateIndex);

	}

	private SinusoidalLocation getNextRandomNeighbour(LocationList neighbourSet) {
		int nextNeighbour = 0;
		if (floodFillRandom != null) {
			nextNeighbour = floodFillRandom.nextInt(neighbourSet.size());
		}
		SinusoidalLocation loc = neighbourSet.remove(nextNeighbour);
		return loc;
	}

	private void addLocationToPlate(SinusoidalLocation loc, int plateIndex) {
		int savedPlate = plateIndex;
		if (savedPlate == 0) {
			savedPlate = -1;
		}
		tectMap.setPlateIndex(loc, savedPlate);
	}

	private void addNeighboursToSet(LocationList neighbourSet,
			SinusoidalLocation loc) {
		LocationCollection neighbours = neighbourhoods.getNeighbours(loc);
		LocationIterator iterator = neighbours.iterator();
		while (iterator.hasNext()) {
			SinusoidalLocation next = iterator.next();
			if (isLocationFree(next)) {
				neighbourSet.add(next);
			}
		}
	}

	private boolean isLocationFree(SinusoidalLocation loc) {
		return tectMap.getPlateIndex(loc) == 0;
	}

	public TectonicMap generatePlates() {
		String plateCreationMethod = config.getPlateCreationMethod();

		if ("Flood Fill".equals(plateCreationMethod)) {
			floodFillPlates();
		} else if ("Plate Splitting".equals(plateCreationMethod)) {
			splitPlates();
		}

		return tectMap;
	}

	private void splitPlates() {
		int plateCount = 1;
		PlateSplitter splitter = new PlateSplitter(tectMap);
		splitter.setProcess(process);
		while (plateCount < config.getPlateCount()) {

			int randomPlate = floodFillRandom.nextInt(plateCount) + 1;
			splitter.splitPlate(randomPlate, plateCount + 1);
			plateCount++;
		}

		tectMap.generateBaseElevations();
	}

	public void floodFillPlates() {
		Set<Integer> keys = edgeSets.keySet();
		boolean done = false;
		int count = 0;
		while (!done) {
			done = true;

			Iterator<Integer> iterator = keys.iterator();

			while (iterator.hasNext()) {
				Integer integer = iterator.next();
				SinusoidalLocation loc = spreadPlateByOne(integer.intValue());

				if (loc != null) {
					done = false;
				}
			}
			if (count++ % 100 == 0) {
				updateGui();
			}
		}
	}

	protected void updateGui() {
		if (process != null) {
			process.setTectonicMap(tectMap);
			process.updateGui();
		}
	}

	public void setProcess(MapProcess process) {
		this.process = process;
	}

	public void setEdgeSets(Map<Integer, LocationList> map) {
		edgeSets = map;
	}

	public void setLazy(boolean isLazy) {
		this.isLazy = isLazy;
	}

}
