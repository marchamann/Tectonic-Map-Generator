package ca.hamann.mapgen.test;

import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.Rotater;
import ca.hamann.mapgen.sinusoidal.BasicMover;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import ca.hamann.mapgen.tectonic.TectonicMap;
import junit.framework.TestCase;

public class TestRotater extends TestCase {

  private SinusoidalGrid grid;

  private TectonicMap tectMap;

  private BasicMover mover;

  protected void setUp() throws Exception {
  }

  protected void tearDown() throws Exception {
  }

  public void testRotateEast() {

    tectMap = new TectonicMap(new MapConfiguration(5));
    grid = tectMap.getGrid();

    mover = tectMap.getGrid().getMover();
    SinusoidalLocation loc = grid.getInitialLocation();
    for (int i = 0; i < grid.getHeight(); i++) {
      tectMap.setPlateIndex(loc, 1);
      loc = mover.moveSouth(loc);
    }

    Rotater rotater = new Rotater(tectMap, true);

    tectMap = rotater.processMap();

    loc = grid.getInitialLocation();
    for (int i = 0; i < grid.getHeight(); i++) {
      assertEquals(1, countInLine(loc));
      loc = mover.moveSouth(loc);
    }

  }

  private int countInLine(SinusoidalLocation loc) {
    int end = grid.getWidthAtY(loc.getY());
    int count = 0;
    for (int i = 0; i < end; i++) {
      if (tectMap.getPlateIndex(loc) > 0) {
        //        System.out.println(loc);
        count++;
      }
      loc = mover.moveEast(loc);
    }
    return count;
  }

}
