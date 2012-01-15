package ca.hamann.mapgen.persistence.save;

import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.persistence.save.json.JsonObjectWriter;
import ca.hamann.mapgen.persistence.save.json.SequenceJoiner;

public class MapConfigurationWriter extends JsonObjectWriter {

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

		joiner.setDelimiter(",\n");
		return write(joiner);
	}

}
