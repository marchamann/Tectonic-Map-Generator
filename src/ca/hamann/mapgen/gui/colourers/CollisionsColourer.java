package ca.hamann.mapgen.gui.colourers;

import java.awt.Color;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class CollisionsColourer implements LocationColourer {

  private TectonicMap tectMap;

  public CollisionsColourer(TectonicMap tectMap) {
    this.tectMap = tectMap;
  }

  public int getColourForLocation(SinusoidalLocation loc) {

    return getColour(tectMap.getCollisions(loc));
  }

  private int getColour(int collisions) {
    if (collisions > 127) {
      collisions = 127;
    }
    if (collisions < -255) {
      collisions = -255;
    }

    if (collisions > 0) {
      return new Color(128 + collisions, 0, 0).getRGB();
    } else if (collisions < 0) {
      return new Color(0, 0, 255 + collisions).getRGB();
    }

    return Color.GREEN.getRGB();
  }

  public TectonicMap getTectonicMap() {
    return tectMap;
  }

  public void setTectonicMap(TectonicMap map) {
    tectMap = map;
  }

}
