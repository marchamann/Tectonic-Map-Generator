package ca.hamann.mapgen.tectonic;

import java.util.Random;

import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.containers.LocationList;
import ca.hamann.mapgen.neighbours.Neighbourhoods;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class EmptyLocationFiller {
	private TectonicMap tectMap;
	private Neighbourhoods neighbourhoods;

	private Random fillEmptyRandom;

	private boolean riftValleyMaker = true;

	public EmptyLocationFiller(TectonicMap tectMap) {
		this.tectMap = tectMap;
		fillEmptyRandom = tectMap.getConfiguration().getRandom();
		neighbourhoods = tectMap.getGrid().getNeighbourhoods();
	}

	public void fillEmptyLocations() {
		fillEmptyLocations(getEmptyLocations());
	}

	public void fillEmptyLocations(LocationList emptyLocations) {
		while (!emptyLocations.isEmpty()) {
			SinusoidalLocation loc = emptyLocations.remove(0);
			if (fillEmptyLocation(loc) == false) {
				emptyLocations.add(loc);
			}
		}
	}

	public LocationList getEmptyLocations() {
		SinusoidalLocation loc;

		LocationIterator iterator = tectMap.getGrid().iterator();

		LocationList emptyLocations = new LocationList();

		while (iterator.hasNext()) {
			loc = iterator.next();
			if (tectMap.getPlateIndex(loc) == 0) {
				emptyLocations.add(loc);
			}
		}
		return emptyLocations;
	}

	public boolean fillEmptyLocation(SinusoidalLocation loc) {
		SinusoidalLocation neighbour = getNonZeroNeighbour(loc);

		if (neighbour != null) {
			tectMap.setPlateIndex(loc, tectMap.getPlateIndex(neighbour));

			if (riftValleyMaker) {
				makeRiftValley(loc, neighbour);
			}
			return true;
		}

		return false;

	}

	private void makeRiftValley(SinusoidalLocation loc,
			SinusoidalLocation neighbour) {
		int decrement = 1;
		tectMap.setCollisions(loc, -1);
		tectMap.setElevation(loc, tectMap.getElevation(neighbour) - decrement);
	}

	private SinusoidalLocation getNonZeroNeighbour(SinusoidalLocation loc) {
		LocationCollection neighbours = neighbourhoods.getNeighbours(loc);

		LocationIterator iterator = neighbours.iterator();

		int neighbourPlate = 0;
		SinusoidalLocation nextLoc = null;

		int max = fillEmptyRandom.nextInt(neighbours.size()) + 1;
		for (int i = 1; i <= max; i++) {
			nextLoc = iterator.next();
			neighbourPlate = tectMap.getPlateIndex(nextLoc);
		}

		if (neighbourPlate > 0) {
			return nextLoc;
		}

		return null;
	}

	public void setRiftValleyMaker(boolean b) {
		riftValleyMaker = b;
	}

}
