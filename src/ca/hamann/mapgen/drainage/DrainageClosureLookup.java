package ca.hamann.mapgen.drainage;

import java.util.Map;
import java.util.TreeMap;

import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.containers.LocationSet;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class DrainageClosureLookup {
  private Map closures;

  public DrainageClosureLookup() {
    closures = new TreeMap();
  }

  public void addToClosure(SinusoidalLocation key, SinusoidalLocation member) {
    LocationCollection closure = getClosure(key);
    if (closure == null) {
      closure = new LocationSet();
      closures.put(key, closure);
    }
    closure.add(member);
  }

  private LocationCollection getClosure(SinusoidalLocation key) {
    return (LocationCollection) closures.get(key);
  }
}
