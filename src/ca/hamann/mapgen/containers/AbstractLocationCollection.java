package ca.hamann.mapgen.containers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public abstract class AbstractLocationCollection implements LocationCollection {

	protected Collection<SinusoidalLocation> locations;

	public AbstractLocationCollection(Collection<SinusoidalLocation> collection) {
		locations = collection;
	}

	public void addAll(LocationCollection locations) {
		LocationIterator iterator = locations.iterator();

		while (iterator.hasNext()) {
			add(iterator.next());
		}
	}

	public LocationIterator iterator() {
		return new LocationCollectionIterator(locations.iterator());
	}

	public boolean isEmpty() {
		return locations.isEmpty();
	}

	public int size() {
		return locations.size();
	}

	public boolean add(SinusoidalLocation location) {
		return locations.add(location);
	}

	public SinusoidalLocation[] toArray() {
		return (SinusoidalLocation[]) locations.toArray();
	}

	public boolean contains(SinusoidalLocation loc) {
		return locations.contains(loc);
	}

	public boolean remove(SinusoidalLocation loc) {
		return locations.remove(loc);
	}

	public void printOut() {
		LocationIterator iterator = iterator();

		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	public LocationList getOrderedLocations(
			Comparator<SinusoidalLocation> comparator) {
		LocationList result = new LocationList();

		SinusoidalLocation[] nLocs = locations
				.toArray(new SinusoidalLocation[] {});

		Arrays.sort(nLocs, comparator);

		for (int i = 0; i < nLocs.length; i++) {
			result.add((SinusoidalLocation) nLocs[i]);
		}

		return result;
	}

}
