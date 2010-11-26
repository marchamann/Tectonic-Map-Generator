package ca.hamann.mapgen.gui.projections;

import java.awt.image.BufferedImage;

import ca.hamann.mapgen.gui.colourers.LocationColourer;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;

public class SinusoidalSeparatedProjection extends AbstractProjection {

  public SinusoidalSeparatedProjection(SinusoidalGrid grid) {
    super(grid);
  }

  public void colourLocation(
    LocationColourer colourer,
    BufferedImage image,
    SinusoidalLocation loc) {
    int x = translateX(loc);
    int y = translateY(loc);
    try {
      image.setRGB(x, y, colourer.getColourForLocation(loc));
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new RuntimeException("x:" + x + " y:" + y);
    }
  }

  protected int translateX(SinusoidalLocation loc) {
    int x =
      grid.getHalfSpearWidth()
        + loc.getSpear() * grid.getFullSpearWidth()
        + loc.getX();
    return x;
  }

}
