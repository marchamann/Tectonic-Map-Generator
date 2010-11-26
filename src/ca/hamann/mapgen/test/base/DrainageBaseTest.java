package ca.hamann.mapgen.test.base;

import junit.framework.TestCase;
import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.drainage.CoastInlandDivider;
import ca.hamann.mapgen.drainage.DrainageProcessor;
import ca.hamann.mapgen.drainage.SeaDrainageDivider;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class DrainageBaseTest extends TestCase {
  protected MapConfiguration config;
  protected SinusoidalGrid grid;
  protected TectonicMap tectMap;
  protected DrainageProcessor processor;
  protected SinusoidalLocation origin, north;

  protected void setUp() throws Exception {
    config = new MapConfiguration(5);
    grid = config.getGrid();
    tectMap = new TectonicMap(config);
    processor = new DrainageProcessor(tectMap);
    origin = grid.getLocation(0, 0, 0);
    north = tectMap.moveNorth(origin);
  }

  protected void tearDown() throws Exception {
    config = null;
    tectMap = null;
    processor = null;
    origin = null;
    north = null;
  }

  protected void setupFlat5x5Landmass() {
    for (int i = -2; i <= 2; i++)
      for (int j = 2; j >= -2; j--) {
        SinusoidalLocation loc = grid.getLocation(0, j, i);
        tectMap.setElevation(loc, config.getSeaLevel() + 1);
      }
  }

  protected void setup5x5LandmassWithLake() {
    for (int i = -2; i <= 2; i++)
      for (int j = 2; j >= -2; j--) {
        int increment = 5;
        SinusoidalLocation loc = grid.getLocation(0, j, i);
        if (j == 0 && i < 1) {
          if (i == -2) {
            increment = 2;
          } else {
            increment = 1;
          }
        }
        tectMap.setElevation(loc, config.getSeaLevel() + increment);
      }
  }

  protected void setup5x5Depression() {
    setupFlat5x5Landmass();
    SinusoidalLocation[] outerRidge =
      {
        loc(0, 2, -2),
        loc(0, 2, -1),
        loc(0, 2, -0),
        loc(0, 2, 1),
        loc(0, 2, 2),
        loc(0, 1, -2),
        loc(0, 1, 2),
        loc(0, -1, -2),
        loc(0, -1, 2),
        loc(0, 0, 2),
        loc(0, -2, -2),
        loc(0, -2, -1),
        loc(0, -2, -0),
        loc(0, -2, 1),
        loc(0, -2, 2)};

    SinusoidalLocation drainageSpot = loc(0, 0, -2);

    for (int i = 0; i < outerRidge.length; i++) {
      tectMap.setElevation(outerRidge[i], config.getSeaLevel() + 5);
    }

    tectMap.setElevation(drainageSpot, config.getSeaLevel() + 2);
  }

  private SinusoidalLocation loc(int spear, int y, int x) {
    return grid.getLocation(spear, y, x);
  }

  protected LocationCollection setupBasicDrainage() {

    SeaDrainageDivider seaDivider = new SeaDrainageDivider(processor);

    CoastInlandDivider ciDivider = new CoastInlandDivider(processor);
    ciDivider.divideCoastFromInland(processor.getLand());

    LocationCollection inland = ciDivider.getInland();
    LocationCollection coast = ciDivider.getCoastalLand();

    seaDivider.divideDrainageUntilFixedPoint(coast, inland);

    return seaDivider.getUndrained();
  }
}
