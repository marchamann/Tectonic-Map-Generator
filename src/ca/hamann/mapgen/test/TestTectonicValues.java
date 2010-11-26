package ca.hamann.mapgen.test;

import ca.hamann.mapgen.tectonic.TectonicValues;
import junit.framework.TestCase;

public class TestTectonicValues extends TestCase {

  private TectonicValues values;

  protected void setUp() throws Exception {
    values = new TectonicValues();
  }

  protected void tearDown() throws Exception {
    values = null;
  }

  public void testPlateIndex() {
    int index = 1;
    values.setPlateIndex(index);
    assertEquals(index, values.getPlateIndex());
  }

  public void testCollisions() {
    int collisions = 1;
    values.setCollisions(collisions);
    assertEquals(collisions, values.getCollisions());
  }

  public void testElevation() {
    int elevation = 1;
    values.setElevation(elevation);
    assertEquals(elevation, values.getElevation());
  }

  public void testLateralIncrements() {
    int lateralIncrements = 1;
    values.setLateralIncrements(lateralIncrements);
    assertEquals(lateralIncrements, values.getLateralIncrements());
  }

//  public void testCollisionsAffectElevation() {
//    values.setElevation(0);
//    values.setCollisions(1);
//    assertEquals(1, values.getElevation());
//  }

}
