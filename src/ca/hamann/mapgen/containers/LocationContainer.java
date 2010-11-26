package ca.hamann.mapgen.containers;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public interface LocationContainer {
  public boolean contains(SinusoidalLocation loc);
  public boolean isEmpty();
  public LocationIterator iterator();
  public int size();
}