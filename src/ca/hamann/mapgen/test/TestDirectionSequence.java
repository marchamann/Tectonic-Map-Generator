package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.direction.DirectionSequence;
import ca.hamann.mapgen.direction.MapDirection;

public class TestDirectionSequence extends TestCase {

	private DirectionSequence seq;
	private MapDirection north = MapDirection.NORTH;
	private MapDirection south = MapDirection.SOUTH;

	protected void setUp() throws Exception {
		seq = new DirectionSequence();
	}

	protected void tearDown() throws Exception {
		seq = null;
	}

	public void testGetCurrentDirection() {
		seq.append(north);

		assertEquals(north, seq.getCurrentDirection());
	}

	public void testGetNextDirection() {
		seq.append(north);
		seq.append(south);

		assertEquals(north, seq.getNextDirection());
		assertEquals(south, seq.getNextDirection());
		assertEquals(north, seq.getNextDirection());
	}

}
