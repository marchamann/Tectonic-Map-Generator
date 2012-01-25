package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.direction.DirectionSequence;
import ca.hamann.mapgen.direction.MapDirection;
import ca.hamann.mapgen.persistence.save.PlateWriter;
import ca.hamann.mapgen.tectonic.Plate;

public class TestPlateWriter extends TestCase {

	public void testWrite() throws Exception {
		Plate plate = new Plate(0);
		plate.setCount(1);
		DirectionSequence sequence = new DirectionSequence();
		sequence.append(MapDirection.NORTH);
		sequence.append(MapDirection.SOUTH);

		plate.setDirectionSequence(sequence);

		PlateWriter writer = new PlateWriter();

		assertEquals(
				"{ \"index\" : 0,\n"
						+ "\"count\" : 1,\n"
						+ "\"directionSequence\" : [ { \"direction\" : NORTH, \"repetitionCount\" : 1 },"
						+ "{ \"direction\" : SOUTH, \"repetitionCount\" : 1 } ]"
						+ " }", writer.write(plate));
	}
}
