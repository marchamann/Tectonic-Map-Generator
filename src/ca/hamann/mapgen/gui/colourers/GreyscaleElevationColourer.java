package ca.hamann.mapgen.gui.colourers;

import java.awt.Color;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class GreyscaleElevationColourer implements LocationColourer {

	protected TectonicMap tectMap;

	public GreyscaleElevationColourer(TectonicMap tectMap) {
		this.tectMap = tectMap;
	}

	public int getColourForLocation(SinusoidalLocation loc) {
		int colour;
		int altitude = tectMap.getElevation(loc);

		if (altitude <= tectMap.getSeaLevel())
			colour = 0x000000;
		else if (altitude > 255)
			colour = 0xFFFFFF;
		else {
			int colourComponant = altitude;
			colour = new Color(colourComponant, colourComponant,
					colourComponant).getRGB();
		}
		return colour;
	}

	public void setTectonicMap(TectonicMap tectMap) {
		this.tectMap = tectMap;
	}

}
