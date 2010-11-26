package ca.hamann.mapgen.test;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import junit.framework.TestCase;

public class TestSinusoidalMap extends TestCase {

  private SinusoidalGrid grid;
  private int halfSpearWidth = 15;
  private int maxLocation = (halfSpearWidth * 2 + 1) * 2;

  SinusoidalLocation origin;

  protected void setUp() throws Exception {
    grid = new SinusoidalGrid(halfSpearWidth);
    origin = grid.getLocation(0, 0, 0);
  }

  protected void tearDown() throws Exception {
    grid = null;
    origin = null;
  }

  public void testMoveHorizontalOneSpear() {
    assertEquals(1, grid.moveHorizontalOneSpear(0, true));
    assertEquals(0, grid.moveHorizontalOneSpear(7, true));

    assertEquals(7, grid.moveHorizontalOneSpear(0, false));
    assertEquals(6, grid.moveHorizontalOneSpear(7, false));
  }

  public void testGetMaxXAtY() {
    assertEquals(15, grid.getMaxXatY(0));
    assertEquals(0, grid.getMaxXatY(62));
    assertEquals(1, grid.getMaxXatY(61));
    assertEquals(9, grid.getMaxXatY(37));
  }

  public void testGetNextIteratedLocation() {
    SinusoidalLocation loc =
      grid.getLocation(0, maxLocation, grid.getMaxXatY(maxLocation));
    assertEquals(
      grid.getLocation(1, maxLocation, grid.getMaxXatY(maxLocation)),
      grid.getNextIteratedLocation(loc));

    loc = grid.getLocation(7, maxLocation, grid.getMaxXatY(maxLocation));
    int newY = maxLocation - 1;
    assertEquals(
      grid.getLocation(0, newY, -grid.getMaxXatY(newY)),
      grid.getNextIteratedLocation(loc));

    loc = grid.getLocation(7, -maxLocation, -grid.getMaxXatY(maxLocation));
    assertNull(grid.getNextIteratedLocation(loc));
  }

  public void testIsValidLocation() {
//    assertTrue(grid.isValidLocation(grid.getLocation(0, 0, 0)));
//    assertFalse(grid.isValidLocation(grid.getLocation(8, 0, 0)));
//    assertFalse(grid.isValidLocation(grid.getLocation(-1, 0, 0)));
//    assertFalse(
//      grid.isValidLocation(grid.getLocation(0, grid.getMaxY() + 1, 0)));
//    assertFalse(
//      grid.isValidLocation(grid.getLocation(0, 0, halfSpearWidth + 1)));
  }

  public void testgetTotalLocations() {
    assertEquals(20824, grid.getTotalLocations());
  }

}
