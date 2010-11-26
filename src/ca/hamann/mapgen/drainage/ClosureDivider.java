package ca.hamann.mapgen.drainage;

import java.util.Random;

import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.containers.LocationList;
import ca.hamann.mapgen.containers.LocationSet;
import ca.hamann.mapgen.containers.traversal.LocationContainerTraversal;
import ca.hamann.mapgen.drainage.filters.BoundaryFilter;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class ClosureDivider {

  private Random random = new Random(0);
  private DrainageProcessor processor;
  private LocationCollection closure, remaining;

  public ClosureDivider(DrainageProcessor processor) {
    this.processor = processor;
    closure = new LocationSet();
  }

  public void divideClosureStep(
    LocationList currentClosure,
    LocationCollection remaining) {

    SinusoidalLocation currentDrain =
      currentClosure.remove(random.nextInt(currentClosure.size()));

    closure.add(currentDrain);

    LocationCollection neighbours = processor.getNeighbours(currentDrain);
    //processor.getNotLowerNeighbours(currentDrain);

    LocationIterator iterator = neighbours.iterator();

    while (iterator.hasNext()) {
      SinusoidalLocation next = iterator.next();
      if (remaining.contains(next)) {
        processor.addDrainageArrow(next, currentDrain);
        if (!processor.hasTransitiveTerminus(next)) {
          throw new RuntimeException("From: " + next + " To: " + currentDrain);
        }
        currentClosure.add(next);
        remaining.remove(next);
      }
    }

    //    LocationCollection result = new LocationSet();
    //    result.addAll(currentClosure);
    //
    //    DrainagePartition partition = new DrainagePartition(remaining);
    //
    //    partition.partition(new ClosureFilter(currentClosure, processor));
    //
    //    result.addAll(partition.getDrainedLand());
    //
    //    this.remaining = partition.getUndrainedLand();

  }

  public void divideClosure(
    SinusoidalLocation loc,
    LocationCollection depression) {
    closure = new LocationSet();
    LocationList current = new LocationList();

    current.add(loc);

    remaining = depression;
    remaining.remove(loc);

    while (!current.isEmpty()) {
      divideClosureStep(current, remaining);
    }

  }

  public LocationCollection getClosure() {
    return closure;
  }

  public LocationCollection getRemaining() {
    return remaining;
  }

  public LocationSet getBoundarySet(LocationCollection collection) {
    LocationSet result = new LocationSet();

    LocationIterator iterator = collection.iterator();

    while (iterator.hasNext()) {
      SinusoidalLocation next = iterator.next();

      LocationContainerTraversal traversal =
        processor.getNeighboursTraversal(next);

      LocationCollection boundary =
        traversal.filter(new BoundaryFilter(collection));

      result.addAll(boundary);
    }

    return result;
  }

  public SinusoidalLocation getLowestBoundaryDrain(LocationCollection collection) {
    LocationList orderedBoundary =
      getBoundarySet(collection).getOrderedLocations(
        processor.getElevationComparator());
    //    if (orderedBoundary.isEmpty()) {
    //      collection.printOut();
    //      int i = 0;
    //    }

    SinusoidalLocation result = orderedBoundary.get(0);
    if (!processor.getSeaDrainedLand().contains(result)) {
      System.out.println(result);
    }

    return result;
  }

}
