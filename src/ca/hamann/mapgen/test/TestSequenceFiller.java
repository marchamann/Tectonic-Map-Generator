package ca.hamann.mapgen.test;

import ca.hamann.mapgen.fillers.SequenceFiller;
import junit.framework.TestCase;

public class TestSequenceFiller extends TestCase {

  private SequenceFiller filler;

  protected void setUp() throws Exception {
  }

  protected void tearDown() throws Exception {
  }

  public void testHowMany1To2() {
    int inputSize = 1, outputSize = 2;
    int inputIndex = 0;

    filler = new SequenceFiller(inputSize, outputSize);
    assertEquals(2, filler.howMany(inputIndex));
  }

  public void testHowMany2To3() {
    int inputSize = 2, outputSize = 3;

    filler = new SequenceFiller(inputSize, outputSize);
    assertEquals(2, filler.howMany(0));
    assertEquals(1, filler.howMany(1));
  }

//    public void testInRangeTo1000() {
//  
//      for (int i = 2; i <= 1000; ++i) {
//        for (int j = 1; j < i; j++) {
//          filler = new SequenceFiller(j, i);
//          int sum = 0;
//          for (int k = 0; k < j; k++) {
//            sum += filler.howMany(k);
//          }
//  
//          if (i != sum) {
//            fail("Total: " + i + "  Sum: " + sum + " Inputs: " + j);
//          }
//        }
//      }
//    }

  public void testSumUpTo() {
    int inputSize = 4, outputSize = 4;

    filler = new SequenceFiller(inputSize, outputSize);

    assertEquals(0, filler.sumUpTo(0));
    assertEquals(1, filler.sumUpTo(1));
    assertEquals(3, filler.sumUpTo(3));
  }

  public void testHowMany4To1() {
    int inputSize = 4, outputSize = 1;

    filler = new SequenceFiller(inputSize, outputSize);
    assertEquals(1, filler.howMany(0));
    assertEquals(0, filler.howMany(1));
    assertEquals(0, filler.howMany(2));
    assertEquals(0, filler.howMany(3));
  }
}
