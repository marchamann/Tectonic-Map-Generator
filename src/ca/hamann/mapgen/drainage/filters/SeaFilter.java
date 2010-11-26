package ca.hamann.mapgen.drainage.filters;

import ca.hamann.mapgen.containers.traversal.AbstractLocationFilter;
import ca.hamann.mapgen.drainage.DrainageProcessor;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class SeaFilter extends AbstractLocationFilter {
  private DrainageProcessor processor;

  public SeaFilter(DrainageProcessor processor) {
    this.processor = processor;
  }
  public boolean filterLocation(SinusoidalLocation loc) {
    if (processor.isSea(loc)) {
      getTraversal().signalBreak();
      return true;
    }
    return false;
  }
}