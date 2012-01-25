package ca.hamann.mapgen.persistence.load.json;

import java.util.ArrayList;
import java.util.List;

public class JsonArrayParser<T> {

	private ValueParser<T> parser;

	public List<T> parse(String input) {
		NextCommaParser commaParser = new NextCommaParser();
		List<T> result = new ArrayList<T>();

		String string = stripSquareBrackets(input);

		int comma = commaParser.parse(string);

		while (comma != -1) {
			String value = string.substring(0, comma);
			result.add(parser.parse(value));
			string = string.substring(comma + 1);
			comma = commaParser.parse(string);
		}
		result.add(parser.parse(string));

		return result;
	}

	private String stripSquareBrackets(String input) {
		String trimmedInput = input.trim();
		return trimmedInput.substring(1, trimmedInput.length() - 1);
	}

	public void setParser(ValueParser<T> parser) {
		this.parser = parser;
	}

}
