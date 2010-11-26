package ca.hamann.mapgen.test;

import ca.hamann.mapgen.tectonic.TectonicPlates;
import junit.framework.TestCase;

public class TestTectonicPlates extends TestCase {

  protected void setUp() throws Exception {
  }

  protected void tearDown() throws Exception {
  }

  public void testLessDensePlate() {
    TectonicPlates plates = new TectonicPlates(2);

    plates.setSeaPlateCount(1);
    //    plates.setLandPlate(1);
    //    plates.setSeaPlate(2);

    assertEquals(2, plates.lessDensePlate(1, 2));
    assertEquals(2, plates.lessDensePlate(2, 1));

    plates.setSeaPlateCount(0);
    //    plates.setLandPlate(2);

    assertEquals(1, plates.lessDensePlate(1, 2));
    assertEquals(1, plates.lessDensePlate(2, 1));
  }

}
