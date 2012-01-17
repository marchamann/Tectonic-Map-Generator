package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.direction.DirectionSubSequence;
import ca.hamann.mapgen.direction.MapDirection;
import ca.hamann.mapgen.persistence.load.DirectionSubSequenceParser;
import ca.hamann.mapgen.persistence.save.json.DirectionSubsequenceWriter;

public class TestDirectionSubSequenceParser extends TestCase {

	public void testParse() throws Exception {
		DirectionSubsequenceWriter writer = new DirectionSubsequenceWriter();
		DirectionSubSequenceParser parser = new DirectionSubSequenceParser();

		DirectionSubSequence seq1 = new DirectionSubSequence(
				MapDirection.IDENTITY, 2);

		DirectionSubSequence result = parser.parse(writer.write(seq1));
		assertEquals(MapDirection.IDENTITY, result.getDirection());
		assertEquals(2, result.getRepetitionCount());
	}
}
