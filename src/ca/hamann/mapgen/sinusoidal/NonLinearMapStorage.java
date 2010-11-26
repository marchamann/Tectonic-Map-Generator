package ca.hamann.mapgen.sinusoidal;

public class NonLinearMapStorage {

	private int maxX, maxY;

	private int[][][] array;

	public NonLinearMapStorage(SinusoidalGrid grid) {
		array = new int[8][grid.getHeight()][grid.getFullSpearWidth()];
		maxX = grid.getHalfSpearWidth();
		maxY = grid.getMaxY();
	}

	public void putValue(int spear, int y, int x, int value) {
		array[spear][getY(y)][getX(x)] = value;
	}

	public int getValue(int spear, int y, int x) {
		return array[spear][getY(y)][getX(x)];
	}

	private int getY(int y) {
		return y + maxY;
	}

	private int getX(int x) {
		return x + maxX;
	}

	public void putValue(SinusoidalLocation loc, int value) {
		putValue(loc.getSpear(), loc.getY(), loc.getX(), value);
	}
}
