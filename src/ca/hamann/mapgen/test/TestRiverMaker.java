package ca.hamann.mapgen.test;

import ca.hamann.mapgen.drainage.RiverMaker;
import ca.hamann.mapgen.sinusoidal.BasicMover;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.test.base.DrainageBaseTest;

public class TestRiverMaker extends DrainageBaseTest {

  private RiverMaker maker;
  SinusoidalLocation coastalLocation;

  protected void setUp() throws Exception {
    super.setUp();
    setupRiverLandmass();
    setupBasicDrainage();
    maker = new RiverMaker(processor);
    coastalLocation = grid.getLocation(0, 0, -5);
  }

  private void setupRiverLandmass() {
    for (int i = 0; i < 10; i++) {
      int x = i - 5;
      tectMap.setElevation(grid.getLocation(0, 1, x), 131);
      tectMap.setElevation(grid.getLocation(0, 0, x), 129);
      tectMap.setElevation(grid.getLocation(0, -1, x), 131);
    }
    tectMap.setElevation(grid.getLocation(0, 0, 4), 130);
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    maker = null;
    coastalLocation = null;
  }

  public void testInitializeRainfall() {
    maker.initializeRainfall();
    assertEquals(1, maker.getWater(origin));
  }

  public void testDrainWaterInland() {
    maker.setWater(origin, 1);
    SinusoidalLocation drain = processor.getDrainageLocation(origin);

    assertNotNull(drain);
    maker.drainWater(origin);

    assertEquals(0, maker.getWater(origin));
    assertEquals(1, maker.getWater(drain));
    assertEquals(1, processor.getFlow(drain));
  }

  public void testDrainWaterCoastal() {
    //    SinusoidalLocation coastalLocation = grid.getLocation(0, 0, -2);
    maker.setWater(coastalLocation, 1);

    SinusoidalLocation drain = processor.getDrainageLocation(coastalLocation);
    assertNull(drain);

    maker.drainWater(coastalLocation);

    assertEquals(0, maker.getWater(origin));
  }

  public void testDrainAllLand() {
    assertEquals(
      coastalLocation,
      processor.getTransitiveTerminus(grid.getLocation(0, 0, 3)));

    maker.initializeRainfall();
    maker.drainAllLand();

    BasicMover mover = tectMap.getBasicMover();

    assertEquals(8, processor.getFlow(coastalLocation));
    assertEquals(7, processor.getFlow(mover.moveEast(coastalLocation)));
  }

}
