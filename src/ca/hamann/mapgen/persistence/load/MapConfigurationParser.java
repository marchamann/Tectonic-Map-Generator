package ca.hamann.mapgen.persistence.load;

import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.persistence.load.json.JsonObject;
import ca.hamann.mapgen.persistence.load.json.JsonObjectParser;

public class MapConfigurationParser {

	public MapConfiguration parse(String input) {
		JsonObjectParser parser = new JsonObjectParser();
		JsonObject obj = parser.parse(input);

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
