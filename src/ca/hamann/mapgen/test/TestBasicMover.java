package ca.hamann.mapgen.test;

import ca.hamann.mapgen.sinusoidal.BasicMover;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import junit.framework.TestCase;

public class TestBasicMover extends TestCase {

  private BasicMover mover;
  private SinusoidalGrid grid;

  private int halfSpearWidth = 15;
  private int maxLocation = (halfSpearWidth * 2 + 1) * 2;

  SinusoidalLocation origin;

  protected void setUp() throws Exception {
    grid = new SinusoidalGrid(halfSpearWidth);
    mover = grid.getMover();
    origin = grid.getLocation(0, 0, 0);
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    grid = null;
    mover = null;
    origin = null;
  }

  public void testMoveNorthOneLocationAtSpearEdge() {
    SinusoidalLocation edgeLoc = grid.getLocation(0, maxLocation - 1, -1);

    assertEquals(grid.getLocation(0, maxLocation, 0), mover.moveNorth(edgeLoc));
  }

  public void testMoveEastOneLocationAt30() {
    SinusoidalLocation locAt30 = grid.getLocation(0, 30, grid.getMaxXatY(30));
    assertEquals(
      grid.getLocation(1, 30, -grid.getMaxXatY(30)),
      mover.moveEast(locAt30));
  }

  public void testMoveWestOneLocationAt30() {
    SinusoidalLocation locAt30 = grid.getLocation(1, 30, -grid.getMaxXatY(30));
    assertEquals(
      grid.getLocation(0, 30, grid.getMaxXatY(30)),
      mover.moveWest(locAt30));
  }

  public void testMoveNorthOneLocation() {

    assertEquals(grid.getLocation(0, 1, 0), mover.moveNorth(origin));

    SinusoidalLocation maxY = grid.getLocation(0, maxLocation, 0);

    assertEquals(maxY, mover.moveNorth(maxY));
  }

  public void testMoveSouthOneLocation() {
    assertEquals(grid.getLocation(0, -1, 0), mover.moveSouth(origin));

    SinusoidalLocation minY = grid.getLocation(0, -maxLocation, 0);

    assertEquals(minY, mover.moveSouth(minY));
  }

}
