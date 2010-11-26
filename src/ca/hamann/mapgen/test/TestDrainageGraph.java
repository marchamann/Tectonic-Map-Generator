package ca.hamann.mapgen.test;

import ca.hamann.mapgen.drainage.DrainageGraph;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.test.base.DrainageBaseTest;

public class TestDrainageGraph extends DrainageBaseTest {

  private DrainageGraph graph;
  private SinusoidalLocation origin, west, farWest;

  protected void setUp() throws Exception {
    super.setUp();
    graph = new DrainageGraph(processor);
    origin = grid.getLocation(0, 0, 0);
    west = grid.getLocation(0, 0, -1);
    farWest = grid.getLocation(0, 0, -2);
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    graph = null;
    origin = west = farWest = null;
  }

  public void testGetTransitiveTerminusWithEmptyGraph() {
    assertEquals(origin, graph.getTransitiveTerminus(origin));
  }

  public void testGetTransitiveTerminusWithSingleEntry() {
    graph.addDrainageArrow(origin, west);
    assertEquals(west, graph.getTransitiveTerminus(origin));
  }

  public void testGetTransitiveTerminusWithChain() {
    graph.addDrainageArrow(origin, west);
    graph.addDrainageArrow(west, farWest);

    assertEquals(farWest, graph.getTransitiveTerminus(origin));
  }

  public void testGetTransitiveTerminusWithCycle() {
    graph.addDrainageArrow(origin, west);
    graph.addDrainageArrow(west, origin);

    assertNull(graph.getTransitiveTerminus(origin));
  }
}
