package ca.hamann.mapgen.containers.traversal;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public abstract class AbstractLocationFilter implements LocationFilter {

  private LocationContainerTraversal traversal;

  public abstract boolean filterLocation(SinusoidalLocation loc);

  public void setTraversal(LocationContainerTraversal traversal) {
    this.traversal = traversal;
  }

  public LocationContainerTraversal getTraversal() {
    return traversal;
  }

}
