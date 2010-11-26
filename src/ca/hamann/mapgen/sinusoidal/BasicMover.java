package ca.hamann.mapgen.sinusoidal;

import java.util.TreeMap;

public class BasicMover implements SinusoidalMover {

  protected SinusoidalGrid grid;
  private TreeMap moveEastMap;

  public BasicMover(SinusoidalGrid grid) {
    this.grid = grid;
    moveEastMap = new TreeMap();
  }

  public SinusoidalLocation moveNorth(SinusoidalLocation loc) {
    return moveVertical(loc, false);
  }

  public SinusoidalLocation moveSouth(SinusoidalLocation loc) {
    return moveVertical(loc, true);
  }

  public SinusoidalLocation moveEast(SinusoidalLocation loc) {
      SinusoidalLocation result = //null;
   (SinusoidalLocation) moveEastMap.get(loc);
    if (result == null) {
      result = moveHorizontal(loc, true);
      moveEastMap.put(loc, result);
    }
    return result;
  }

  public SinusoidalLocation moveWest(SinusoidalLocation loc) {
    return moveHorizontal(loc, false);
  }

  public SinusoidalLocation moveHorizontal(
    SinusoidalLocation loc,
    boolean east) {
    int deltaX = -1;

    if (east) {
      deltaX = 1;
    }

    int oldX = loc.getX();
    int oldY = loc.getY();
    int newSpear = loc.getSpear();
    int newX = oldX + deltaX;
    if (oldX == (deltaX * grid.getMaxXatY(oldY))) {
      newSpear = grid.moveHorizontalOneSpear(newSpear, east);
      newX = -oldX;
    }
    return grid.getLocation(newSpear, oldY, newX);
  }

  public SinusoidalLocation moveVertical(
    SinusoidalLocation loc,
    boolean south) {
    int oldY = loc.getY();
    int newY = grid.moveOneY(oldY, south);
    int newSpear = loc.getSpear();
    int newX = loc.getX();
    int maxX = grid.getMaxXatY(newY);
    if (Math.abs(newX) - Math.abs(maxX) > 0) {
      if (newX < 0) {
        newX = -maxX;
      } else {
        newX = maxX;
      }
    }
    return grid.getLocation(newSpear, newY, newX);
  }

  public SinusoidalLocation moveEastFirstTime(SinusoidalLocation loc) {
    int deltaX = 1;

    int oldX = loc.getX();
    int oldY = loc.getY();
    int newSpear = loc.getSpear();
    int newX = oldX + deltaX;
    if (oldX == (deltaX * grid.getMaxXatY(oldY))) {
      newSpear = grid.moveHorizontalOneSpear(newSpear, true);
      newX = -oldX;
    }
    return new SinusoidalLocation(newSpear, oldY, newX);
  }

}
