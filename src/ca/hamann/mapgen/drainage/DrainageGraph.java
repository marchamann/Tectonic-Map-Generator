package ca.hamann.mapgen.drainage;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import ca.hamann.mapgen.containers.LocationSet;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class DrainageGraph {

  private DrainageProcessor processor;
  private Map graphMap;

  public DrainageGraph(DrainageProcessor processor) {
    graphMap = new TreeMap();
    this.processor = processor;
  }

  public void addDrainageArrow(
    SinusoidalLocation from,
    SinusoidalLocation to) {
//    if (graphMap.containsKey(from)) {
//      throw new RuntimeException(
//        "Duplicate key: "
//          + from
//          + " New to: "
//          + to
//          + " Old to: "
//          + graphMap.get(from));
//    }
    graphMap.put(from, to);
  }

  public SinusoidalLocation getDrainageLocation(SinusoidalLocation from) {
    return (SinusoidalLocation) graphMap.get(from);
  }

  public SinusoidalLocation getTransitiveTerminus(SinusoidalLocation loc) {
    LocationSet visited = new LocationSet();
    SinusoidalLocation result = getDrainageLocation(loc);
    SinusoidalLocation lastResult = loc;
    while (result != null) {
      if (visited.contains(result)) {
        return null;
      }
      visited.add(result);
      lastResult = result;
      result = getDrainageLocation(result);
    }
    return lastResult;
  }

  public boolean validate() {
    Iterator iterator = graphMap.keySet().iterator();

    while (iterator.hasNext()) {
      SinusoidalLocation next = (SinusoidalLocation) iterator.next();
      SinusoidalLocation terminus = getTransitiveTerminus(next);
      if (terminus == null || !processor.isNextToSea(terminus)) {
        return false;
      }
    }
    return true;
  }

  public boolean checkIncreasingFlow() {
    Iterator iterator = graphMap.keySet().iterator();

    while (iterator.hasNext()) {
      SinusoidalLocation next = (SinusoidalLocation) iterator.next();
      SinusoidalLocation drain = getDrainageLocation(next);
      if (processor.getFlow(drain) <= processor.getFlow(next)) {
        return false;
      }
    }
    return true;
  }
}
