package ca.hamann.mapgen.direction;

public class DirectionSequenceGenerator {

	private DirectionSequence getMonoSequence(MapDirection direction) {
		DirectionSequence result = new DirectionSequence();
		result.append(direction);
		return result;
	}

	public DirectionSequence getEastSequence() {
		return getMonoSequence(MapDirection.EAST);
	}

	public DirectionSequence getWestSequence() {
		return getMonoSequence(MapDirection.WEST);
	}

	public DirectionSequence getNorthEastSequence() {
		return getMonoSequence(MapDirection.NORTHEAST);
		// return getObliqueSequence(MapDirection.EAST, MapDirection.NORTH);
	}

	public DirectionSequence getSouthEastSequence() {
		return getMonoSequence(MapDirection.SOUTHEAST);
		// return getObliqueSequence(MapDirection.EAST, MapDirection.SOUTH);
	}

	public DirectionSequence getNorthWestSequence() {
		return getMonoSequence(MapDirection.NORTHWEST);
		// return getObliqueSequence(MapDirection.WEST, MapDirection.NORTH);
	}

	public DirectionSequence getSouthWestSequence() {
		return getMonoSequence(MapDirection.SOUTHWEST);
		// return getObliqueSequence(MapDirection.WEST, MapDirection.SOUTH);
	}

	public DirectionSequence getNorthClockwiseSequence() {
		DirectionSequence result = new DirectionSequence();
		result.append(new DirectionSubSequence(MapDirection.SOUTHWEST, 50));
		result.append(new DirectionSubSequence(MapDirection.WEST, 50));
		result.append(new DirectionSubSequence(MapDirection.NORTHWEST, 50));
		result.append(new DirectionSubSequence(MapDirection.NORTHEAST, 50));
		result.append(new DirectionSubSequence(MapDirection.EAST, 50));
		result.append(new DirectionSubSequence(MapDirection.SOUTHEAST, 50));
		return result;
	}

	public DirectionSequence getSouthClockwiseSequence() {
		DirectionSequence result = new DirectionSequence();
		result.append(new DirectionSubSequence(MapDirection.NORTHEAST, 50));
		result.append(new DirectionSubSequence(MapDirection.EAST, 50));
		result.append(new DirectionSubSequence(MapDirection.SOUTHEAST, 50));
		result.append(new DirectionSubSequence(MapDirection.SOUTHWEST, 50));
		result.append(new DirectionSubSequence(MapDirection.WEST, 50));
		result.append(new DirectionSubSequence(MapDirection.NORTHWEST, 50));

		return result;
	}

	public DirectionSequence getSouthCounterClockwiseSequence() {
		DirectionSequence result = new DirectionSequence();
		result.append(new DirectionSubSequence(MapDirection.NORTHWEST, 50));
		result.append(new DirectionSubSequence(MapDirection.WEST, 50));
		result.append(new DirectionSubSequence(MapDirection.SOUTHWEST, 50));
		result.append(new DirectionSubSequence(MapDirection.SOUTHEAST, 50));
		result.append(new DirectionSubSequence(MapDirection.EAST, 50));
		result.append(new DirectionSubSequence(MapDirection.NORTHEAST, 50));

		return result;
	}

	public DirectionSequence getNorthCounterClockwiseSequence() {
		DirectionSequence result = new DirectionSequence();
		result.append(new DirectionSubSequence(MapDirection.SOUTHEAST, 50));
		result.append(new DirectionSubSequence(MapDirection.EAST, 50));
		result.append(new DirectionSubSequence(MapDirection.NORTHEAST, 50));
		result.append(new DirectionSubSequence(MapDirection.NORTHWEST, 50));
		result.append(new DirectionSubSequence(MapDirection.WEST, 50));
		result.append(new DirectionSubSequence(MapDirection.SOUTHWEST, 50));

		return result;
	}

	public DirectionSequence getSequenceByInt(int index) {
		DirectionSequence sequence = new DirectionSequence();

		index = index % 6;

		switch (index) {
		case 1:
			sequence = getEastSequence();
			break;

		case 2:
			sequence = getWestSequence();
			break;

		case 3:
			sequence = getNorthClockwiseSequence();
			break;

		case 4:
			sequence = getSouthClockwiseSequence();
			break;

		case 5:
			sequence = getSouthCounterClockwiseSequence();
			break;

		case 0:
			sequence = getNorthCounterClockwiseSequence();
			break;
		}

		return sequence;
	}

}
