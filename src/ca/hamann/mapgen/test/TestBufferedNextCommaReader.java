package ca.hamann.mapgen.test;

import java.io.Reader;
import java.io.StringReader;

import junit.framework.TestCase;
import ca.hamann.mapgen.persistence.load.json.BufferedNextCommaReader;

public class TestBufferedNextCommaReader extends TestCase {
	public void testRead() throws Exception {

		BufferedNextCommaReader reader = new BufferedNextCommaReader();

		Reader source = new StringReader(
				"a,b,c                                     , d");

		assertEquals("a", reader.readToNextComma(source));
		assertEquals("b", reader.readToNextComma(source));
		assertEquals("c", reader.readToNextComma(source));
		assertEquals("d", reader.readToNextComma(source));

	}
}
