package ca.hamann.mapgen.test;

import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import junit.framework.TestCase;

public class TestSinusoidalLocation extends TestCase {

  private SinusoidalGrid grid;

  protected void setUp() throws Exception {
    grid = new SinusoidalGrid(15);
  }

  protected void tearDown() throws Exception {
    grid = null;
  }

  public void testSinusoidalLocation() {
    SinusoidalLocation loc = grid.getLocation(1, 2, 3);

    assertEquals(1, loc.getSpear());
    assertEquals(3, loc.getX());
    assertEquals(2, loc.getY());
  }

  public void testEquals() {
    SinusoidalLocation loc1, loc2;
    loc1 = grid.getLocation(0, 0, 0);
    loc2 = grid.getLocation(0, 0, 0);
    assertEquals(loc1, loc2);

    assertEquals(loc1.hashCode(), loc2.hashCode());

    loc2 = new SinusoidalLocation(0, 0, 0) {};

    assertFalse(loc1.equals(loc2));
  }

}
