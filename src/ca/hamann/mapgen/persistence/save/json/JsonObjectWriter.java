package ca.hamann.mapgen.persistence.save.json;

public class JsonObjectWriter {

	private String formatInteger(int value) {
		return Integer.toString(value);
	}

	public String formatMemberPair(String name, String value) {
		return "\"" + name + "\"" + " : " + value;
	}

	public String formatMemberPair(String name, int value) {
		return formatMemberPair(name, formatInteger(value));
	}

	protected String formatMemberPair(String name, long value) {
		return formatMemberPair(name, Long.toString(value));
	}

	protected String formatMemberPair(String name, boolean value) {
		return formatMemberPair(name, Boolean.toString(value));
	}

	protected String write(SequenceJoiner joiner) {
		return "{ " + joiner.join() + " }";
	}
}
