package ca.hamann.mapgen.fillers;

import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;

public class CylindricalLineFillers extends AbstractLineFillers {

  private SinusoidalGrid map;

  public CylindricalLineFillers(SinusoidalGrid map) {
    this.map = map;
    fillLineFillers();
  }

  private void fillLineFillers() {
    lineFillers = new SequenceFiller[map.getMaxY() + 1];
    int totalWidth = map.getFullSpearWidth();
    for (int i = map.getMaxY(); i >= 0; i--) {
      int width = getSpearWidthAtY(i);
      lineFillers[i] = new CylindricalSequenceFiller(width, totalWidth);
    }
  }

  public int getWidthAtY(int y) {
    return getSpearWidthAtY(y) * 8;
  }

  public int getSpearWidthAtY(int y) {
    return map.getMaxXatY(y) * 2 + 1;
  }
}
