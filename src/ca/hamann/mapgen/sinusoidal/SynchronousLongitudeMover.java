package ca.hamann.mapgen.sinusoidal;

import ca.hamann.mapgen.fillers.CylindricalLineFillers;

public class SynchronousLongitudeMover extends BasicMover {

  private SinusoidalMapStorage lateralIncrementsMap;
  private CylindricalLineFillers fillers;

  public SynchronousLongitudeMover(SinusoidalGrid grid) {
    super(grid);
    lateralIncrementsMap = new SinusoidalMapStorage(grid);
    fillers = new CylindricalLineFillers(grid);
  }

  public SinusoidalLocation moveEast(SinusoidalLocation loc) {
    return moveLateral(loc, 1);
  }

  public SinusoidalLocation moveWest(SinusoidalLocation loc) {
    return moveLateral(loc, -1);
  }

  private int getIncrements(SinusoidalLocation loc) {
    int deltaX = grid.getWidthOrdinal(loc);
    return fillers.getLineFiller(loc.getY()).howMany(deltaX);
  }

  private SinusoidalLocation moveLateral(
    SinusoidalLocation loc,
    int increment) {
    SinusoidalLocation result = loc;
    int increments = getLateralIncrements(loc);
    increments += increment;
    if (Math.abs(increments) >= getIncrements(loc)) {
      result = grid.getMover().moveEast(loc);
      if (increments < 0) {
        result = grid.getMover().moveWest(loc);
      }
      increments = 0;
    }
    setLateralIncrements(loc, increments);
    return result;
  }

  public int getLateralIncrements(SinusoidalLocation loc) {
    return lateralIncrementsMap.getValue(loc);
  }

  public void setLateralIncrements(SinusoidalLocation loc, int value) {
    lateralIncrementsMap.putValue(loc, value);
  }

}
