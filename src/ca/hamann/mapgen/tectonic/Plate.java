package ca.hamann.mapgen.tectonic;

import ca.hamann.mapgen.direction.*;

public class Plate {

  private int index;
  private int count = 0;
  private DirectionSequence sequence;
  public Plate(int index) {
    this.index = index;
  }

//  private boolean landPlate;
//
//  public void setSeaPlate() {
//    landPlate = false;
//  }
//
//  public void setLandPlate() {
//    landPlate = true;
//  }
//
//  public boolean isSeaPlate() {
//    return !landPlate;
//  }

  public void setDirectionSequence(DirectionSequence sequence) {
    this.sequence = sequence;
  }

  public DirectionSequence getDirectionSequence() {
    return sequence;
  }

  public int getIndex() {
    return index;
  }

  public void incrementCount() {
    count++;
  }

  public void decrementCount() {
    if (count > 0) {
      count--;
    }
  }

  public int getCount() {
    return count;
  }

  public void setCount(int i) {
    count = i;
  }

}
