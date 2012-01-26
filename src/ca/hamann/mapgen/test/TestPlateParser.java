package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.direction.DirectionSequence;
import ca.hamann.mapgen.direction.MapDirection;
import ca.hamann.mapgen.persistence.load.PlateParser;
import ca.hamann.mapgen.persistence.save.PlateWriter;
import ca.hamann.mapgen.tectonic.Plate;

public class TestPlateParser extends TestCase {

	private PlateParser parser;
	private Plate plate;
	private PlateWriter writer;

	@Override
	protected void setUp() throws Exception {
		parser = new PlateParser();
		plate = new Plate(1);
		writer = new PlateWriter();
	}

	public void testParse() throws Exception {

		plate.setCount(2);
		DirectionSequence sequence = new DirectionSequence();
		sequence.append(MapDirection.SOUTH);
		sequence.append(MapDirection.NORTH);

		plate.setDirectionSequence(sequence);

		Plate result = parser.parse(writer.write(plate));

		assertEquals(1, result.getIndex());
		assertEquals(2, result.getCount());

		DirectionSequence seq = result.getDirectionSequence();

		assertEquals(MapDirection.SOUTH, seq.getNextDirection());
		assertEquals(MapDirection.NORTH, seq.getNextDirection());
	}

	public void testParseWithEmptyDirectionSequence() throws Exception {

		Plate result = parser.parse(writer.write(plate));
		assertNotNull(result.getDirectionSequence().getNextDirection());

	}

}
