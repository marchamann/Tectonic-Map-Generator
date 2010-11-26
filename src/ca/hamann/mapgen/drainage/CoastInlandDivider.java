package ca.hamann.mapgen.drainage;

import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.drainage.filters.*;

public class CoastInlandDivider {

  private LocationCollection coastLand, inLand;

  private DrainageProcessor processor;

  public CoastInlandDivider(DrainageProcessor processor) {
    this.processor = processor;
  }

  public void divideCoastFromInland(LocationCollection allLand) {

    DrainagePartition partition = new DrainagePartition(allLand);

    partition.partition(new CoastFilter(processor));

    coastLand = partition.getDrainedLand();
    inLand = partition.getUndrainedLand();

  }

  public LocationCollection getCoastalLand() {
    return coastLand;
  }

  public LocationCollection getInland() {
    return inLand;
  }

}
