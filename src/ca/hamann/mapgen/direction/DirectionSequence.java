package ca.hamann.mapgen.direction;

import java.util.ArrayList;
import java.util.List;

public class DirectionSequence {

	private DirectionSubSequence currentSubSequence;
	private int repetitionsLeft;

	private int currentSubSequenceIndex;
	private List<DirectionSubSequence> sequence;

	public DirectionSequence() {
		sequence = new ArrayList<DirectionSubSequence>();
		currentSubSequenceIndex = -1;
		repetitionsLeft = 0;
	}

	public void append(MapDirection direction) {
		sequence.add(new DirectionSubSequence(direction, 1));
	}

	public void append(DirectionSubSequence seq) {
		sequence.add(seq);
	}

	public MapDirection getNextDirection() {
		if (!sequence.isEmpty() && repetitionsLeft == 0) {
			currentSubSequenceIndex = incrementCurrent();
			currentSubSequence = sequence.get(currentSubSequenceIndex);
			repetitionsLeft = currentSubSequence.getRepetitionCount();
		}
		repetitionsLeft--;

		if (currentSubSequence == null) {
			return MapDirection.IDENTITY;
		}
		return currentSubSequence.getDirection();
	}

	private int incrementCurrent() {
		return (currentSubSequenceIndex + 1) % sequence.size();
	}

	public MapDirection getCurrentDirection() {
		if (sequence.isEmpty()) {
			return MapDirection.IDENTITY;
		}
		if (currentSubSequence == null) {
			getNextDirection();
		}

		return currentSubSequence.getDirection();
	}

	public int getCurrentSubSequenceIndex() {
		return currentSubSequenceIndex;
	}

	public int getRepetitionsLeft() {
		return repetitionsLeft;
	}

	public DirectionSubSequence getSubSequence(int index) {
		return sequence.get(index);
	}

	public int subSequenceCount() {
		return sequence.size();
	}

	public List<DirectionSubSequence> getSequence() {
		return sequence;
	}

}
