package ca.hamann.mapgen.persistence.save;

import ca.hamann.mapgen.persistence.save.json.JsonObjectWriter;
import ca.hamann.mapgen.persistence.save.json.SequenceJoiner;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class LocationWriter extends JsonObjectWriter {

	public String write(TectonicMap map, SinusoidalLocation loc) {
		SequenceJoiner joiner = new SequenceJoiner();
		joiner.setDelimiter(",\n");
		joiner.addString(formatMemberPair("location", writeLocation(loc)));
		joiner.addString(formatMemberPair("plate", map.getPlateIndex(loc)));
		joiner.addString(formatMemberPair("collisions", map.getCollisions(loc)));
		joiner.addString(formatMemberPair("elevation", map.getElevation(loc)));
		joiner.addString(formatMemberPair("accumulatedWater",
				map.getAccumulatedWater(loc)));
		joiner.addString(formatMemberPair("flow", map.getFlow(loc)));
		return write(joiner);
	}

	private String writeLocation(SinusoidalLocation loc) {
		SequenceJoiner joiner = new SequenceJoiner();
		joiner.setDelimiter(", ");
		joiner.addString(formatMemberPair("spear", loc.getSpear()));
		joiner.addString(formatMemberPair("x", loc.getX()));
		joiner.addString(formatMemberPair("y", loc.getY()));
		return write(joiner);
	}
}
