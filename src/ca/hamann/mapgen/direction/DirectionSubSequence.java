package ca.hamann.mapgen.direction;

public class DirectionSubSequence {

  private MapDirection direction;
  private int repetitionCount;
  private static final int INFINITY = -1;

  public DirectionSubSequence(MapDirection direction, int repetitionCount) {
    this.direction = direction;
    this.repetitionCount = repetitionCount;
  }

  public DirectionSubSequence(MapDirection direction) {
    this(direction, INFINITY);
  }

  public MapDirection getDirection() {
    return direction;
  }

  public int getRepetitionCount() {
    return repetitionCount;
  }

}
