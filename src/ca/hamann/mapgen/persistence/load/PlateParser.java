package ca.hamann.mapgen.persistence.load;

import java.util.List;

import ca.hamann.mapgen.direction.DirectionSequence;
import ca.hamann.mapgen.direction.DirectionSubSequence;
import ca.hamann.mapgen.persistence.load.json.JsonArrayParser;
import ca.hamann.mapgen.persistence.load.json.JsonObject;
import ca.hamann.mapgen.persistence.load.json.JsonObjectParser;
import ca.hamann.mapgen.persistence.load.json.ValueParser;
import ca.hamann.mapgen.tectonic.Plate;

public class PlateParser implements ValueParser<Plate>{

	public Plate parse(String input) {
		JsonObjectParser parser = new JsonObjectParser();
		return populatePlate(parser.parse(input));
	}

	private Plate populatePlate(JsonObject obj) {

		Plate result = new Plate(obj.getValueAsInt("index"));
		result.setCount(obj.getValueAsInt("count"));

		result.setDirectionSequence(parseDirectionSequence(obj
				.getValue("directionSequence")));

		return result;
	}

	private DirectionSequence parseDirectionSequence(String value) {
		DirectionSequence result = new DirectionSequence();
		JsonArrayParser<DirectionSubSequence> parser = new JsonArrayParser<DirectionSubSequence>();
		parser.setParser(new DirectionSubSequenceParser());

		List<DirectionSubSequence> subSequences = parser.parse(value);

		for (DirectionSubSequence directionSubSequence : subSequences) {
			result.append(directionSubSequence);
		}

		return result;
	}
}
