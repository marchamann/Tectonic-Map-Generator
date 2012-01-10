package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.persistence.load.MapConfigurationParser;
import ca.hamann.mapgen.persistence.save.MapConfigurationWriter;

public class TestMapConfigurationParser extends TestCase {
	public void testParse() throws Exception {

		MapConfiguration start = new MapConfiguration();
		start.setHalfSpearWidth(1);
		start.setPlateCount(2);
		start.setBaseLandElevation(3);
		start.setBaseSeaElevation(4);
		start.setStartingSeed(5);
		start.setStartingLandPlateCount(7);
		start.setLazySpreading(true);
		start.setPlateCreationMethod("test");

		MapConfigurationParser parser = new MapConfigurationParser();

		MapConfigurationWriter writer = new MapConfigurationWriter();

		MapConfiguration end = parser.parse(writer.write(start));

		assertEquals(start.getHalfSpearWidth(), end.getHalfSpearWidth());
		assertEquals(start.getPlateCount(), end.getPlateCount());
		assertEquals(start.getBaseLandElevation(), end.getBaseLandElevation());
		assertEquals(start.getBaseSeaElevation(), end.getBaseSeaElevation());
		assertEquals(start.getStartingSeed(), end.getStartingSeed());
		assertEquals(start.getStartingLandPlateCount(),
				end.getStartingLandPlateCount());
		assertTrue(end.isLazySpreading());
		assertEquals(start.getPlateCreationMethod(),
				end.getPlateCreationMethod());
	}
}
