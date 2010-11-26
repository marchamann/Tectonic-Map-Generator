package ca.hamann.mapgen.gui.projections;

import java.awt.image.BufferedImage;

import ca.hamann.mapgen.gui.colourers.LocationColourer;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;

public interface Projection {
  void colourLocation(
    LocationColourer colourer,
    BufferedImage image,
    SinusoidalLocation loc);

  void setGrid(SinusoidalGrid grid);
}
