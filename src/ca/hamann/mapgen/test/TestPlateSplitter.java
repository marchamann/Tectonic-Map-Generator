package ca.hamann.mapgen.test;

import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.tectonic.PlateSplitter;
import ca.hamann.mapgen.tectonic.TectonicMap;
import junit.framework.TestCase;

public class TestPlateSplitter extends TestCase {

  private MapConfiguration config;
  private TectonicMap tectMap;
  private PlateSplitter splitter;
  private SinusoidalLocation origin, east, easter, eastest;

  protected void setUp() throws Exception {
    config = new MapConfiguration(5);
    tectMap = new TectonicMap(config);
    splitter = new PlateSplitter(tectMap);
    origin = config.getGrid().getLocation(0, 0, 0);
    east = config.getGrid().getLocation(0, 0, 1);
    easter = config.getGrid().getLocation(0, 0, 2);
    eastest = config.getGrid().getLocation(0, 0, 3);
  }

  protected void tearDown() throws Exception {
    config = null;
    tectMap = null;
    splitter = null;
  }

  public void testCollectPlate() {
    int plateIndex = 1;
    tectMap.setPlateIndex(origin, plateIndex);
    LocationCollection plate = splitter.collectPlate(plateIndex);

    assertTrue(plate.contains(origin));
    assertEquals(0, tectMap.getPlateIndex(origin));
  }

  public void testSplitPlate() {
    initializePlateOne();
    splitter.splitPlate(1, 2);

    assertTrue(tectMap.getPlateIndex(origin) > 0);
    assertTrue(tectMap.getPlateIndex(east) > 0);
    assertTrue(tectMap.getPlateIndex(easter) > 0);
    assertTrue(tectMap.getPlateIndex(eastest) > 0);
  }

  private void initializePlateOne() {
    int plateIndex = 1;
    tectMap.setPlateIndex(origin, plateIndex);
    tectMap.setPlateIndex(east, plateIndex);
    tectMap.setPlateIndex(easter, plateIndex);
    tectMap.setPlateIndex(eastest, plateIndex);
  }

}
