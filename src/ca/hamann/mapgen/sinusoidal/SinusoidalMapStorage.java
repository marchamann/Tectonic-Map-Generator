package ca.hamann.mapgen.sinusoidal;

public class SinusoidalMapStorage {

	private SinusoidalGrid grid;

	private int[] array;

	public SinusoidalMapStorage(SinusoidalGrid grid) {
		this.grid = grid;
		array = new int[grid.size()];
	}

	public void putValue(SinusoidalLocation loc, int value) {
		array[loc.getGridOrdinal()] = value;
	}

	public int getValue(SinusoidalLocation loc) {
		return array[loc.getGridOrdinal()];
	}

	public int nonZeroCount() {
		int count = 0;

		for (int i = 0; i < array.length; i++) {
			if (array[i] != 0) {
				count++;
			}
		}

		return count;
	}

	public SinusoidalGrid getBaseMap() {
		return grid;
	}

}
