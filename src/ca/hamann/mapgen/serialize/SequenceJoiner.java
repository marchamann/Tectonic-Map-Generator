package ca.hamann.mapgen.serialize;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SequenceJoiner {
	private String delimiter = ",";
	private List<String> strings = new ArrayList<String>();

	public String join() {
		if (strings.size() > 0) {
			Iterator<String> iterator = strings.iterator();
			StringBuilder builder = new StringBuilder(iterator.next());

			while (iterator.hasNext()) {
				builder.append(delimiter);
				builder.append(iterator.next());
			}
			return builder.toString();
		}
		return "";
	}

	public void addString(String value) {
		strings.add(value);
	}

	public void setDelimiter(String string) {
		delimiter = string;
	}

}
