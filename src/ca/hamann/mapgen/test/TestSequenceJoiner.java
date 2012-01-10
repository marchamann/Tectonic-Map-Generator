package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.persistence.save.json.SequenceJoiner;

public class TestSequenceJoiner extends TestCase {
	private SequenceJoiner joiner;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		joiner = new SequenceJoiner();
	}
	
	public void testEmptySequence() throws Exception {
		assertEquals("", joiner.join());
	}

	public void testJoin() throws Exception {
		joiner.addString("1");
		joiner.addString("2");
		joiner.addString("3");
		
		assertEquals("1,2,3", joiner.join());
		
		joiner.setDelimiter(",\n");
		assertEquals("1,\n2,\n3", joiner.join());
	}

}
