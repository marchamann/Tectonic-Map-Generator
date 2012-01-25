package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.persistence.load.json.NextCommaParser;

public class TestNextCommaParser extends TestCase {
	private NextCommaParser parser = new NextCommaParser();

	public void testParseWithEmptyInput() throws Exception {
		assertEquals(-1, parser.parse(""));
	}

	public void testParseWithNormalInput() throws Exception {
		assertEquals(6, parser.parse("value1, value2"));
	}

	public void testParseWithNestedArray() throws Exception {
		assertEquals(17, parser.parse("[ value1, value2], value3"));
	}

	public void testParseWithNestedStringWithComma() throws Exception {
		assertEquals(9, parser.parse("\"string,\", value3"));
	}

	public void testParseWithNestedStringWithEscapedQuote() throws Exception {
		assertEquals(11, parser.parse("\"string\\\",\", value3"));
	}

	public void testParseWithNestedStringWithNestedObject() throws Exception {
		assertEquals(
				49,
				parser.parse("\"name1\" : { \"name3\" : value3 , \"name4\" : value4 }, \"name2\" : value2"));
	}

	public void testParseWithDeeplyNestedInput() throws Exception {
		assertEquals(29,
				parser.parse("[ {\"a\": [ b, c]}, \"value1,\" ], value2"));
	}

}
