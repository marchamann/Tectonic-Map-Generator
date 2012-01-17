package ca.hamann.mapgen.persistence.load.json;

public interface ValueParser<T> {
	public T parse(String input);
}
