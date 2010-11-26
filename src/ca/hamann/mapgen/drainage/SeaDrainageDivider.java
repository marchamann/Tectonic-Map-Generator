package ca.hamann.mapgen.drainage;

import java.util.Random;

import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.containers.LocationList;
import ca.hamann.mapgen.containers.LocationSet;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class SeaDrainageDivider {

	private DrainageProcessor processor;

	private LocationCollection drained, undrained; // , tempDrained;

	private Random random = new Random(0);

	public SeaDrainageDivider(DrainageProcessor processor) {
		this.processor = processor;
	}

	public void divideDrainage(LocationList currentlyDrained,
			LocationCollection currentlyUndrained) {

		SinusoidalLocation currentDrain = currentlyDrained.remove(random
				.nextInt(currentlyDrained.size()));

		drained.add(currentDrain);

		LocationList neighbours = processor.getNotLowerNeighbours(currentDrain);

		LocationIterator iterator = neighbours.iterator();

		while (iterator.hasNext()) {
			SinusoidalLocation next = iterator.next();
			if (currentlyUndrained.contains(next)) {
				processor.addDrainageArrow(next, currentDrain);
				currentlyDrained.add(next);
				currentlyUndrained.remove(next);
			}
		}

		// drained = new LocationSet();
		// drained.addAll(currentlyDrained);
		//
		// DrainagePartition partition = new
		// DrainagePartition(currentlyUndrained);
		//
		// partition.partition(new HasDrainageNeighbourFilter(this));
		//
		// drained.addAll(partition.getDrainedLand());
		// undrained = partition.getUndrainedLand();
	}

	public SinusoidalLocation getDrainageNeighbour(SinusoidalLocation loc) {
		SinusoidalLocation neighbour = processor.getDrainageLocation(loc);

		if (neighbour != null) {
			return neighbour;
		}

		neighbour = getLowestNeighbourWithDrainage(loc);

		if (neighbour != null && processor.isNotHigherThan(neighbour, loc)) {
			processor.addDrainageArrow(loc, neighbour);
			return neighbour;
		}

		return null;
	}

	private SinusoidalLocation getLowestNeighbourWithDrainage(
			SinusoidalLocation loc) {
		LocationList neighbours = processor.getNotHigherNeighbours(loc);

		LocationIterator iterator = neighbours.iterator();

		while (iterator.hasNext()) {
			SinusoidalLocation neighbour = iterator.next();
			if (drained.contains(neighbour)) {
				return neighbour;
			}
		}
		return null;
	}

	public LocationCollection getDrained() {
		return drained;
	}

	public LocationCollection getUndrained() {
		return undrained;
	}

	public void divideDrainageUntilFixedPoint(LocationCollection coast,
			LocationCollection inland) {

		drained = new LocationSet();

		LocationList currentlyDrained = new LocationList();
		LocationCollection currentlyUndrained = new LocationSet();

		currentlyDrained.addAll(coast);
		currentlyUndrained.addAll(inland);

		while (!currentlyDrained.isEmpty()) {
			divideDrainage(currentlyDrained, currentlyUndrained);
		}

		undrained = currentlyUndrained;
		processor.setSeaDrainedLand(drained);
	}

}
