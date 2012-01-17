package ca.hamann.mapgen.test;

import java.util.List;

import junit.framework.TestCase;
import ca.hamann.mapgen.persistence.load.json.JsonArrayParser;
import ca.hamann.mapgen.persistence.load.json.ValueParser;

public class TestJsonArrayParser extends TestCase {

	private JsonArrayParser<String> parser;

	@Override
	protected void setUp() throws Exception {
		parser = new JsonArrayParser<String>();
		parser.setParser(new ValueParser<String>() {
			@Override
			public String parse(String input) {
				return input;
			}
		});
	}

	public void testParse() throws Exception {
		List<String> result = parser.parse("[value1, value2]");
		assertEquals("value1", result.get(0));
		assertEquals("value2", result.get(1));
	}

	public void testParseWithFormattingVariation() throws Exception {
		List<String> result = parser.parse(" [ value1 , value2 ] ");
		assertEquals("value1", result.get(0));
		assertEquals("value2", result.get(1));
	}

}
