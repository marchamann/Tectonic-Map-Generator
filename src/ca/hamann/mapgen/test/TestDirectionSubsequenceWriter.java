package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.direction.DirectionSubSequence;
import ca.hamann.mapgen.direction.MapDirection;
import ca.hamann.mapgen.persistence.save.json.DirectionSubsequenceWriter;

public class TestDirectionSubsequenceWriter extends TestCase {
	public void testWrite() throws Exception {

		DirectionSubSequence sequence = new DirectionSubSequence(
				MapDirection.NORTH, 1);
		DirectionSubsequenceWriter writer = new DirectionSubsequenceWriter();

		assertEquals("{ direction : NORTH, repetitionCount : 1 }", writer.write(sequence));

	}
}
