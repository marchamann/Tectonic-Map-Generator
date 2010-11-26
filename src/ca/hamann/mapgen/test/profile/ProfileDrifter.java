package ca.hamann.mapgen.test.profile;

import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.tectonic.PlateDrifter;
import ca.hamann.mapgen.tectonic.PlateGenerator;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class ProfileDrifter {

  private static TectonicMap tectMap;

  public static void main(String[] args) {
    PlateGenerator gen = new PlateGenerator(new MapConfiguration(1));

    tectMap = gen.floodFillPlates();

    PlateDrifter drifter = new PlateDrifter(tectMap);

    System.out.println("Started drifting...");

    for (int i = 0; i < 5; i++) {
      tectMap = drifter.driftMap();
    }
  }
}
