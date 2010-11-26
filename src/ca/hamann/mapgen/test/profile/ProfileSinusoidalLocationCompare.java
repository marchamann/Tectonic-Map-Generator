package ca.hamann.mapgen.test.profile;

import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class ProfileSinusoidalLocationCompare {

  public static void main(String[] args) {

    SinusoidalGrid grid = new SinusoidalGrid(15);

    SinusoidalLocation loc1 = grid.getLocation(0, 0, 0);
    SinusoidalLocation loc2 = grid.getLocation(0, 0, 1);

    for (int i = 0; i < 10000; i++) {
      loc1.compareTo(loc2);
    }
  }
}
