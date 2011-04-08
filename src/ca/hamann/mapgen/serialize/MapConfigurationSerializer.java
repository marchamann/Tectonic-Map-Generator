package ca.hamann.mapgen.serialize;

import ca.hamann.mapgen.MapConfiguration;

public class MapConfigurationSerializer {

	public String serialize(MapConfiguration config) {
		int halfSpearWidth = config.getGrid().getHalfSpearWidth();
		SequenceJoiner joiner = new SequenceJoiner();
		joiner.addString(formatMemberPair("halfSpearWidth", halfSpearWidth));
		joiner.addString(formatMemberPair("plateCount", config.getPlateCount()));
		return "{" + joiner.join() + "}";
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

}
