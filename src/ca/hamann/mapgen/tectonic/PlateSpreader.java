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

	public boolean spreadPlateByOne(int plateIndex) {
		LocationList neighbourSet = getNeighbourSet(plateIndex);

		if (neighbourSet.isEmpty()) {
			return false;
		}

		int nextNeighbour = 0;
		if (floodFillRandom != null) {
			nextNeighbour = floodFillRandom.nextInt(neighbourSet.size());
		}
		SinusoidalLocation loc = neighbourSet.remove(nextNeighbour);

		if (isPlateLocationFree(loc)) {
			setPlateForLocation(loc, plateIndex);

			rememberNewNeighbours(neighbourSet, loc);

			return true;
		}

		return spreadPlateByOne(plateIndex);

	}

	private void rememberNewNeighbours(LocationList neighbourSet,
			SinusoidalLocation loc) {
		LocationCollection neighbours = neighbourhoods.getNeighbours(loc);
		LocationIterator iterator = neighbours.iterator();
		while (iterator.hasNext()) {
			SinusoidalLocation next = iterator.next();
			if (isPlateLocationFree(next)) {
				neighbourSet.add(next);
			}
		}
	}

	private void setPlateForLocation(SinusoidalLocation loc, int plateIndex) {
		int savedPlate = plateIndex;
		if (savedPlate == 0) {
			savedPlate = -1;
		}
		tectMap.setPlateIndex(loc, savedPlate);
	}

	private boolean isPlateLocationFree(SinusoidalLocation loc) {
		return tectMap.getPlateIndex(loc) == 0;
	}

	public TectonicMap floodFillPlates() {
		Set<Integer> keys = edgeSets.keySet();
		boolean done = false;
		int count = 0;
		while (!done) {
			done = true;

			Iterator<Integer> iterator = keys.iterator();

			while (iterator.hasNext()) {
				Integer integer = iterator.next();
				boolean foundFreeLocation = spreadPlateByOne(integer.intValue());

				if (foundFreeLocation) {
					done = false;
				}
			}
			if (count++ % 100 == 0) {
				updateGui();
			}
		}

		return tectMap;
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

}
