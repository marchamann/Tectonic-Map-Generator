package ca.hamann.mapgen.containers;

import java.util.Iterator;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class LocationCollectionIterator implements LocationIterator {

  private Iterator iterator;

  public LocationCollectionIterator(Iterator iterator) {
    this.iterator = iterator;
  }

  public boolean hasNext() {
    return iterator.hasNext();
  }

  public SinusoidalLocation next() {
    return (SinusoidalLocation) iterator.next();
  }

}
