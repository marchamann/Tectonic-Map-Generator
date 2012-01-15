package ca.hamann.mapgen.persistence.save.json;

public class JsonArrayWriter {

	public String write(SequenceJoiner joiner) {
		return "[ " + joiner.join() + " ]";
	}

}
