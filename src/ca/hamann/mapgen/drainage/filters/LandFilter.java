package ca.hamann.mapgen.drainage.filters;

import ca.hamann.mapgen.containers.traversal.AbstractLocationFilter;
import ca.hamann.mapgen.drainage.DrainageProcessor;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class LandFilter extends AbstractLocationFilter {
  private DrainageProcessor processor;

  public LandFilter(DrainageProcessor processor) {
    this.processor = processor;
  }

  public boolean filterLocation(SinusoidalLocation loc) {
    return processor.isLand(loc);
  }
}