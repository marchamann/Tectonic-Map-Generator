package ca.hamann.mapgen.gui.colourers;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.tectonic.TectonicMap;

public interface LocationColourer {

  int getColourForLocation(SinusoidalLocation loc);

  void setTectonicMap(TectonicMap tectMap);

}
