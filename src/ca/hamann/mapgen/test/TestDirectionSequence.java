package ca.hamann.mapgen.test;

import ca.hamann.mapgen.direction.DirectionSequence;
import ca.hamann.mapgen.direction.DirectionSequenceGenerator;
import ca.hamann.mapgen.direction.MapDirection;
import junit.framework.TestCase;

public class TestDirectionSequence extends TestCase {

  private DirectionSequence seq;
  private MapDirection north = MapDirection.NORTH;
  private MapDirection south = MapDirection.SOUTH;

  private DirectionSequenceGenerator seqGen;

  protected void setUp() throws Exception {
    seq = new DirectionSequence();
    seqGen = new DirectionSequenceGenerator();
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

  public void testLongSequence() {
    seq = seqGen.getNorthClockwiseSequence();
    MapDirection lastDirection = null;
    for (int i = 0; i < 300; i++) {
      MapDirection direction = seq.getNextDirection();
      if (!direction.equals(lastDirection)) {
        System.out.println(direction);
      }
      lastDirection = direction;
    }
  }
}
