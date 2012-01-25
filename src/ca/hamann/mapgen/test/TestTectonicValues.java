package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.tectonic.TectonicValues;

public class TestTectonicValues extends TestCase {

	private TectonicValues values;

	protected void setUp() throws Exception {
		values = new TectonicValues();
	}

	protected void tearDown() throws Exception {
		values = null;
	}

	public void testPlateIndex() {
		int index = 1;
		values.setPlateIndex(index);
		assertEquals(index, values.getPlateIndex());
	}

	public void testCollisions() {
		int collisions = 1;
		values.setCollisions(collisions);
		assertEquals(collisions, values.getCollisions());
	}

	public void testElevation() {
		int elevation = 1;
		values.setElevation(elevation);
		assertEquals(elevation, values.getElevation());
	}

}
