package ca.hamann.mapgen.persistence.save;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.persistence.save.json.JsonArrayWriter;
import ca.hamann.mapgen.persistence.save.json.JsonObjectWriter;
import ca.hamann.mapgen.persistence.save.json.SequenceJoiner;
import ca.hamann.mapgen.tectonic.Plate;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class TectonicMapWriter extends JsonObjectWriter {

	public void write(Writer output, TectonicMap map) {
		JsonArrayWriter arrWriter = new JsonArrayWriter();

		MapConfiguration configuration = map.getConfiguration();
		try {
			output.write("{ \"config\" : ");
			output.write(writeConfiguration(configuration));
			output.write(", \"plates\" : ");
			output.write(arrWriter.write(getPlatesJoiner(map)));
			output.write(", \"locations\" : [");
			writeLocations(map, output);
			output.write("]");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private SequenceJoiner getPlatesJoiner(TectonicMap map) {
		SequenceJoiner result = new SequenceJoiner();
		PlateWriter writer = new PlateWriter();
		Iterator<Plate> platesIterator = map.getPlates().iterator();

		while (platesIterator.hasNext()) {
			result.addString(writer.write(platesIterator.next()));
		}

		return result;
	}

	private String writeConfiguration(MapConfiguration configuration) {
		MapConfigurationWriter writer = new MapConfigurationWriter();

		return writer.write(configuration);
	}

	private void writeLocations(TectonicMap map, Writer output) {
		LocationIterator iterator = map.getGrid().iterator();
		LocationWriter writer = new LocationWriter();
		while (iterator.hasNext()) {
			try {
				output.write(writer.write(map, iterator.next()));
				output.write(",\n");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}
}
