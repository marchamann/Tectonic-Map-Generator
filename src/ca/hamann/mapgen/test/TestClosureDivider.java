package ca.hamann.mapgen.test;

import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.containers.LocationList;
import ca.hamann.mapgen.containers.LocationSet;
import ca.hamann.mapgen.drainage.ClosureDivider;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.test.base.DrainageBaseTest;

public class TestClosureDivider extends DrainageBaseTest {

  private ClosureDivider divider;
  private LocationCollection depression;
  private SinusoidalLocation west;

  protected void setUp() throws Exception {
    super.setUp();
    setup5x5Depression();

    depression = setupBasicDrainage();
    divider = new ClosureDivider(processor);
    west = grid.getLocation(0, 0, -2);
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    divider = null;
    depression = null;
    west = null;
  }

  public void testDivideClosureStep() {

    LocationList currentClosure = new LocationList();
    currentClosure.add(origin);

    LocationCollection remaining = new LocationSet();
    remaining.add(north);

    divider.divideClosureStep(currentClosure, remaining);

    assertEquals(1, divider.getClosure().size());
  }

  public void testGetClosure() {
    divider.divideClosure(origin, depression);

    assertEquals(9, divider.getClosure().size());
  }

  public void testGetBoundarySet() {
    divider.divideClosure(origin, depression);
    assertEquals(16, divider.getBoundarySet(divider.getClosure()).size());
  }

  public void testGetLowestBoundaryDrain() {
    divider.divideClosure(origin, depression);
    assertEquals(west, divider.getLowestBoundaryDrain(divider.getClosure()));
  }

}
