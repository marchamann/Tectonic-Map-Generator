package ca.hamann.mapgen.persistence.load.json;

import java.util.Map;
import java.util.TreeMap;

public class JsonObject {

	private Map<String, String> map = new TreeMap<String, String>();

	public String getValue(String name) {
		return map.get(name);
	}

	public void addPair(NameValuePair pair) {
		if (pair != null) {
			map.put(pair.getName(), pair.getValue());
		}
	}

	public int getValueAsInt(String name) {
		return Integer.parseInt(getValue(name));
	}

	public long getValueAsLong(String name) {
		return Long.parseLong(getValue(name));
	}

	public boolean getValueAsBoolean(String name) {
		return Boolean.parseBoolean(getValue(name));
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}
}
