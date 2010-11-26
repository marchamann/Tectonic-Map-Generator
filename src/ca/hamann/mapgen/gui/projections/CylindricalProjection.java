package ca.hamann.mapgen.gui.projections;

import java.awt.image.BufferedImage;

import ca.hamann.mapgen.fillers.CylindricalLineFillers;
import ca.hamann.mapgen.fillers.SequenceFiller;
import ca.hamann.mapgen.gui.colourers.LocationColourer;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;

public class CylindricalProjection extends AbstractProjection {

  CylindricalLineFillers fillers;

  public CylindricalProjection(SinusoidalGrid grid) {
    super(grid);
    fillers = new CylindricalLineFillers(grid);
  }

  public void colourLocation(
    LocationColourer colourer,
    BufferedImage image,
    SinusoidalLocation loc) {
    int y = loc.getY();
    int newY = translateY(loc);
    int offset = getWidthOrdinal(loc);
    SequenceFiller filler = fillers.getLineFiller(y);
    int startX = filler.sumUpTo(offset);
    int repeatCount = filler.howMany(offset);

    for (int i = 0; i < repeatCount; i++) {
      image.setRGB(startX + i, newY, colourer.getColourForLocation(loc));
    }

  }

}
