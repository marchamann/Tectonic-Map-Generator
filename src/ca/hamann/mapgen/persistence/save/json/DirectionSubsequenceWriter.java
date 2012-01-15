package ca.hamann.mapgen.persistence.save.json;

import ca.hamann.mapgen.direction.DirectionSubSequence;

public class DirectionSubsequenceWriter extends JsonObjectWriter {

	public String write(DirectionSubSequence sequence) {
		SequenceJoiner joiner = new SequenceJoiner();
		joiner.setDelimiter(", ");

		joiner.addString(formatMemberPair("direction", sequence.getDirection()
				.getName()));
		joiner.addString(formatMemberPair("repetitionCount",
				sequence.getRepetitionCount()));

		return write(joiner);
	}
}
