package ca.hamann.mapgen;

import java.util.Random;

import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;

public class MapConfiguration {

	private int baseLandElevation = 130;
	private int baseSeaElevation = 64;

	private long startingSeed = 0;

	private int plateCount = 20;
	private int startingLandPlateCount = 6;

	private int seaLevel = 128;

	private int collisionIncrement = 7;
	private int erosionLimit = 5;

	private SinusoidalGrid grid;

	private Random random;

	private boolean lazySpreading = false;
	
	private String plateCreationMethod = "Flood Fill";

	public MapConfiguration() {
		initRandom();
	}

	private void initRandom() {
		this.random = new Random(startingSeed);
	}

	public MapConfiguration(int halfSpearWidth) {
		this();
		setHalfSpearWidth(halfSpearWidth);
	}

	public int getBaseLandElevation() {
		return baseLandElevation;
	}

	public int getBaseSeaElevation() {
		return baseSeaElevation;
	}

	public void setBaseLandElevation(int i) {
		baseLandElevation = i;
	}

	public void setBaseSeaElevation(int i) {
		baseSeaElevation = i;
	}

	public SinusoidalGrid getGrid() {
		return grid;
	}

	public void setHalfSpearWidth(int halfSpearWidth) {
		grid = new SinusoidalGrid(halfSpearWidth);
	}

	public int getPlateCount() {
		return plateCount;
	}

	public void setPlateCount(int i) {
		plateCount = i;
	}

	public Random getRandom() {
		return random;
	}

	public long getStartingSeed() {
		return startingSeed;
	}

	public void setStartingSeed(long seed) {
		startingSeed = seed;
		initRandom();
	}

	public int getStartingLandPlateCount() {
		return startingLandPlateCount;
	}

	public void setStartingLandPlateCount(int i) {
		startingLandPlateCount = i;
	}

	public int getSeaLevel() {
		return seaLevel;
	}

	public int getCollisionIncrement() {
		return collisionIncrement;
	}

	public int getErosionLimit() {
		return erosionLimit;
	}

	public void setErosionLimit(int i) {
		erosionLimit = i;
	}

	public boolean isLazySpreading() {
		return lazySpreading;
	}

	public void setLazySpreading(boolean lazySpreading) {
		this.lazySpreading = lazySpreading;
	}

	public String getPlateCreationMethod() {
		return plateCreationMethod;
	}

	public void setPlateCreationMethod(String plateCreationMethod) {
		this.plateCreationMethod = plateCreationMethod;
	}
	
	
}
