package ca.hamann.mapgen.gui.projections;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;

public abstract class AbstractProjection implements Projection {

  protected SinusoidalGrid grid;

  public AbstractProjection(SinusoidalGrid grid) {
    this.grid = grid;
  }

  protected int translateY(SinusoidalLocation loc) {
    int y = grid.getMaxY() - loc.getY();
    return y;
  }

  public SinusoidalGrid getGrid() {
    return grid;
  }

  protected int getWidthOrdinal(SinusoidalLocation loc) {
    return grid.getWidthOrdinal(loc);
  }

  public int getWidthAtY(int y) {
    return grid.getWidthAtY(y);
  }

  public int getHalfWidthAtY(int y) {
    return grid.getHalfWidthAtY(y);
  }

  public int getSpearWidthAtY(int y) {
    return grid.getSpearWidthAtY(y);
  }

  public int getNormalizedX(int x, int y) {
    return grid.getNormalizedX(x, y);
  }

  public void setGrid(SinusoidalGrid grid) {
    this.grid = grid;
  }

}
