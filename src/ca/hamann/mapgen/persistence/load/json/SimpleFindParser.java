package ca.hamann.mapgen.persistence.load.json;

public class SimpleFindParser implements FindParser {
	private String find;

	public SimpleFindParser(String find) {
		super();
		this.find = find;
	}

	@Override
	public int parse(String string) {
		return string.indexOf(find);
	}
}