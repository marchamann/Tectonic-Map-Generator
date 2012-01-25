package ca.hamann.mapgen.persistence.load.json;

public class JsonObjectParser implements ValueParser<JsonObject> {

	public JsonObject parse(String input) {
		JsonObject result = new JsonObject();
		NextCommaParser commaParser = new NextCommaParser();
		NameValuePairParser parser = new NameValuePairParser();

		String string = removeBraces(input);

		int comma = commaParser.parse(string);

		while (comma != -1) {
			String value = string.substring(0, comma);
			result.addPair(parser.parse(value));
			string = string.substring(comma + 1);
			comma = commaParser.parse(string);
		}

		result.addPair(parser.parse(string));

		return result;
	}

	private String removeBraces(String input) {
		String trimmedInput = input.trim();
		if (!trimmedInput.startsWith("{")) {
			return trimmedInput;
		}
		return trimmedInput.substring(1, trimmedInput.length() - 1).trim();
	}

}
