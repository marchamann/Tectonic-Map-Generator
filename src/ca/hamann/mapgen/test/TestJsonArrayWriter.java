package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.persistence.save.json.JsonArrayWriter;
import ca.hamann.mapgen.persistence.save.json.SequenceJoiner;

public class TestJsonArrayWriter extends TestCase {
	public void testWrite() throws Exception {
		SequenceJoiner joiner = new SequenceJoiner();
		joiner.addString("value1");
		joiner.addString("value2");

		JsonArrayWriter writer = new JsonArrayWriter();
		assertEquals("[ value1,value2 ]", writer.write(joiner));

	}
}
