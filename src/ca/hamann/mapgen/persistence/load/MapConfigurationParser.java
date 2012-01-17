package ca.hamann.mapgen.persistence.load;

import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.persistence.load.json.JsonObject;
import ca.hamann.mapgen.persistence.load.json.JsonObjectParser;

public class MapConfigurationParser {
	JsonObjectParser parser = new JsonObjectParser();

	public MapConfiguration parse(String input) {
		JsonObject obj = parser.parse(input);
		return populateMapConfiguration(obj);
	}

	private MapConfiguration populateMapConfiguration(JsonObject obj) {
		MapConfiguration result = new MapConfiguration(1);
		result.setHalfSpearWidth(obj.getValueAsInt("halfSpearWidth"));
		result.setPlateCount(obj.getValueAsInt("plateCount"));
		result.setBaseLandElevation(obj.getValueAsInt("baseLandElevation"));
		result.setBaseSeaElevation(obj.getValueAsInt("baseSeaElevation"));
		result.setStartingSeed(obj.getValueAsLong("startingSeed"));
		result.setStartingLandPlateCount(obj
				.getValueAsInt("startingLandPlateCount"));
		result.setLazySpreading(obj.getValueAsBoolean("lazySpreading"));
		result.setPlateCreationMethod(obj.getValue("plateCreationMethod"));
		return result;
	}
}
