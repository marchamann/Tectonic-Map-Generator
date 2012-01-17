package ca.hamann.mapgen.persistence.save;

import java.util.List;

import ca.hamann.mapgen.direction.DirectionSubSequence;
import ca.hamann.mapgen.persistence.save.json.DirectionSubsequenceWriter;
import ca.hamann.mapgen.persistence.save.json.JsonArrayWriter;
import ca.hamann.mapgen.persistence.save.json.JsonObjectWriter;
import ca.hamann.mapgen.persistence.save.json.SequenceJoiner;
import ca.hamann.mapgen.tectonic.Plate;

public class PlateWriter extends JsonObjectWriter {

	public String write(Plate plate) {
		SequenceJoiner joiner = new SequenceJoiner();
		joiner.setDelimiter(",\n");
		joiner.addString(formatMemberPair("index", plate.getIndex()));
		joiner.addString(formatMemberPair("count", plate.getCount()));

		joiner.addString(formatMemberPair("directionSequence",
				writeDirections(plate)));

		return write(joiner);
	}

	private String writeDirections(Plate plate) {
		return writeDirections(plate.getDirectionSequence().getSequence());
	}

	public String writeDirections(List<DirectionSubSequence> dirs) {
		DirectionSubsequenceWriter writer = new DirectionSubsequenceWriter();
		JsonArrayWriter arrWriter = new JsonArrayWriter();
		SequenceJoiner joiner = new SequenceJoiner();
		for (DirectionSubSequence directionSubSequence : dirs) {
			joiner.addString(writer.write(directionSubSequence));
		}

		return arrWriter.write(joiner);
	}
}
