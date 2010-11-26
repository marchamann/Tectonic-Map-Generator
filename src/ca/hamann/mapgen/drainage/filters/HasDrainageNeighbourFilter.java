package ca.hamann.mapgen.drainage.filters;

import ca.hamann.mapgen.containers.traversal.AbstractLocationFilter;
import ca.hamann.mapgen.drainage.SeaDrainageDivider;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class HasDrainageNeighbourFilter extends AbstractLocationFilter {
  private SeaDrainageDivider divider;

  public HasDrainageNeighbourFilter(SeaDrainageDivider divider) {
    this.divider = divider;
  }

  public boolean filterLocation(SinusoidalLocation loc) {
    return divider.getDrainageNeighbour(loc) != null;
  }
}