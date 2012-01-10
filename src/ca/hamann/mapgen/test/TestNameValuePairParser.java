package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.persistence.load.json.NameValuePair;
import ca.hamann.mapgen.persistence.load.json.NameValuePairParser;

public class TestNameValuePairParser extends TestCase {

	private NameValuePairParser parser;
	private NameValuePair pair;

	@Override
	protected void setUp() throws Exception {
		parser = new NameValuePairParser();
	}

	public void testParse() throws Exception {
		pair = parser.parse("\"name\" : value");

		assertEquals("name", pair.getName());
		assertEquals("value", pair.getValue());
	}

	public void testParseNameWithExtraSpace() throws Exception {
		pair = parser.parse("\" name\" : value");

		assertEquals(" name", pair.getName());
	}

}
