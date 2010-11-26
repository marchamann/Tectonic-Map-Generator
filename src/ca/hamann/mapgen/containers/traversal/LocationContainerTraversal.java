package ca.hamann.mapgen.containers.traversal;

import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.containers.LocationContainer;
import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.containers.LocationList;
import ca.hamann.mapgen.containers.LocationSet;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class LocationContainerTraversal {

  private LocationContainer container;

  private boolean receivedBreak;

  private LocationCollection rejects;

  private int counter = 0;

  public LocationContainerTraversal(LocationContainer container) {
    this.container = container;
  }

  public void traverse(LocationVisitor visitor) {
    visitor.setTraversal(this);
    LocationIterator iterator = container.iterator();
    while (iterator.hasNext() && !receivedBreak) {
      visitor.visitLocation(iterator.next());
      counter++;
      if (counter == 5000) {
        counter = 0;
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  public LocationCollection filter(LocationFilter filter) {
    LocationCollection result = new LocationSet();
    rejects = new LocationList();

    filter.setTraversal(this);
    LocationIterator iterator = container.iterator();
    while (iterator.hasNext() && !receivedBreak) {
      SinusoidalLocation next = iterator.next();
      if (filter.filterLocation(next)) {
        result.add(next);
      } else {
        rejects.add(next);
      }
    }

    return result;
  }

  public SinusoidalLocation select(LocationFilter filter) {
    filter.setTraversal(this);
    LocationIterator iterator = container.iterator();
    while (iterator.hasNext() && !receivedBreak) {
      SinusoidalLocation next = iterator.next();
      if (filter.filterLocation(next)) {
        return next;
      }
    }

    return null;
  }

  public void signalBreak() {
    receivedBreak = true;
  }

  public LocationCollection getRejects() {
    return rejects;
  }

}
