package ca.hamann.mapgen.tectonic;

import java.util.Random;

import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.containers.LocationList;
import ca.hamann.mapgen.direction.DirectionSequence;
import ca.hamann.mapgen.direction.DirectionSequenceGenerator;
import ca.hamann.mapgen.gui.MapProcessor;
import ca.hamann.mapgen.gui.genscreen.GeneratorScreen;
import ca.hamann.mapgen.gui.genscreen.MapProcess;
import ca.hamann.mapgen.neighbours.TectonicNeighbourhoods;
import ca.hamann.mapgen.sinusoidal.SinusoidalGrid;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class PlateGenerator implements MapProcessor {

	private TectonicNeighbourhoods neighbourhoods;
	private MapConfiguration config;
	private SinusoidalGrid grid;
	private TectonicMap tectMap;
	private PlateSpreader spreader;

	private Random floodFillRandom;

	public PlateGenerator(MapConfiguration config) {
		this.config = config;
		grid = config.getGrid();
		tectMap = new TectonicMap(config);
		neighbourhoods = tectMap.getNeighbourhoods();
		floodFillRandom = config.getRandom();
		spreader = tectMap.getPlateSpreader();
	}

	public void seedPlates() {
		int plateCount = initializePlates();

		String plateCreationMethod = config.getPlateCreationMethod();

		if ("Flood Fill".equals(plateCreationMethod)) {
			int[] startingLocations = randomizeStartingLocations(plateCount);
			mapStartingLocations(startingLocations);
		} else if ("Plate Splitting".equals(plateCreationMethod)) {
			fillMapWithStartPlate();
		}
	}

	private void fillMapWithStartPlate() {
		LocationIterator iterator = grid.iterator();
		while (iterator.hasNext()) {
			tectMap.setPlateIndex(iterator.next(), 1);
		}
	}

	private int[] randomizeStartingLocations(int plateCount) {

		int locationCount = grid.getTotalLocations();

		int[] startingLocations = new int[plateCount + 1];
		for (int i = 1; i <= plateCount; i++) {

			int starting = floodFillRandom.nextInt(locationCount);
			startingLocations[i] = starting;
		}
		return startingLocations;
	}

	private void initializePlateDirections() {
		DirectionSequenceGenerator seqGen = new DirectionSequenceGenerator();
		for (int i = 1; i <= config.getPlateCount(); i++) {
			DirectionSequence sequence = seqGen
					.getSequenceByInt(floodFillRandom.nextInt());
			tectMap.setDirectionSequence(sequence, i);
		}

	}

	private void mapStartingLocations(int[] startingLocations) {
		for (int i = 1; i < startingLocations.length; i++) {
			SinusoidalLocation loc = grid
					.getLocationByOrdinal(startingLocations[i]);
			setPlateAtLoc(loc, i);
		}
	}

	private int initializePlates() {
		int plateCount = config.getPlateCount();

		TectonicPlates plates = new TectonicPlates(plateCount);
		plates.setSeaPlateCount(plateCount - config.getStartingLandPlateCount());
		tectMap.setPlates(plates);

		return plateCount;
	}

	public TectonicMap setPlateAtLoc(SinusoidalLocation loc, int plateIndex) {
		tectMap.setPlateIndex(loc, plateIndex);
		LocationList edgeSet = getNeighbourSet(plateIndex);
		edgeSet.addAll(neighbourhoods.getNeighbours(loc));
		return tectMap;
	}

	public LocationList getNeighbourSet(int plateIndex) {
		return spreader.getNeighbourSet(plateIndex);
	}

	public TectonicMap getTectonicMap() {
		return tectMap;
	}

	public TectonicMap seedPolarRegions() {
		int maxY = grid.getMaxY();
		for (int i = 0; i < 8; i++) {
			setPlateAtLoc(grid.getLocation(i, maxY, 0), -1);
			setPlateAtLoc(grid.getLocation(i, -maxY, 0), -1);
		}
		return tectMap;
	}

	public TectonicMap processMap() {
		seedPlates();
		generatePlates();
		initializePlateDirections();
		return tectMap;
	}

	public String getProcessName() {
		return "Generating plates...";
	}

	public void setProcess(MapProcess process) {
		spreader.setProcess(process);
	}

	public void afterProcess(GeneratorScreen screen) {
		screen.activateRiverMode();
	}

	public TectonicMap generatePlates() {
		tectMap = spreader.generatePlates();
		tectMap.generateBaseElevations();
		return tectMap;
	}

	public SinusoidalLocation spreadPlateByOne(int i) {
		return spreader.spreadPlateByOne(i);
	}

}
