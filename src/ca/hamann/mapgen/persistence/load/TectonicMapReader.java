package ca.hamann.mapgen.persistence.load;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.persistence.load.json.BufferedNextCommaReader;
import ca.hamann.mapgen.persistence.load.json.JsonArrayParser;
import ca.hamann.mapgen.persistence.load.json.JsonObject;
import ca.hamann.mapgen.persistence.load.json.JsonObjectParser;
import ca.hamann.mapgen.persistence.load.json.NameValuePair;
import ca.hamann.mapgen.persistence.load.json.NameValuePairParser;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.tectonic.Plate;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class TectonicMapReader {
	private NameValuePairParser pairParser = new NameValuePairParser();
	private BufferedNextCommaReader reader = new BufferedNextCommaReader();

	public TectonicMap read(Reader source) {

		skipPastFirstBracket(source);

		MapConfiguration config = parseConfiguration(source);

		TectonicMap result = new TectonicMap(config);

		parsePlates(source, result);

		skipPastLocationsPreamble(source);
		parseLocations(source, result);

		return result;
	}

	private void skipPastFirstBracket(Reader source) {

		try {
			while (source.read() != '{') {
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private MapConfiguration parseConfiguration(Reader source) {
		String input = reader.readToNextComma(source);
		MapConfigurationParser parser = new MapConfigurationParser();
		NameValuePair pair = pairParser.parse(input);
		return parser.parse(pair.getValue());
	}

	private void parsePlates(Reader source, TectonicMap map) {
		JsonArrayParser<Plate> parser = new JsonArrayParser<Plate>();
		parser.setParser(new PlateParser());

		String input = reader.readToNextComma(source);
		NameValuePairParser pairParser = new NameValuePairParser();
		NameValuePair pair = pairParser.parse(input);

		List<Plate> result = parser.parse(pair.getValue());

		int index = 0;
		for (Plate plate : result) {
			map.getPlates().setPlate(++index, plate);
		}

	}

	private void skipPastLocationsPreamble(Reader source) {
		reader.skipPastNext(source, "[");

	}

	private void parseLocations(Reader source, TectonicMap map) {
		while (!reader.isDone()) {
			populateLocation(map, reader.readToNextComma(source));
		}
	}

	private void populateLocation(TectonicMap map, String location) {
		JsonObjectParser parser = new JsonObjectParser();
		JsonObject obj = parser.parse(location);

		if (!obj.isEmpty()) {

			JsonObject locationObj = parser.parse(obj.getValue("location"));

			SinusoidalLocation loc = map.getGrid().getLocation(
					locationObj.getValueAsInt("spear"),
					locationObj.getValueAsInt("y"),
					locationObj.getValueAsInt("x"));

			map.setPlateIndex(loc, obj.getValueAsInt("plate"));
			map.setCollisions(loc, obj.getValueAsInt("collisions"));
			map.setElevation(loc, obj.getValueAsInt("elevation"));
			map.setAccumulatedWater(loc, obj.getValueAsInt("accumulatedWater"));
			map.setFlow(loc, obj.getValueAsInt("flow"));
		}
	}
}
