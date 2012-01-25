package ca.hamann.mapgen.persistence.load.json;

public class NextCommaParser implements FindParser {

	public int parse(String string) {
		int nestedArrayLevel = 0;
		int nestedObjectLevel = 0;
		boolean nestedString = false;
		int escapeLevel = 0;

		for (int i = 0; i < string.length(); i++) {
			char currentChar = string.charAt(i);

			if (currentChar == '\\') {
				escapeLevel++;
			} else if (currentChar == '[') {
				nestedArrayLevel++;
			} else if (currentChar == ']') {
				nestedArrayLevel--;
			} else if (currentChar == '{') {
				nestedObjectLevel++;
			} else if (currentChar == '}') {
				nestedObjectLevel--;
				if (nestedObjectLevel < 0) {
					throw new RuntimeException("Unmatched } in <<" + string
							+ ">>");
				}
			} else if (currentChar == '"' && (escapeLevel % 2) == 0) {
				nestedString = !nestedString;
			} else if (currentChar == ',' && nestedArrayLevel == 0
					&& nestedObjectLevel == 0 && !nestedString) {
				return i;
			}

			if (currentChar != '\\') {
				escapeLevel = 0;
			}
		}
		return -1;
	}
}
