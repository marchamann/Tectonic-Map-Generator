package ca.hamann.mapgen.test;

import junit.framework.TestCase;
import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.direction.DirectionSequence;
import ca.hamann.mapgen.direction.MapDirection;
import ca.hamann.mapgen.sinusoidal.BasicMover;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.tectonic.EmptyLocationFiller;
import ca.hamann.mapgen.tectonic.PlateDrifter;
import ca.hamann.mapgen.tectonic.PlateGenerator;
import ca.hamann.mapgen.tectonic.TectonicMap;
import ca.hamann.mapgen.tectonic.TectonicPlates;

public class TestPlateDrifter extends TestCase {

	private BasicMover mover;
	protected SinusoidalGrid map;
	private MapConfiguration config;
	protected TectonicMap tectMap;
	private SinusoidalLocation loc, eastLoc;
	private PlateDrifter drifter;
	private DirectionSequence eastSequence, westSequence, northSequence;

	protected void setUp() throws Exception {
		config = new MapConfiguration(5);
		config.setPlateCount(2);
		map = config.getGrid();
		mover = map.getMover();

		tectMap = new TectonicMap(config);
		tectMap.setPlates(new TectonicPlates(10));

		loc = map.getInitialLocation();
		eastLoc = mover.moveEast(loc);
		mover.moveNorth(loc);
		mover.moveSouth(loc);

		drifter = new PlateDrifter(tectMap);

		eastSequence = new DirectionSequence();
		eastSequence.append(MapDirection.EAST);

		westSequence = new DirectionSequence();
		westSequence.append(MapDirection.WEST);

		northSequence = new DirectionSequence();
		northSequence.append(MapDirection.NORTH);

	}

	protected void tearDown() throws Exception {
		map = null;
		tectMap = null;

		loc = null;
		eastLoc = null;
		drifter = null;

		eastSequence = null;
		westSequence = null;
		northSequence = null;
	}

	public void testMoveTectonicLocationeEmptyDestination() {
		tectMap.setPlateIndex(loc, 1);
		assertEquals(1, drifter.moveTectonicLocation(loc, eastLoc));
	}

	public void testMoveTectonicLocationOccupiedSamePlateDestination() {
		tectMap.setPlateIndex(loc, 1);
		tectMap.setCollisions(loc, 1);
		drifter.setPlateIndex(eastLoc, 1);
		drifter.setCollisions(eastLoc, 1);
		drifter.moveTectonicLocation(loc, eastLoc);
		assertEquals(1, drifter.getCollisions(eastLoc));
	}

	public void testMoveTectonicLocationOccupiedLessDenseDestination() {
		tectMap.setPlateIndex(loc, 2);
		drifter.setPlateIndex(eastLoc, 1);
		assertEquals(2, drifter.moveTectonicLocation(loc, eastLoc));
		assertEquals(config.getCollisionIncrement(),
				drifter.getCollisions(eastLoc));
	}

	public void testDriftLocation() {
		drifter.setDirectionSequence(eastSequence, 1);

		tectMap.setPlateIndex(loc, 1);
		tectMap.setCollisions(loc, 3);
		drifter.initializeNewToMap();
		drifter.driftLocation(loc);

		assertEquals(1, drifter.getPlateIndex(eastLoc));
		assertEquals(3, drifter.getCollisions(eastLoc));
	}

	public void testDriftMap() {
		PlateGenerator gen = new PlateGenerator(config);
		gen.seedPlates();
		tectMap = gen.generatePlates();
		drifter = new PlateDrifter(tectMap);
		drifter.setDirectionSequence(eastSequence, 1);
		drifter.setDirectionSequence(westSequence, 2);

		tectMap = drifter.driftMap();

		int emptyLocations = map.getTotalLocations()
				- tectMap.countFilledPlateLocations();

		assertTrue(emptyLocations == 0);
	}

	public void testFillEmptyLocation() {

		LocationCollection neighbours = map.getNeighbourhoods().getNeighbours(
				loc);

		LocationIterator iterator = neighbours.iterator();
		while (iterator.hasNext()) {
			SinusoidalLocation neighbourLoc = iterator.next();
			drifter.setPlateIndex(neighbourLoc, 1);
			drifter.setCollisions(neighbourLoc, 2);
		}
		EmptyLocationFiller filler = new EmptyLocationFiller(drifter.getToMap());
		filler.fillEmptyLocation(loc);

		assertEquals(1, drifter.getPlateIndex(loc));
		assertEquals(-1, drifter.getCollisions(loc));

	}

}
