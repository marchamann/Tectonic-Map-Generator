package ca.hamann.mapgen.persistence.load.json;

import java.util.ArrayList;
import java.util.List;

public class JsonArrayParser<T> {

	private ValueParser<T> parser;

	public List<T> parse(String input) {
		List<T> result = new ArrayList<T>();

		String[] arr = extractValues(input);
		for (String value : arr) {
			result.add(parser.parse(value.trim()));

		}
		return result;
	}

	private String[] extractValues(String input) {
		return stripSquareBrackets(input).split(",");
	}

	private String stripSquareBrackets(String input) {
		String trimmedInput = input.trim();
		return trimmedInput.substring(1, trimmedInput.length() - 1);
	}

	public void setParser(ValueParser<T> parser) {
		this.parser = parser;
	}

}
