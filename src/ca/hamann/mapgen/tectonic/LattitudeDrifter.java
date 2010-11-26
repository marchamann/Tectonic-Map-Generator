package ca.hamann.mapgen.tectonic;

import ca.hamann.mapgen.direction.*;
import ca.hamann.mapgen.sinusoidal.BasicMover;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;

public class LattitudeDrifter {
  private BasicMover mover;
  private TectonicMap fromMap, toMap;
  private PlateDrifter pDrifter;
  private SinusoidalGrid map;

  public LattitudeDrifter(PlateDrifter drifter) {
    this.pDrifter = drifter;
    this.fromMap = drifter.fromMap;
    this.toMap = drifter.toMap;
    this.map = drifter.grid;

    mover = map.getMover();
  }

  public TectonicMap moveLattitude(int lattitude) {
    SinusoidalLocation start =
      getStartOfPlateLattitude(map.getLocation(0, lattitude, 0));
    SinusoidalLocation startSegment = start, end, boundary;

    boundary = mover.moveWest(start);

    do {
      end = getEndOfPlateLattitude(startSegment);
      MapDirection direction =
        pDrifter.getCurrentDirection(fromMap.getPlateIndex(startSegment));
      SinusoidalLocation newLoc =
        direction.move(fromMap.getMover(), startSegment);
      moveContiguousPlateLocations(startSegment, newLoc, end);
      startSegment = mover.moveEast(end);

    } while (!end.equals(boundary) && !start.equals(startSegment));

    return toMap;
  }

  public SinusoidalLocation getStartOfPlateLattitude(SinusoidalLocation loc) {
    SinusoidalLocation boundary = mover.moveEast(loc);
    SinusoidalLocation result =
      getFurthestContiguousInDirection(loc, MapDirection.WEST, boundary);
    if (result.equals(boundary)) {
      result = loc;
    }
    return result;
  }

  public SinusoidalLocation getEndOfPlateLattitude(SinusoidalLocation loc) {
    return getFurthestContiguousInDirection(
      loc,
      MapDirection.EAST,
      mover.moveWest(loc));
  }

  public SinusoidalLocation getFurthestContiguousInDirection(
    SinusoidalLocation loc,
    MapDirection direction,
    SinusoidalLocation boundary) {
    SinusoidalLocation nextLoc = direction.move(map.getMover(), loc);

    int plateIndex = fromMap.getPlateIndex(loc);
    int nextPlate = fromMap.getPlateIndex(nextLoc);
    if (nextPlate != plateIndex || loc.equals(boundary)) {
      return loc;
    }

    return getFurthestContiguousInDirection(nextLoc, direction, boundary);
  }

  public void moveContiguousPlateLocations(
    SinusoidalLocation start,
    SinusoidalLocation movedStart,
    SinusoidalLocation end) {
    pDrifter.moveTectonicLocation(start, movedStart);

    if (start.equals(end)) {
      return;
    }

    moveContiguousPlateLocations(
      mover.moveEast(start),
      mover.moveEast(movedStart),
      end);
  }

}
