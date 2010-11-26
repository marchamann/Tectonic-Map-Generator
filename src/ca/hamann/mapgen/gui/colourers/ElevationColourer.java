package ca.hamann.mapgen.gui.colourers;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class ElevationColourer implements LocationColourer {

  protected TectonicMap tectMap;

  public ElevationColourer(TectonicMap tectMap) {
    this.tectMap = tectMap;
  }

  public int getColourForLocation(SinusoidalLocation loc) {
    int colour, altitude = tectMap.getElevation(loc);
    int[] colours =
      new int[] {
        0x719959,
        0x75AA65,
        0x95BE71,
        0xB2D675,
        0xCAE295,
        0xDEEEA1,
        0xF2EEA1,
        0xEEDE99,
        0xF2CE85,
        0xEAB681,
        0xDA9D79,
        0xC28D7D,
        0xD69D91,
        0xE2AEA5,
        0xDEBAB6,
        0xEEC6D2,
        0xFFCEE2,
        0xFADAEA,
        0xFFDEE6,
        0xFFE6F2,
        0xFFF2FF,
        0xFFFFFF,
        0xDBF0FF,
        0xFFFFFF };

    if (altitude <= tectMap.getSeaLevel())
      colour = ((altitude / 16) * 16) + 127;
    else if (altitude > 255)
      colour = 0xFFFFFF;
    else
      colour = colours[(altitude - 128) / 6];
    return colour;
  }

  public void setTectonicMap(TectonicMap tectMap) {
    this.tectMap = tectMap;
  }

}
