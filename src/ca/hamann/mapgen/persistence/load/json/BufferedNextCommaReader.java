package ca.hamann.mapgen.persistence.load.json;

import java.io.IOException;
import java.io.Reader;

public class BufferedNextCommaReader {
	private char buffer[] = new char[10000];
	private StringBuilder accumulator = new StringBuilder();
	private boolean done = false;
	int comma = -1;
	int result = 0;

	public String readToNextComma(Reader source) {
		return extractToNextFind(source, new NextCommaParser());
	}

	private String extractToNextFind(Reader source, FindParser parser) {
		String output = "";
		try {
			comma = -1;
			result = 0;

			while (comma == -1 && result != -1) {
				comma = parser.parse(accumulator.toString());

				if (comma == -1) {
					result = source.read(buffer, 0, buffer.length);
					if (result != -1) {
						accumulator.append(buffer, 0, result);
					}
				}
			}

			if (comma != -1) {
				output = accumulator.substring(0, comma).trim();
				accumulator = new StringBuilder(
						accumulator.substring(comma + 1));
			} else {
				output = accumulator.toString().trim();
				if (result == -1) {
					done = true;
				}
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return output;
	}

	public void skipPastNext(Reader source, String find) {
		extractToNextFind(source, new SimpleFindParser(find));

	}

	public boolean isDone() {
		return done;
	}

}
