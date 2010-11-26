package ca.hamann.mapgen.fillers;

public class CylindricalSequenceFiller extends SequenceFiller {

  public CylindricalSequenceFiller(int inputSize, int outputSize) {
    super(inputSize, outputSize);
  }

  protected void calculateOutputs() {
    super.calculateOutputs();

    int size = getInputSize();

    int newLength = size * 8;

    int[] newOutputs = new int[newLength];

    int[] outputs = this.getOutputs();

    for (int i = 0; i < 8; i++)
      for (int j = 0; j < size; j++) {
        newOutputs[i * size + j] = outputs[j];
      }

    setOutputs(newOutputs);

  }

}
