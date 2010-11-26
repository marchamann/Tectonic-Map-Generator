package ca.hamann.mapgen.sinusoidal;

public class SinusoidalMapStorage {

	private SinusoidalGrid grid;

	// private int[][][] array;
	private int[] array;

	public SinusoidalMapStorage(SinusoidalGrid grid) {
		this.grid = grid;
		// array = new int[8][grid.getHeight()][grid.getFullSpearWidth()];
		array = new int[grid.size()];
	}

	public void putValue(SinusoidalLocation loc, int value) {
		array[loc.getGridOrdinal()] = value;
		// array[loc.getSpear()][getYFromLoc(loc)][getXFromLoc(loc)] = value;
	}

	public int getValue(SinusoidalLocation loc) {
		// return array[loc.getSpear()][getYFromLoc(loc)][getXFromLoc(loc)];
		return array[loc.getGridOrdinal()];
	}

	public int nonZeroCount() {
		// int maxX = array[0][0].length;
		// int maxY = array[0].length;
		//
		int count = 0;
		//
		// for (int spear = 0; spear < 8; spear++)
		// for (int y = 0; y < maxY; y++)
		// for (int x = 0; x < maxX; x++) {
		// if (array[spear][y][x] != 0) {
		// count++;
		// }
		// }

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
