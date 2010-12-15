package ca.hamann.mapgen.tectonic;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import ca.hamann.mapgen.direction.DirectionSequence;

public class TectonicPlates {

	private int plateCount;
	private Map<Integer, Plate> plates;
	private Set<SubductionRelation> subductionRelations;
	private int seaPlateCount;

	public TectonicPlates(int plateCount) {
		this.plateCount = plateCount;
		initializePlates();
		seaPlateCount = plateCount;
	}

	private void initializePlates() {
		plates = new TreeMap<Integer, Plate>();
		for (int i = 1; i <= plateCount; i++) {
			setPlate(i, new Plate(i));
		}
		subductionRelations = new TreeSet<SubductionRelation>();

	}

	// public void setLandPlate(int index) {
	// getPlate(index).setLandPlate();
	// }
	//
	// public void setSeaPlate(int index) {
	// getPlate(index).setSeaPlate();
	//
	// }

	public int lessDensePlate(int plateIndex1, int plateIndex2) {
		int result = plateIndex1;
		// Plate plate1 = getPlate(plateIndex1);
		// Plate plate2 = getPlate(plateIndex2);

		boolean isSea1 = !isLandPlate(plateIndex1); // plate1.isSeaPlate();
		boolean isSea2 = !isLandPlate(plateIndex2);

		if (isSea1 && !isSea2) {
			result = plateIndex2;
		} else if (!isSea1 && isSea2) {
			result = plateIndex1;
		} else if (subductionRelations.contains(new SubductionRelation(
				plateIndex1, plateIndex2))) {
			result = plateIndex1;
		} else if (subductionRelations.contains(new SubductionRelation(
				plateIndex2, plateIndex1))) {
			return plateIndex2;
		} else {
			subductionRelations.add(new SubductionRelation(plateIndex1,
					plateIndex2));
		}
		return result;
	}

	public Plate getPlate(int index) {
		return plates.get(new Integer(index));
	}

	public void setPlate(int index, Plate plate) {
		plates.put(new Integer(index), plate);
	}

	public Iterator<Plate> iterator() {
		return plates.values().iterator();
	}

	public boolean isLandPlate(int plateIndex) {
		return plateIndex > seaPlateCount;
	}

	public void setDirectionSequence(DirectionSequence sequence, int plateIndex) {
		getPlate(plateIndex).setDirectionSequence(sequence);
	}

	public DirectionSequence getDirectionSequence(int plateIndex) {
		return getPlate(plateIndex).getDirectionSequence();
	}

	public int getBiggestPlate() {
		Iterator<Plate> iterator = iterator();
		int result = 0;
		int biggestCount = 0;

		while (iterator.hasNext()) {
			Plate plate = iterator.next();
			if (plate.getCount() > biggestCount) {
				result = plate.getIndex();
				biggestCount = plate.getCount();
			}
		}

		return result;
	}

	public void decrementCountOfPlate(int plateIndex) {
		Plate plate = getPlate(plateIndex);
		if (plate != null) {
			plate.decrementCount();
		}
	}

	public void incrementCountOfPlate(int plateIndex) {
		Plate plate = getPlate(plateIndex);
		if (plate != null) {
			plate.incrementCount();
		}
	}

	public int getSeaPlateCount() {
		return seaPlateCount;
	}

	public void setSeaPlateCount(int count) {
		seaPlateCount = count;
	}

}
