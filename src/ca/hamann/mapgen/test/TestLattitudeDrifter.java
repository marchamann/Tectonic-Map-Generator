package ca.hamann.mapgen.test;

import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.direction.DirectionSequenceGenerator;
import ca.hamann.mapgen.sinusoidal.BasicMover;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import ca.hamann.mapgen.tectonic.LattitudeDrifter;
import ca.hamann.mapgen.tectonic.PlateDrifter;
import ca.hamann.mapgen.tectonic.TectonicMap;
import ca.hamann.mapgen.tectonic.TectonicPlates;
import junit.framework.TestCase;

public class TestLattitudeDrifter extends TestCase {

  private BasicMover mover;
  private DirectionSequenceGenerator gen;
  private SinusoidalLocation start, movedStart, end;
  private LattitudeDrifter lattDrifter;
  private PlateDrifter drifter;
  protected SinusoidalGrid map;
  protected TectonicMap tectMap;

  protected void setUp() throws Exception {
    map = new SinusoidalGrid(15);
    mover = map.getMover();
    tectMap = new TectonicMap(new MapConfiguration(15));
    tectMap.setPlates(new TectonicPlates(10));
    drifter = new PlateDrifter(tectMap);
    lattDrifter = new LattitudeDrifter(drifter);

    start = map.getInitialLocation();
    movedStart = mover.moveSouth(start);
    end = mover.moveEast(start);

    tectMap.setPlateIndex(start, 1);
    tectMap.setPlateIndex(end, 1);

    gen = new DirectionSequenceGenerator();
  }

  protected void tearDown() throws Exception {
    map = null;
    tectMap = null;
    drifter = null;
    lattDrifter = null;

    start = movedStart = end = null;

    gen = null;
  }

  public void testMoveContiguousPlateLocations() {
    lattDrifter.moveContiguousPlateLocations(start, movedStart, end);
    assertEquals(1, drifter.getPlateIndex(mover.moveEast(movedStart)));
  }

  public void testMoveContiguousPlateLocationsSizeOne() {
    lattDrifter.moveContiguousPlateLocations(start, movedStart, start);
    assertEquals(0, drifter.getPlateIndex(mover.moveEast(movedStart)));
  }

  public void testGetStartOfPlateLattitude() {
    assertEquals(start, lattDrifter.getStartOfPlateLattitude(end));
  }

  public void testGetEndOfPlateLattitude() {
    assertEquals(end, lattDrifter.getEndOfPlateLattitude(start));
  }

  public void testGetStartOfPlateLattitudeAllAround() {
    for (int i = 0; i < 7; i++) {
      tectMap.setPlateIndex(end, 1);
      end = mover.moveEast(end);
    }
    assertEquals(start, lattDrifter.getStartOfPlateLattitude(start));
  }

  public void testGetEndOfPlateLattitudeAllAround() {
    for (int i = 0; i < 7; i++) {
      tectMap.setPlateIndex(end, 1);
      end = mover.moveEast(end);
    }
    assertEquals(
      mover.moveWest(start),
      lattDrifter.getEndOfPlateLattitude(start));
  }

  public void testGetEndOfPlateLattitudeAllAroundButOne() {
    for (int i = 0; i < 7; i++) {
      tectMap.setPlateIndex(end, 2);
      end = mover.moveEast(end);
    }
    assertEquals(start, lattDrifter.getEndOfPlateLattitude(end));
  }

  public void testMoveLattitude() {
    for (int i = 1; i <= 8; i++) {
      tectMap.setPlateIndex(end, i);
      drifter.setDirectionSequence(gen.getEastSequence(), i);
      end = mover.moveEast(end);
    }

    TectonicMap toMap = lattDrifter.moveLattitude(map.getMaxY());

    assertEquals(8, toMap.countFilledPlateLocations());
  }

}
