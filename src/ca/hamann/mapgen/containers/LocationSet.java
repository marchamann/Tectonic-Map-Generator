package ca.hamann.mapgen.containers;

import java.util.TreeSet;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class LocationSet extends AbstractLocationCollection {

	public LocationSet() {
		super(new TreeSet<SinusoidalLocation>());
	}

}
