package ca.hamann.mapgen.sinusoidal;

public class SinusoidalLocation implements Comparable<SinusoidalLocation> {

	private int spear, y, x;
	private int gridOrdinal;

	public SinusoidalLocation(int spear, int y, int x) {
		this.spear = spear;
		this.y = y;
		this.x = x;
	}

	public int getSpear() {
		return spear;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean equals(Object obj) {
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		SinusoidalLocation other = (SinusoidalLocation) obj;
		return spear == other.spear && y == other.y && x == other.x;
	}

	public int hashCode() {
		return (spear << 16) ^ (y << 8) ^ (x);
	}

	public String toString() {
		return "SinusoidalLocation: spear:" + spear + " y:" + y + " x:" + x;
	}

	public int compareTo(SinusoidalLocation location) {

		if (location.gridOrdinal > gridOrdinal) {
			return -1;
		}

		if (location.gridOrdinal < gridOrdinal) {
			return 1;
		}

		return 0;
	}

	public int getGridOrdinal() {
		return gridOrdinal;
	}

	public void setGridOrdinal(int i) {
		gridOrdinal = i;
	}

}
