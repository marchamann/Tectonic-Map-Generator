package ca.hamann.mapgen.containers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public abstract class AbstractLocationCollection
  implements LocationCollection {

  protected Collection locations;

  public AbstractLocationCollection(Collection collection) {
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

  public Object[] toArray() {
    return locations.toArray();
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

  public LocationList getOrderedLocations(Comparator comparator) {
    LocationList result = new LocationList();

    Object[] nLocs = locations.toArray();

    Arrays.sort(nLocs, comparator);

    for (int i = 0; i < nLocs.length; i++) {
      result.add((SinusoidalLocation) nLocs[i]);
    }

    return result;
  }
  
  

}
