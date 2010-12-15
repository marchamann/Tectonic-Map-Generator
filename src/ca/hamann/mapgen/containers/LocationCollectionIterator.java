package ca.hamann.mapgen.containers;

import java.util.Iterator;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class LocationCollectionIterator implements LocationIterator {

	private Iterator<SinusoidalLocation> iterator;

	public LocationCollectionIterator(Iterator<SinusoidalLocation> iterator) {
		this.iterator = iterator;
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public SinusoidalLocation next() {
		return (SinusoidalLocation) iterator.next();
	}

}
