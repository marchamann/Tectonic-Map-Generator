package ca.hamann.mapgen.test;

import ca.hamann.mapgen.test.base.DrainageBaseTest;


public class TestDrainageProcessor extends DrainageBaseTest {
  
  public void testIsSea() {
    tectMap.setElevation(origin, config.getSeaLevel());
    assertTrue(processor.isSea(origin));
    assertFalse(processor.isLand(origin));
  }

  public void testIsNextToSea() {
    tectMap.setElevation(north, config.getSeaLevel());
    assertTrue(processor.isNextToSea(origin));
  }
}
