package ca.hamann.mapgen.test;

import ca.hamann.mapgen.tectonic.SubductionRelation;
import junit.framework.TestCase;

public class TestSubductionRelation extends TestCase {

  protected void setUp() throws Exception {
  }

  protected void tearDown() throws Exception {
  }

  public void testCompareTo() {
    SubductionRelation rel1, rel2;

    rel1 = new SubductionRelation(1, 2);
    rel2 = rel1;

    assertEquals(0, rel1.compareTo(rel2));

    rel2 = new SubductionRelation(1, 1);

    assertEquals(1, rel1.compareTo(rel2));

    rel2 = new SubductionRelation(2, 1);

    assertEquals(-1, rel1.compareTo(rel2));
  }

}
