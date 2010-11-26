package ca.hamann.mapgen.drainage;

import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.containers.traversal.LocationContainerTraversal;
import ca.hamann.mapgen.containers.traversal.LocationFilter;

public class DrainagePartition {
  private LocationCollection collection;
  private LocationCollection drainedLand, undrainedLand;

  public DrainagePartition(LocationCollection collection) {
    this.collection = collection;
  }

  public void partition(LocationFilter filter) {
    LocationContainerTraversal traversal =
      new LocationContainerTraversal(collection);

    drainedLand = traversal.filter(filter);
    undrainedLand = traversal.getRejects();
  }

  public LocationCollection getDrainedLand() {
    return drainedLand;
  }

  public LocationCollection getUndrainedLand() {
    return undrainedLand;
  }

}
