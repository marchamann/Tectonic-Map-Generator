package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.persistence.save.MapConfigurationWriter;

public class TestMapConfigurationWriter extends TestCase {
	private MapConfiguration config;
	private MapConfigurationWriter writer;

	@Override
	protected void setUp() throws Exception {
		config = new MapConfiguration();
		writer = new MapConfigurationWriter();
	}

	public void testSerialize() throws Exception {
		config.setHalfSpearWidth(1);
		config.setPlateCount(2);
		config.setBaseLandElevation(3);
		config.setBaseSeaElevation(4);
		config.setStartingSeed(5);
		config.setStartingLandPlateCount(6);
		config.setLazySpreading(true);
		config.setPlateCreationMethod("test");

		String expected = "{ halfSpearWidth : 1, " + "plateCount : 2, "
				+ "baseLandElevation : 3, " + "baseSeaElevation : 4, "
				+ "startingSeed : 5, " + "startingLandPlateCount : 6, "
				+ "lazySpreading : true, " + "plateCreationMethod : test"
				+ " }";
		assertEquals(expected, writer.write(config));
	}

	public void testFormatMemberPair() throws Exception {
		assertEquals("name : value", writer.formatMemberPair("name", "value"));
	}

	public void testFormatIntegerMemberPair() throws Exception {
		assertEquals("name : 1", writer.formatMemberPair("name", 1));
	}

}
