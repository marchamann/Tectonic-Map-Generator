package ca.hamann.mapgen.sinusoidal;

import ca.hamann.mapgen.containers.*;

public class MapLocationIterator implements LocationIterator {

  private SinusoidalGrid grid;
  private SinusoidalLocation next;

  public MapLocationIterator(SinusoidalGrid grid) {
    this.grid = grid;
    next = grid.getInitialLocation();
  }

  public boolean hasNext() {
    return next != null;
  }

  public SinusoidalLocation next() {
    SinusoidalLocation result = next;
    next = grid.getNextIteratedLocation(next);

    return result;
  }

}
