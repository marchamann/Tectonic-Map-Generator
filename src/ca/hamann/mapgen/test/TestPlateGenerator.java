package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.containers.LocationList;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.tectonic.PlateGenerator;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class TestPlateGenerator extends TestCase {

	private SinusoidalGrid map;
	private TectonicMap tectMap;
	private PlateGenerator gen;
	private MapConfiguration config;

	protected void setUp() throws Exception {
		config = new MapConfiguration(15);
		map = config.getGrid();
		gen = new PlateGenerator(config);
		tectMap = gen.getTectonicMap();
	}

	protected void tearDown() throws Exception {
		map = null;
		gen = null;
		tectMap = null;
	}

	public void testSeedPlates() {
		gen.seedPlates();

		assertEquals(20, tectMap.countFilledPlateLocations());
	}

	public void testSetPlateAtLoc() {
		SinusoidalLocation loc = map.getLocation(0, 0, 0);
		tectMap = gen.setPlateAtLoc(loc, 1);

		assertEquals(1, tectMap.countFilledPlateLocations());

		assertEquals(8, gen.getNeighbourSet(1).size());
	}

	public void testSpreadPlateByOne() {
		gen.seedPlates();
		assertNotNull(gen.spreadPlateByOne(1));

		assertEquals(21, tectMap.countFilledPlateLocations());
		assertTrue(gen.getNeighbourSet(1).size() > 4);
	}

	public void testSpreadPlateByOneWithEmptyEdgeSet() {
		assertNull(gen.spreadPlateByOne(1));
	}

	public void testSpreadPlateByOneWithAllEdgeSetFilled() {
		LocationList neighbours = gen.getNeighbourSet(1);
		SinusoidalLocation loc = map.getInitialLocation();
		neighbours.add(loc);

		tectMap.setPlateIndex(loc, 1);

		assertNull(gen.spreadPlateByOne(1));
	}

	public void testLazySpreadPlateByOneWithAllEdgeSetFilled() {
		config.setLazySpreading(true);
		gen = new PlateGenerator(config);
		LocationList neighbours = gen.getNeighbourSet(1);
		SinusoidalLocation loc = map.getInitialLocation();
		neighbours.add(loc);

		tectMap.setPlateIndex(loc, 1);

		assertNotNull(gen.spreadPlateByOne(1));
	}

	public void testFloodFillPlates() {
		gen.seedPlates();
		tectMap = gen.generatePlates();

		assertEquals(map.getTotalLocations(),
				tectMap.countFilledPlateLocations());
	}

	public void testSeedPolarRegions() {
		tectMap = gen.seedPolarRegions();

		assertEquals(-1, tectMap.getPlateIndex(map.getInitialLocation()));
		assertEquals(-1,
				tectMap.getPlateIndex(map.getLocation(0, -map.getMaxY(), 0)));
	}

}
