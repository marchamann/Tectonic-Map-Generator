package ca.hamann.mapgen.drainage.filters;

import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.containers.traversal.AbstractLocationFilter;
import ca.hamann.mapgen.drainage.DrainageProcessor;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class ClosureFilter extends AbstractLocationFilter {

  private DrainageProcessor processor;

  private LocationCollection currentClosure;

  public ClosureFilter(
    LocationCollection currentClosure,
    DrainageProcessor processor) {
    this.currentClosure = currentClosure;
    this.processor = processor;
  }

  public boolean filterLocation(SinusoidalLocation loc) {
    SinusoidalLocation drain = getLowestNeighbourWithDrainage(loc);
    boolean result = (drain != null);

    if (result) {
      processor.addDrainageArrow(loc, drain);
//      if (!processor.hasTransitiveTerminus(loc)) {
//        System.out.println("From: " + loc + " To: " + drain);
//      }
    }

    return result;
  }

  private SinusoidalLocation getLowestNeighbourWithDrainage(SinusoidalLocation loc) {
    LocationCollection neighbours = processor.getNotHigherNeighbours(loc);

    LocationIterator iterator = neighbours.iterator();

    while (iterator.hasNext()) {
      SinusoidalLocation neighbour = iterator.next();
      if (currentClosure.contains(neighbour)) {
        return neighbour;
      }
    }
    return null;
  }
}
