package ca.hamann.mapgen.containers;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public interface LocationIterator {
  public abstract boolean hasNext();
  public abstract SinusoidalLocation next();
}