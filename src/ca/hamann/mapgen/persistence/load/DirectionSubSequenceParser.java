package ca.hamann.mapgen.persistence.load;

import ca.hamann.mapgen.direction.DirectionByNameTable;
import ca.hamann.mapgen.direction.DirectionSubSequence;
import ca.hamann.mapgen.direction.MapDirection;
import ca.hamann.mapgen.persistence.load.json.JsonObject;
import ca.hamann.mapgen.persistence.load.json.JsonObjectParser;
import ca.hamann.mapgen.persistence.load.json.ValueParser;

public class DirectionSubSequenceParser implements
		ValueParser<DirectionSubSequence> {

	private DirectionByNameTable table = new DirectionByNameTable();
	JsonObjectParser parser = new JsonObjectParser();

	public DirectionSubSequence parse(String input) {
		JsonObject obj = parser.parse(input);
		if (obj.isEmpty()) {
			return null;
		}
		return new DirectionSubSequence(getDirection(obj), getRepetitions(obj));
	}

	private int getRepetitions(JsonObject obj) {
		return obj.getValueAsInt("repetitionCount");
	}

	private MapDirection getDirection(JsonObject obj) {
		return table.getDirection(obj.getValue("direction"));
	}
}
