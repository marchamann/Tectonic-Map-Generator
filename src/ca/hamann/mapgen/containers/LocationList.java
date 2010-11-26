package ca.hamann.mapgen.containers;

import java.util.ArrayList;
import java.util.List;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class LocationList extends AbstractLocationCollection {

  private List list;

  public LocationList() {
    super(new ArrayList());
    list = (List) locations;
  }

  public SinusoidalLocation remove(int index) {
    return (SinusoidalLocation) list.remove(index);
  }

  public SinusoidalLocation get(int index) {
    return (SinusoidalLocation) list.get(index);
  }
}
