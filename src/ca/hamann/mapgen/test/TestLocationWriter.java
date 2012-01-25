package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.persistence.save.LocationWriter;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class TestLocationWriter extends TestCase {
	public void testWrite() throws Exception {
		LocationWriter writer = new LocationWriter();
		TectonicMap map = new TectonicMap(new MapConfiguration());
		SinusoidalLocation loc = map.getGrid().getInitialLocation();

		map.setElevation(loc, 1);
		map.setAccumulatedWater(loc, 2);
		map.setFlow(loc, 3);

		assertEquals(
				"{ \"location\" : { \"spear\" : 0, \"x\" : 0, \"y\" : 2 },"
						+ "\n\"plate\" : 0," + "\n\"collisions\" : 0,"
						+ "\n\"elevation\" : 1,"
						+ "\n\"accumulatedWater\" : 2," + "\n\"flow\" : 3"
						+ " }", writer.write(map, loc));
	}
}
