package ca.hamann.mapgen.gui.colourers;

import java.awt.Color;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class PlateColourer implements LocationColourer {

  private TectonicMap tectMap;

  //  private static final int[] colours = new int[] {
  //    Color.red.getRGB(),
  //    Color.green.getRGB(),
  //    Color.blue.getRGB(),
  //    Color.darkGray.getRGB(),
  //    Color.cyan.getRGB(),
  //    Color.magenta.getRGB(),
  //    Color.yellow.getRGB(),
  //    Color.gray.getRGB(),
  //    Color.orange.getRGB(),
  //    Color.pink.getRGB()};

  private static final int FULL = 255,
    HALF = 127,
    QUARTER = 64,
    TWOTHIRDS = 192,
    NONE = 0;

  private static final Color[] colours =
    new Color[] {
      new Color(FULL, NONE, NONE),
      new Color(NONE, FULL, NONE),
      new Color(NONE, NONE, FULL),
      new Color(TWOTHIRDS, TWOTHIRDS, TWOTHIRDS),
      new Color(HALF, NONE, NONE),
      new Color(NONE, HALF, NONE),
      new Color(NONE, NONE, HALF),
      new Color(HALF, HALF, HALF),
      new Color(HALF, HALF, NONE),
      new Color(NONE, HALF, HALF),
      new Color(HALF, NONE, HALF),
      new Color(QUARTER, QUARTER, QUARTER),
      new Color(FULL, FULL, NONE),
      new Color(NONE, FULL, FULL),
      new Color(FULL, NONE, FULL),
      new Color(FULL, FULL, QUARTER),
      new Color(QUARTER, FULL, FULL),
      new Color(FULL, NONE, QUARTER),
      new Color(HALF, HALF, QUARTER),
      new Color(QUARTER, HALF, HALF),
      new Color(HALF, QUARTER, HALF),
      new Color(FULL, HALF, NONE),
      new Color(FULL, NONE, HALF),
      new Color(HALF, FULL, NONE),
      new Color(NONE, FULL, HALF),
      new Color(HALF, NONE, FULL),
      new Color(NONE, HALF, FULL)};

  public PlateColourer(TectonicMap tectMap) {
    this.tectMap = tectMap;
  }

  public int getColourForLocation(SinusoidalLocation loc) {
    return getColour(tectMap.getPlateIndex(loc));
  }

  public static int getColour(int value) {

    if (value < 0) {
      return Color.white.getRGB();
    }
    if (value == 0) {
      return Color.black.getRGB();
    }

    return colours[(value - 1) % colours.length].getRGB();
  }

  public void setTectonicMap(TectonicMap tectMap) {
    this.tectMap = tectMap;
  }

}
