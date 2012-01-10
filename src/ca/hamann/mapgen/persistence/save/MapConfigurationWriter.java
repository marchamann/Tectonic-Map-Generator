package ca.hamann.mapgen.persistence.save;

import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.persistence.save.json.SequenceJoiner;

public class MapConfigurationWriter {

	public String write(MapConfiguration config) {
		int halfSpearWidth = config.getHalfSpearWidth();
		SequenceJoiner joiner = new SequenceJoiner();
		joiner.addString(formatMemberPair("halfSpearWidth", halfSpearWidth));
		joiner.addString(formatMemberPair("plateCount", config.getPlateCount()));
		joiner.addString(formatMemberPair("baseLandElevation",
				config.getBaseLandElevation()));
		joiner.addString(formatMemberPair("baseSeaElevation",
				config.getBaseSeaElevation()));
		joiner.addString(formatMemberPair("startingSeed",
				config.getStartingSeed()));
		joiner.addString(formatMemberPair("startingLandPlateCount",
				config.getStartingLandPlateCount()));
		joiner.addString(formatMemberPair("lazySpreading",
				config.isLazySpreading()));
		joiner.addString(formatMemberPair("plateCreationMethod",
				config.getPlateCreationMethod()));

		return "{ " + joiner.join() + " }";
	}

	private String formatInteger(int value) {
		return Integer.toString(value);
	}

	public String formatMemberPair(String name, String value) {
		return name + " : " + value;
	}

	public String formatMemberPair(String name, int value) {
		return formatMemberPair(name, formatInteger(value));
	}

	private String formatMemberPair(String name, long value) {
		return formatMemberPair(name, Long.toString(value));
	}

	private String formatMemberPair(String name, boolean value) {
		return formatMemberPair(name, Boolean.toString(value));
	}

}
