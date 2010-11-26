package ca.hamann.mapgen.gui.projections;

import java.awt.image.BufferedImage;

import ca.hamann.mapgen.fillers.SequenceFiller;
import ca.hamann.mapgen.gui.colourers.LocationColourer;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;

public class SinusoidalProjection extends AbstractProjection {

  private SequenceFiller[] lineFillers;
  private int smoothingValues[];
  public SinusoidalProjection(SinusoidalGrid map) {
    super(map);
    this.grid = map;
    smoothingValues = new int[map.getMaxY() + 1];
    lineFillers = new SequenceFiller[map.getMaxY() + 1];
    fillSmoothingValues();
    fillLineFillers();
  }

  private void fillLineFillers() {
    for (int i = grid.getMaxY(); i >= 0; i--) {
      int width = getWidthAtY(i);
      lineFillers[i] =
        new SequenceFiller(width, width + smoothingValues[i] * 2);
    }
  }

  private void fillSmoothingValues() {
    for (int i = grid.getMaxY(); i >= 0; i--) {
      smoothingValues[i] = getSmoothingValue(i);
    }
  }

  public int translateX(SinusoidalLocation loc) {
    grid = getGrid();
    int startX = getStartX(loc);
    SequenceFiller lineFiller = getLineFiller(loc.getY());
    return startX + lineFiller.sumUpTo(getWidthOrdinal(loc));
  }

  private SequenceFiller getLineFiller(int y) {
    return lineFillers[Math.abs(y)];
  }

  private int getStartX(SinusoidalLocation loc) {
    int halfWay = grid.getWidth() / 2;
    int y = loc.getY();
    int startX = halfWay - getHalfWidthAtY(y);
    int smoothing = smoothingValues[Math.abs(y)];
    return startX - smoothing;
  }

  public void colourLocation(
    LocationColourer colourer,
    BufferedImage image,
    SinusoidalLocation loc) {

    int extras = getLineFiller(loc.getY()).howMany(getWidthOrdinal(loc)) - 1;
    int start = translateX(loc);
    for (int i = 0; i <= extras; i++) {
      try {
        image.setRGB(
          start + i,
          translateY(loc),
          colourer.getColourForLocation(loc));
      } catch (RuntimeException e) {
        throw new RuntimeException("x: " + (start + i) + " Extras: " + extras);
      }
    }

  }

  public int getCountOfRowsOfWidth(int width) {
    int result = 0;
    int topY = getTopYWithWidth(width);
    for (int i = topY; i >= 0; i--) {
      int widthAt = grid.getMaxXatY(i);
      if (widthAt == width) {
        result++;
      }

      if (widthAt > width) {
        break;
      }
    }

    return result;
  }

  private int getTopYWithWidth(int width) {
    for (int i = grid.getMaxY(); i >= 0; i--) {
      int widthAt = grid.getMaxXatY(i);
      if (widthAt == width) {
        return i;
      }
    }
    return -1;
  }

  public int getSmoothingValue(int y) {
    int width = grid.getMaxXatY(y);
    int widthCount = getCountOfRowsOfWidth(width);

    if (widthCount < 2 || width == grid.getHalfSpearWidth()) {
      return 0;
    }

    SequenceFiller filler = new SequenceFiller(widthCount, 7);

    int sequenceIndex = getTopYWithWidth(width) - y;

    return filler.sumUpTo(sequenceIndex);
  }

}
