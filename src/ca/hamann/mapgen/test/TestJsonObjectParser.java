package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.persistence.load.json.JsonObject;
import ca.hamann.mapgen.persistence.load.json.JsonObjectParser;

public class TestJsonObjectParser extends TestCase {
	JsonObjectParser parser = new JsonObjectParser();

	public void testParse() throws Exception {
		JsonObject obj = parser
				.parse("{\"name1\" : value1, \"name2\" : value2}");

		assertEquals("value1", obj.getValue("name1"));
		assertEquals("value2", obj.getValue("name2"));
	}

	public void testParseWithNestedObject() throws Exception {
		JsonObject obj = parser
				.parse("{\"name1\" : { \"name3\" : value3 , \"name4\" : value4 }, \"name2\" : value2}");

		assertEquals("{ \"name3\" : value3 , \"name4\" : value4 }",
				obj.getValue("name1"));
		assertEquals("value2", obj.getValue("name2"));
	}

	public void testParseWithExtraSpaces() throws Exception {
		JsonObject obj = parser
				.parse(" {\"name1\" : value1, \"name2\" : value2 } ");

		assertEquals("value1", obj.getValue("name1"));
		assertEquals("value2", obj.getValue("name2"));
	}

	public void testParseFailsWithOrphanedRightBracket() throws Exception {
		try {
			parser.parse(" \"name1\" : value1, \"name2\" : value2 } , junk");
		} catch (Exception e) {
			return;
		}
		fail("Expected exception");
	}

	public void testParseWithBlankInput() throws Exception {
		assertTrue(parser.parse(" ").isEmpty());
	}
}
