package ca.hamann.mapgen.fillers;

public class SequenceFiller {

  private int outputSize;
  private int inputSize;
  private int[] outputs;
  private int toDistribute;

  public SequenceFiller(int inputSize, int outputSize) {
    this.inputSize = inputSize;
    this.outputSize = outputSize;

    toDistribute = outputSize;
    calculateOutputs();
  }

  protected void calculateOutputs() {
    outputs = new int[inputSize];
    int base = outputSize / inputSize;
    distributeBase(base);

    while (toDistribute > 0) {
      int every = Math.max(inputSize / toDistribute, 2);
      addOneEvery(every);
    }

  }

  private void addOneEvery(int every) {
    for (int i = 0; i < inputSize && toDistribute > 0; i++) {
      int increment = i * every / 2;
      if (i % 2 == 0) {
        outputs[increment]++;
      } else {
        outputs[inputSize - increment]++;
      }

      toDistribute--;
    }
  }

  private void distributeBase(int base) {
    for (int i = 0; i < inputSize; i++) {
      outputs[i] = base;
      toDistribute -= base;
    }
  }

  public int howMany(int inputIndex) {
    return outputs[inputIndex];
  }

  public int sumUpTo(int inputIndex) {
    int result = 0;
    for (int i = 0; i < inputIndex; i++) {
      result += howMany(i);
    }
    return result;
  }

  public int getInputSize() {
    return inputSize;
  }

  protected void setOutputs(int[] is) {
    outputs = is;
  }

  public int[] getOutputs() {
    return outputs;
  }

}
