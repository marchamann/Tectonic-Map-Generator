package ca.hamann.mapgen.test;

import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.drainage.CoastInlandDivider;
import ca.hamann.mapgen.drainage.SeaDrainageDivider;
import ca.hamann.mapgen.test.base.DrainageBaseTest;

public class TestSeaDrainageDivider extends DrainageBaseTest {

  private SeaDrainageDivider divider;
  private LocationCollection inland, coast;

  protected void setUp() throws Exception {
    super.setUp();
    this.setupFlat5x5Landmass();
    divider = new SeaDrainageDivider(processor);

    CoastInlandDivider ciDivider = new CoastInlandDivider(processor);
    ciDivider.divideCoastFromInland(processor.getLand());

    inland = ciDivider.getInland();
    coast = ciDivider.getCoastalLand();

  }

  protected void tearDown() throws Exception {
    super.tearDown();
    divider = null;
  }

  public void testDivideDrainageUntilFixedPoint() {
    divider.divideDrainageUntilFixedPoint(coast, inland);

    LocationCollection drained = divider.getDrained();
    LocationCollection undrained = divider.getUndrained();

    assertEquals(25, drained.size());
    assertEquals(0, undrained.size());
  }

  public void testDivideDrainageUntilFixedPointWithLake() {
    setup5x5LandmassWithLake();
    divider = new SeaDrainageDivider(processor);

    CoastInlandDivider ciDivider = new CoastInlandDivider(processor);
    ciDivider.divideCoastFromInland(processor.getLand());

    inland = ciDivider.getInland();
    coast = ciDivider.getCoastalLand();

    divider.divideDrainageUntilFixedPoint(coast, inland);

    LocationCollection drained = divider.getDrained();
    LocationCollection undrained = divider.getUndrained();

    assertEquals(23, drained.size());
    assertEquals(2, undrained.size());
  }

  public void testDrainageGraph() {
    divider.divideDrainageUntilFixedPoint(coast, inland);

    assertNotNull(processor.getDrainageLocation(origin));
  }

}
