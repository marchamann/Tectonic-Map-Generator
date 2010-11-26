package ca.hamann.mapgen.drainage;

import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.neighbours.ElevationComparator;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.sinusoidal.SinusoidalMapStorage;

public class RiverMaker {

  private DrainageProcessor processor;
  private SinusoidalMapStorage waterMap;

  public RiverMaker(DrainageProcessor processor) {
    this.processor = processor;
    waterMap = new SinusoidalMapStorage(processor.getTectonicMap().getGrid());
  }

  public int getWater(SinusoidalLocation loc) {
    return waterMap.getValue(loc);
  }

  public void setWater(SinusoidalLocation loc, int value) {
    waterMap.putValue(loc, value);
  }

  public void initializeRainfall() {
    LocationCollection landLocations = processor.getLand();
    LocationIterator iterator = landLocations.iterator();

    while (iterator.hasNext()) {
      setWater(iterator.next(), 1);
    }
  }

  public void correctDrainage() {
    ElevationComparator comparator = processor.getElevationComparator();
    LocationCollection seaDrained = processor.getSeaDrainedLand();
    LocationIterator iterator = seaDrained.iterator();

    while (iterator.hasNext()) {
      SinusoidalLocation next = iterator.next();
      SinusoidalLocation lowest = processor.getLowestNeighbour(next);
      SinusoidalLocation drain = processor.getDrainageLocation(next);

      if (drain != null && comparator.isLowerThan(lowest, drain)) {
        processor.addDrainageArrow(next, lowest);

        if (!processor.hasTransitiveTerminus(next)) {
          processor.addDrainageArrow(next, drain);
        }
      }
    }

  }

  public void drainWater(SinusoidalLocation loc) {
    SinusoidalLocation drain = processor.getDrainageLocation(loc);

    if (drain != null) {
      int water = getWater(loc);
      setWater(drain, getWater(drain) + water);
      processor.setFlow(drain, processor.getFlow(drain) + water);
    }
    setWater(loc, 0);
  }

  public void drainAllLand() {
    boolean flag = true;
    while (flag) {
      flag = false;

      LocationCollection landLocations = processor.getLand();
      LocationIterator iterator = landLocations.iterator();

      while (iterator.hasNext()) {
        SinusoidalLocation next = iterator.next();
        if (getWater(next) > 0) {
          flag = true;
          drainWater(next);
        }
      }

      //      System.out.println(count);
    }
  }

}
