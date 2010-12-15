package ca.hamann.mapgen.containers;

import java.util.ArrayList;
import java.util.List;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class LocationList extends AbstractLocationCollection {

	private List<SinusoidalLocation> list;

	public LocationList() {
		super(new ArrayList<SinusoidalLocation>());
		list = (List<SinusoidalLocation>) locations;
	}

	public SinusoidalLocation remove(int index) {
		return list.remove(index);
	}

	public SinusoidalLocation get(int index) {
		return list.get(index);
	}
}
