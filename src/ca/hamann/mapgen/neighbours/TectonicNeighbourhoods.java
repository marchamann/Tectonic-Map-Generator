package ca.hamann.mapgen.neighbours;

import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.containers.LocationList;
import ca.hamann.mapgen.containers.LocationSet;
import ca.hamann.mapgen.containers.traversal.AbstractLocationFilter;
import ca.hamann.mapgen.containers.traversal.LocationContainerTraversal;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class TectonicNeighbourhoods extends Neighbourhoods {

  private final class LowestElevationMatchingFilter
    extends AbstractLocationFilter {
    public boolean filterLocation(SinusoidalLocation loc) {
      return tectMap.getElevation(loc) == lowestElevation;
    }
  }

  private TectonicMap tectMap;

  private int lowestElevation;

  public TectonicNeighbourhoods(TectonicMap tectMap) {
    super(tectMap.getBasicMover());
    this.tectMap = tectMap;
  }

  public LocationList getElevationOrderedNeighbours(SinusoidalLocation loc) {
    return getSortedNeighbours(loc, new ElevationComparator(tectMap));
  }

  public LocationCollection getLowestNeighbours(SinusoidalLocation loc) {
    LocationList neighbours = getElevationOrderedNeighbours(loc);
    LocationContainerTraversal traversal =
      new LocationContainerTraversal(neighbours);

    SinusoidalLocation lowest = neighbours.get(0);

    lowestElevation = tectMap.getElevation(lowest);

    if (lowestElevation > tectMap.getElevation(loc)) {
      return new LocationSet();
    }

    LocationCollection result =
      traversal.filter(new LowestElevationMatchingFilter());

    return result;
  }

  public SinusoidalLocation getLowestNeighbour(SinusoidalLocation loc) {
    LocationList neighbours = getElevationOrderedNeighbours(loc);
    return neighbours.get(0);
  }

}
