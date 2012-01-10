package ca.hamann.mapgen.persistence.load.json;

public class NameValuePairParser {

	public NameValuePair parse(String string) {

		String[] arr = string.split(":");

		String name = parseJsonString(arr[0]);
		String value = arr[1].trim();

		return new NameValuePair(name, value);
	}

	private String parseJsonString(String input) {
		return input.trim().replace("\"", "");
	}

}
