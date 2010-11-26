package ca.hamann.mapgen.containers.traversal;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public interface LocationFilter {
  public boolean filterLocation(SinusoidalLocation loc);
  public void setTraversal(LocationContainerTraversal traversal);
  public LocationContainerTraversal getTraversal();
}
