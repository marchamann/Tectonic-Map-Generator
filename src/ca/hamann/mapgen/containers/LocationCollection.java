package ca.hamann.mapgen.containers;

import java.util.Comparator;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public interface LocationCollection extends LocationContainer {
  public boolean add(SinusoidalLocation loc);
  public void addAll(LocationCollection locations);
  public Object[] toArray();
  public boolean remove(SinusoidalLocation loc);
  public void printOut();
  public LocationList getOrderedLocations(Comparator comparator);
}
