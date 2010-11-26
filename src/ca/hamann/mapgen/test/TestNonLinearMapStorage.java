package ca.hamann.mapgen.test;

import ca.hamann.mapgen.sinusoidal.NonLinearMapStorage;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import junit.framework.TestCase;

public class TestNonLinearMapStorage extends TestCase {

  public void testGetValue() {
    SinusoidalGrid grid = new SinusoidalGrid(5);
    NonLinearMapStorage storage = new NonLinearMapStorage(grid);

    storage.putValue(1, 1, 1, 10);
    assertEquals(10, storage.getValue(1, 1, 1));

  }
}
