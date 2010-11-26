package ca.hamann.mapgen.test;

import ca.hamann.mapgen.gui.projections.SinusoidalProjection;
import ca.hamann.mapgen.sinusoidal.BasicMover;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import junit.framework.TestCase;

public class TestSinusoidalProjection extends TestCase {

  private BasicMover mover;

  private SinusoidalGrid map;

  private SinusoidalProjection projection;
  private SinusoidalLocation startLoc;
  private int halfWay;

  private int maxY;

  protected void setUp() throws Exception {
    map = new SinusoidalGrid(15);
    mover = map.getMover();
    projection = new SinusoidalProjection(map);
    maxY = map.getMaxY();
    startLoc = map.getLocation(3, maxY, map.getMaxXatY(maxY));
    halfWay = map.getWidth() / 2 - 1;
  }

  protected void tearDown() throws Exception {
    map = null;
    projection = null;
    startLoc = null;
  }

  public void testTranslateX() {

    assertEquals(halfWay, projection.translateX(startLoc));
    assertEquals(halfWay - 1, projection.translateX(mover.moveWest(startLoc)));
    assertEquals(halfWay + 1, projection.translateX(mover.moveEast(startLoc)));

    assertEquals(
      0,
      projection.translateX(map.getLocation(0, 0, -map.getMaxXatY(0))));

    assertEquals(
      map.getWidth() - 1,
      projection.translateX(map.getLocation(7, 0, map.getMaxXatY(0))));

  }

  public void testGetHalfWidthAtX() {
    assertEquals(4, projection.getHalfWidthAtY(map.getMaxY()));
    assertEquals(halfWay + 1, projection.getHalfWidthAtY(0));
  }

  public void testGetCountOfRowsOfWidth() {
    assertEquals(1, projection.getCountOfRowsOfWidth(0));
    assertEquals(2, projection.getCountOfRowsOfWidth(1));
  }

  public void testGetSmoothingValue() {
    int maxY = map.getMaxY();
    assertEquals(0, projection.getSmoothingValue(maxY));
    assertEquals(0, projection.getSmoothingValue(0));

    //    for (int i = maxY; i >= 0; i--) {
    //      int width = map.getMaxXatY(i);
    //      System.out.println(
    //        i
    //          + " "
    //          + width
    //          + " "
    //          + projection.getCountOfRowsOfWidth(width)
    //          + " "
    //          + projection.getSmoothingValue(i));
    //    }
  }

}
