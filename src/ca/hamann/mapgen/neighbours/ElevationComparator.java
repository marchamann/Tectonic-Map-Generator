package ca.hamann.mapgen.neighbours;

import java.util.Comparator;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class ElevationComparator implements Comparator<SinusoidalLocation> {

	private TectonicMap tectMap;

	public ElevationComparator(TectonicMap tectMap) {
		this.tectMap = tectMap;
	}

	public int compare(SinusoidalLocation o1, SinusoidalLocation o2) {
		int elevation1 = tectMap.getElevation((SinusoidalLocation) o1);
		int elevation2 = tectMap.getElevation((SinusoidalLocation) o2);

		if (elevation1 < elevation2) {
			return -1;
		}

		if (elevation1 > elevation2) {
			return 1;
		}

		// if (elevation1 == elevation2) {
		// int pseudoElev1 = tectMap.getPseudoElevation((SinusoidalLocation)
		// o1);
		// int pseudoElev2 = tectMap.getPseudoElevation((SinusoidalLocation)
		// o2);
		//
		// if (pseudoElev1 < pseudoElev2) {
		// return -1;
		// }
		//
		// if (pseudoElev1 > pseudoElev2) {
		// return 1;
		// }
		// }

		return 0;
	}

	public boolean isLowerThan(SinusoidalLocation loc1, SinusoidalLocation loc2) {
		return compare(loc1, loc2) < 0;
	}

	public boolean isNotHigherThan(SinusoidalLocation loc1,
			SinusoidalLocation loc2) {
		return compare(loc1, loc2) < 1;
	}

	public boolean isNotLowerThan(SinusoidalLocation loc1,
			SinusoidalLocation loc2) {
		return compare(loc1, loc2) > -1;
	}
}
