package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.persistence.load.json.JsonObject;
import ca.hamann.mapgen.persistence.load.json.JsonObjectParser;

public class TestJsonObjectParser extends TestCase {
	public void testParse() throws Exception {
		JsonObjectParser parser = new JsonObjectParser();
		JsonObject obj = parser
				.parse("{\"name1\" : value1, \"name2\" : value2}");

		assertEquals("value1", obj.getValue("name1"));
		assertEquals("value2", obj.getValue("name2"));
	}
}
