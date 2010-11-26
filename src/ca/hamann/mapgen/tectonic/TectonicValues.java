package ca.hamann.mapgen.tectonic;

public class TectonicValues {

  private int plateIndex;
  private int collisions;
  private int elevation;
  private int lateralIncrements;

  public void setPlateIndex(int index) {
    this.plateIndex = index;
  }

  public int getPlateIndex() {
    return plateIndex;
  }

  public int getCollisions() {
    return collisions;
  }

  public void setCollisions(int collisions) {
    this.collisions = collisions;
  }

  public int getElevation() {
    return elevation;
  }

  public void setElevation(int elevation) {
    this.elevation = elevation;
  }

  public int getLateralIncrements() {
    return lateralIncrements;
  }

  public void setLateralIncrements(int lateralIncrements) {
    this.lateralIncrements = lateralIncrements;
  }

}
