package ca.hamann.mapgen.fillers;

public abstract class AbstractLineFillers {

  protected SequenceFiller lineFillers[];

  public SequenceFiller getLineFiller(int y) {
    return lineFillers[Math.abs(y)];
  }

}
