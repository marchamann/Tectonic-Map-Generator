package ca.hamann.mapgen.drainage.filters;

import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.containers.traversal.AbstractLocationFilter;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class BoundaryFilter extends AbstractLocationFilter {
  private LocationCollection collection;

  public BoundaryFilter(LocationCollection collection) {
    this.collection = collection;
  }

  public boolean filterLocation(SinusoidalLocation loc) {
    return !collection.contains(loc);
  }
}