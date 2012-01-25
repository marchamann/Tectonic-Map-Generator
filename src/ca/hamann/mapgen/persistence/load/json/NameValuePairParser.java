package ca.hamann.mapgen.persistence.load.json;

public class NameValuePairParser {

	public NameValuePair parse(String string) {
		int colon = string.indexOf(":");

		if (colon == -1) {
			return null;
		}
		String name = parseJsonString(string.substring(0, colon));
		String value = string.substring(colon + 1).trim();

		return new NameValuePair(name, value);
	}

	private String parseJsonString(String input) {
		String trimmedInput = input.trim();
		return trimmedInput.substring(1, trimmedInput.length() - 1);
	}

}
