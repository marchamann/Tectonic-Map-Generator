package ca.hamann.mapgen.test;

import ca.hamann.mapgen.drainage.CoastInlandDivider;
import ca.hamann.mapgen.test.base.DrainageBaseTest;

public class TestCoastInlandDivider extends DrainageBaseTest {

  private CoastInlandDivider divider;

  protected void setUp() throws Exception {
    super.setUp();
    divider = new CoastInlandDivider(processor);
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    divider = null;
  }

  public void testDivideLandIntoCoastalAndInland() {
    setupFlat5x5Landmass();

    divider.divideCoastFromInland(processor.getLand());

    assertEquals(16, divider.getCoastalLand().size());
    assertEquals(9, divider.getInland().size());
  }

}
