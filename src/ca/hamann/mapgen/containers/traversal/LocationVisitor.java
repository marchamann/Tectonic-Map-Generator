package ca.hamann.mapgen.containers.traversal;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public interface LocationVisitor {
  public void visitLocation(SinusoidalLocation loc);
  public void setTraversal(LocationContainerTraversal traversal);
}
