package ca.hamann.mapgen.test;

import ca.hamann.mapgen.containers.LocationList;
import ca.hamann.mapgen.neighbours.Neighbourhoods;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import junit.framework.TestCase;

public class TestNeighbourhoods extends TestCase {

  private Neighbourhoods neighbourhoods;
  private SinusoidalGrid grid;

  protected void setUp() throws Exception {
    grid = new SinusoidalGrid(10);
    neighbourhoods = grid.getNeighbourhoods();
  }

  protected void tearDown() throws Exception {
    grid = null;
    neighbourhoods = null;
  }

  public void testGetNeighbours() {
    LocationList neighbours =
      (LocationList) neighbourhoods.getNeighbours(grid.getLocation(0, 0, 0));
    assertEquals(8, neighbours.size());

  }
}
