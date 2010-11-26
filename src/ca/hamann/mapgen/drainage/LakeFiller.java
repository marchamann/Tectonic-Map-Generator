package ca.hamann.mapgen.drainage;

import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.containers.LocationList;
import ca.hamann.mapgen.neighbours.ElevationComparator;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class LakeFiller {
  private DrainageProcessor processor;
  private LocationList orderedDepressions;
  private ElevationComparator comparator;

  public LakeFiller(
    DrainageProcessor processor,
    LocationCollection depressions) {
    this.processor = processor;
    orderedDepressions = order(depressions);

    comparator = processor.getElevationComparator();
  }

  public SinusoidalLocation removeNextLowestLocation() {
    return orderedDepressions.remove(0);
  }

  public void fillLakeWithBottom(SinusoidalLocation bottom) {
    ClosureDivider divider = new ClosureDivider(processor);

    orderedDepressions.remove(bottom);

    divider.divideClosure(bottom, orderedDepressions);
    LocationCollection closure = divider.getClosure();

    SinusoidalLocation drain = divider.getLowestBoundaryDrain(closure);

    processor.addDrainageArrow(bottom, drain);
    if (!processor.hasTransitiveTerminus(drain)) {
      throw new RuntimeException("Drain: " + drain);
    }

    //    System.out.println(bottom + " : " + processor.getDrainageLocation(bottom));

    LocationIterator iterator = closure.iterator();
    while (iterator.hasNext()) {
      SinusoidalLocation next = iterator.next();

      if (comparator.isLowerThan(next, drain)) {
        int difference =
          processor.getElevation(drain) - processor.getElevation(next);

        processor.setAccummulatedWater(next, difference);
      }
    }

    orderedDepressions = order(divider.getRemaining());
  }

  private LocationList order(LocationCollection collection) {
    return collection.getOrderedLocations(processor.getElevationComparator());
  }

  public void fillLakes() {
    while (orderedDepressions.size() > 0) {
      fillLakeWithBottom(removeNextLowestLocation());
    }
  }

}
