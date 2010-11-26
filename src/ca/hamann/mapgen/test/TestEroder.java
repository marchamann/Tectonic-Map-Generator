package ca.hamann.mapgen.test;

import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.sinusoidal.BasicMover;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import ca.hamann.mapgen.tectonic.Eroder;
import ca.hamann.mapgen.tectonic.TectonicMap;
import ca.hamann.mapgen.tectonic.TectonicPlates;
import junit.framework.TestCase;

public class TestEroder extends TestCase {

  private BasicMover mover;
  private SinusoidalGrid map;
  private TectonicMap tectMap;
  private Eroder eroder;
  private SinusoidalLocation loc;

  protected void setUp() throws Exception {
    map = new SinusoidalGrid(15);
    mover = map.getMover();
    tectMap = new TectonicMap(new MapConfiguration(15));
    eroder = new Eroder(tectMap);
    loc = map.getInitialLocation();
  }

  protected void tearDown() throws Exception {
    map = null;
    tectMap = null;
    eroder = null;
    loc = null;
  }

  public void testSetBaseElevation() {

    int collisions = 1;
    int baseSeaElevation = 64;
    TectonicPlates plates = new TectonicPlates(3);
    tectMap.setPlates(plates);

    tectMap.setPlateIndex(loc, 1);
    tectMap.setCollisions(loc, 1);
    tectMap.setBaseElevation(loc);
    assertEquals(baseSeaElevation + collisions, tectMap.getElevation(loc));

    plates.setSeaPlateCount(2);
    //    plates.setLandPlate(3);
    int baseLandElevation = 130;
    tectMap.setPlateIndex(loc, 3);
    tectMap.setBaseElevation(loc);
    assertEquals(baseLandElevation + collisions, tectMap.getElevation(loc));
  }

  public void testErodeLocation() {
    SinusoidalLocation testLoc = mover.moveEast(loc);
    tectMap.setElevation(loc, 10);

    eroder.erodeLocation(loc);
    assertEquals(5, tectMap.getElevation(loc));
    assertEquals(1, tectMap.getElevation(testLoc));
  }

}
