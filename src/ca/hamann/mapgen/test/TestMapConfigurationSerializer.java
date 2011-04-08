package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.serialize.MapConfigurationSerializer;

public class TestMapConfigurationSerializer extends TestCase {
	private MapConfiguration config;
	private MapConfigurationSerializer serializer;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		config = new MapConfiguration();
		serializer = new MapConfigurationSerializer();
	}

	public void testSerialize() throws Exception {
		config.setHalfSpearWidth(1);
		config.setPlateCount(2);
		assertEquals("{" + serializer.formatMemberPair("halfSpearWidth", "1")
				+ "," + serializer.formatMemberPair("plateCount", "2") + "}",
				serializer.serialize(config));
	}

	public void testFormatMemberPair() throws Exception {
		assertEquals("name : value",
				serializer.formatMemberPair("name", "value"));
	}

	public void testFormatIntegerMemberPair() throws Exception {
		assertEquals("name : 1", serializer.formatMemberPair("name", 1));
	}

}
