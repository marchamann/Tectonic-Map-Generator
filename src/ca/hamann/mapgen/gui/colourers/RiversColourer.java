package ca.hamann.mapgen.gui.colourers;

import java.awt.Color;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class RiversColourer extends ElevationColourer {

  public RiversColourer(TectonicMap tectMap) {
    super(tectMap);
  }

  public int getColourForLocation(SinusoidalLocation loc) {
    int landWater = new Color(150, 200, 255).getRGB();

    if (tectMap.getAccumulatedWater(loc) > 0) {
      return landWater; //Color.CYAN.getRGB();
    }

    if (tectMap.getFlow(loc) > 100) {
      return landWater;
      //      return Color.BLUE.getRGB();
    }

    return super.getColourForLocation(loc);
  }

}
