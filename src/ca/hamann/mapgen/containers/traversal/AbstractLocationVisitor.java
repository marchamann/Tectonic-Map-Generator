package ca.hamann.mapgen.containers.traversal;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public abstract class AbstractLocationVisitor implements LocationVisitor {

  protected LocationContainerTraversal traversal;

  public abstract void visitLocation(SinusoidalLocation loc);

  public void setTraversal(LocationContainerTraversal traversal) {
    this.traversal = traversal;
  }

}
