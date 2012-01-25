package ca.hamann.mapgen.test;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import junit.framework.TestCase;
import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.persistence.load.TectonicMapReader;
import ca.hamann.mapgen.persistence.save.TectonicMapWriter;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.tectonic.Plate;
import ca.hamann.mapgen.tectonic.TectonicMap;
import ca.hamann.mapgen.tectonic.TectonicPlates;
import ca.hamann.mapgen.tectonic.TectonicValues;

public class TestTectonicMapReader extends TestCase {
	public void testRead() throws Exception {

		TectonicMapWriter writer = new TectonicMapWriter();
		MapConfiguration config = new MapConfiguration(0);
		config.setBaseLandElevation(1);
		config.setPlateCount(2);
		TectonicMap map = new TectonicMap(config);
		Plate plate = map.getPlates().getPlate(1);
		plate.setCount(3);

		LocationIterator iterator = map.getGrid().iterator();

		SinusoidalLocation initialLocation = iterator.next();

		SinusoidalLocation lastLocation = null;
		while (iterator.hasNext()) {
			lastLocation = iterator.next();
		}

		TectonicValues values = new TectonicValues();
		values.setPlateIndex(4);
		values.setCollisions(5);
		values.setElevation(6);

		map.setTectonicValues(initialLocation, values);
		map.setTectonicValues(lastLocation, values);

		map.setAccumulatedWater(initialLocation, 7);
		map.setFlow(initialLocation, 8);

		TectonicMapReader reader = new TectonicMapReader();

		StringWriter output = new StringWriter();
		writer.write(output, map);

		Reader source = new StringReader(output.toString());

		TectonicMap result = reader.read(source);

		MapConfiguration resultConfig = result.getConfiguration();
		TectonicPlates resultPlates = result.getPlates();

		assertEquals(config.getHalfSpearWidth(),
				resultConfig.getHalfSpearWidth());
		assertEquals(config.getBaseLandElevation(),
				resultConfig.getBaseLandElevation());
		assertEquals(plate.getCount(), resultPlates.getPlate(1).getCount());

		assertEquals(4, result.getPlateIndex(initialLocation));
		assertEquals(5, result.getCollisions(initialLocation));
		assertEquals(6, result.getElevation(initialLocation));
		assertEquals(7, result.getAccumulatedWater(initialLocation));
		assertEquals(8, result.getFlow(initialLocation));

		assertEquals(4, result.getPlateIndex(lastLocation));

	}
}
