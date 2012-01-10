package ca.hamann.mapgen.persistence.load.json;

public class JsonObjectParser {

	public JsonObject parse(String input) {
		JsonObject result = new JsonObject();

		input = input.replace("{", "").replace("}", "").trim();

		String[] arr = input.split(",");

		NameValuePairParser parser = new NameValuePairParser();
		for (String string : arr) {
			NameValuePair pair = parser.parse(string);

			result.addPair(pair);

		}

		return result;
	}

}
