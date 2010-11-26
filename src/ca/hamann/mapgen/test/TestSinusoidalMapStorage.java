package ca.hamann.mapgen.test;

import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import ca.hamann.mapgen.sinusoidal.SinusoidalMapStorage;
import junit.framework.TestCase;

public class TestSinusoidalMapStorage extends TestCase {

  private SinusoidalGrid map;
  private SinusoidalLocation loc;
  private SinusoidalMapStorage storage;

  protected void setUp() throws Exception {
    map = new SinusoidalGrid(15);
    loc = map.getInitialLocation();
    storage = new SinusoidalMapStorage(map);
  }

  protected void tearDown() throws Exception {
    map = null;
    loc = null;
    storage = null;
  }

  public void testValue() {
    storage.putValue(loc, 1);
    assertEquals(1, storage.getValue(loc));
  }

  public void testFullRange() {
    LocationIterator iterator = map.iterator();

    assertEquals(0, storage.nonZeroCount());

    while (iterator.hasNext()) {
      SinusoidalLocation nextLoc = iterator.next();

      storage.putValue(nextLoc, 1);
      assertEquals(1, storage.getValue(nextLoc));
    }

    assertEquals(20824, storage.nonZeroCount());
  }

}
