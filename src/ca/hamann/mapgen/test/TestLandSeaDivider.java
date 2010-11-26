package ca.hamann.mapgen.test;

import ca.hamann.mapgen.drainage.LandSeaDivider;
import ca.hamann.mapgen.test.base.DrainageBaseTest;

public class TestLandSeaDivider extends DrainageBaseTest {
  private LandSeaDivider divider;

  protected void setUp() throws Exception {
    super.setUp();
    divider = new LandSeaDivider(processor);
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    divider = null;
  }



  public void testGetLand() {
    setupFlat5x5Landmass();
    assertEquals(25, divider.getLand().size());
  }

  //  public void testDivideLandIntoCoastalAndInland() {
  //    setupFlat5x5Landmass();
  //
  //    divider.divideLandIntoCoastalAndInland();
  //
  //    assertEquals(16, maker.getSeaDrainedLand().size());
  //    assertEquals(9, maker.getInland().size());
  //  }

}
