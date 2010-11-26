package ca.hamann.mapgen.drainage;

import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.containers.traversal.LocationContainerTraversal;
import ca.hamann.mapgen.drainage.filters.*;

public class LandSeaDivider {

  private DrainageProcessor processor;

  public LandSeaDivider(DrainageProcessor processor) {
    this.processor = processor;
  }

  public LocationCollection getLand() {
    LocationContainerTraversal traversal =
      new LocationContainerTraversal(processor.getAllLocations());

    return traversal.filter(new LandFilter(processor));
  }

}
