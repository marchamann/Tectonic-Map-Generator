package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.direction.DirectionSequence;
import ca.hamann.mapgen.direction.MapDirection;
import ca.hamann.mapgen.persistence.load.PlateParser;
import ca.hamann.mapgen.persistence.save.PlateWriter;
import ca.hamann.mapgen.tectonic.Plate;

public class TestPlateParser extends TestCase {
	public void testParse() throws Exception {
		PlateParser parser = new PlateParser();

		Plate plate = new Plate(1);
		plate.setCount(2);
		DirectionSequence sequence = new DirectionSequence();
		sequence.append(MapDirection.SOUTH);
		sequence.append(MapDirection.NORTH);

		plate.setDirectionSequence(sequence);

		PlateWriter writer = new PlateWriter();

		Plate result = parser.parse(writer.write(plate));

		assertEquals(1, result.getIndex());
		assertEquals(2, result.getCount());

		DirectionSequence seq = result.getDirectionSequence();

		assertEquals(MapDirection.SOUTH, seq.getNextDirection());
		assertEquals(MapDirection.NORTH, seq.getNextDirection());
	}
}
