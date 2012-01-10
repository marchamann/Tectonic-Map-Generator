package ca.hamann.mapgen.persistence.load.json;

public class NameValuePair {

	private String name;
	private String value;

	public NameValuePair(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

}
