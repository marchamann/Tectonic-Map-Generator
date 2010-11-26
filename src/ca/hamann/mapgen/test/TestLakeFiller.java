package ca.hamann.mapgen.test;

import ca.hamann.mapgen.drainage.LakeFiller;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.test.base.DrainageBaseTest;

public class TestLakeFiller extends DrainageBaseTest {

  private LakeFiller filler;

//  private SinusoidalLocation lowest = new SinusoidalLocation(0, 1, -1);

  protected void setUp() throws Exception {
    super.setUp();
    setup5x5Depression();
    filler = new LakeFiller(processor, setupBasicDrainage());
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

//  public void testRemoveNextLowestLocation() {
//    assertEquals(lowest, filler.removeNextLowestLocation());
//  }

  public void testGetLowestClosure() {
    SinusoidalLocation lowest = filler.removeNextLowestLocation();
    filler.fillLakeWithBottom(lowest);
    assertEquals(1, tectMap.getAccummulatedWater(origin));
  }

  public void testFillLakes() {
    filler.fillLakes();
    assertTrue(processor.validateGraph());
  }

}
