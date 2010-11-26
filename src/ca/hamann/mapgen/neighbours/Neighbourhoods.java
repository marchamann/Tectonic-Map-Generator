package ca.hamann.mapgen.neighbours;

import java.util.Comparator;
import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.containers.LocationList;
import ca.hamann.mapgen.containers.traversal.LocationContainerTraversal;
import ca.hamann.mapgen.direction.MapDirection;
import ca.hamann.mapgen.sinusoidal.BasicMover;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class Neighbourhoods {
  private BasicMover mover;

  public Neighbourhoods(BasicMover mover) {
    this.mover = mover;
  }

  public LocationCollection getCardinalNeighbours(SinusoidalLocation loc) {
    LocationList result = new LocationList();

    SinusoidalLocation[] neighbours =
      new SinusoidalLocation[] {
        mover.moveEast(loc),
        mover.moveSouth(loc),
        mover.moveWest(loc),
        mover.moveNorth(loc)};

    for (int i = 0; i < 4; i++) {
      SinusoidalLocation neighbour = neighbours[i];

      result.add(neighbour);
    }

    return result;
  }

  public LocationCollection getNeighbours(SinusoidalLocation loc) {
    LocationCollection neighbours = getCardinalNeighbours(loc);

    addNonDuplicate(MapDirection.NORTHEAST.move(mover, loc), neighbours);
    addNonDuplicate(MapDirection.SOUTHEAST.move(mover, loc), neighbours);
    addNonDuplicate(MapDirection.NORTHWEST.move(mover, loc), neighbours);
    addNonDuplicate(MapDirection.SOUTHWEST.move(mover, loc), neighbours);

    return neighbours;
  }

  public void addNonDuplicate(
    SinusoidalLocation loc,
    LocationCollection collection) {
    if (!collection.contains(loc))
      collection.add(loc);
  }

  public LocationContainerTraversal getNeighboursTraversal(SinusoidalLocation loc) {
    return new LocationContainerTraversal(getNeighbours(loc));
  }

  public LocationList getSortedNeighbours(
    SinusoidalLocation loc,
    Comparator comparator) {
    return getNeighbours(loc).getOrderedLocations(comparator);
  }
}
