package ca.hamann.mapgen.tectonic;

import java.util.Random;

import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.containers.LocationList;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class PlateSplitter {

  private TectonicMap tectMap;
  private PlateSpreader spreader;

  private Random random = new Random(0);

  public PlateSplitter(TectonicMap tectMap) {
    this.tectMap = tectMap;
    spreader = new PlateSpreader(tectMap);
  }

  public LocationList collectPlate(int plateIndex) {
    LocationList plate = new LocationList();
    LocationIterator iterator = tectMap.getGrid().iterator();

    while (iterator.hasNext()) {
      SinusoidalLocation next = iterator.next();

      if (tectMap.getPlateIndex(next) == plateIndex) {
        tectMap.setPlateIndex(next, 0);
        plate.add(next);
      }
    }

    return plate;
  }

  public void splitPlate(int oldPlateIndex, int emptyPlateIndex) {
    LocationList oldPlate = collectPlate(oldPlateIndex);

    SinusoidalLocation loc1 = oldPlate.remove(random.nextInt(oldPlate.size()));
    SinusoidalLocation loc2 = oldPlate.remove(random.nextInt(oldPlate.size()));

    spreader.getNeighbourSet(oldPlateIndex).add(loc1);
    spreader.getNeighbourSet(emptyPlateIndex).add(loc2);

    spreader.floodFillPlates();

    EmptyLocationFiller filler = new EmptyLocationFiller(tectMap);
    filler.setRiftValleyMaker(false);
    filler.fillEmptyLocations();
  }

}
