package ca.hamann.mapgen.direction;

import java.util.Map;
import java.util.TreeMap;

public class DirectionByNameTable {

	Map<String, MapDirection> map = new TreeMap<String, MapDirection>();

	public DirectionByNameTable() {
		addDirection(MapDirection.EAST);
		addDirection(MapDirection.IDENTITY);
		addDirection(MapDirection.NORTH);
		addDirection(MapDirection.NORTHEAST);
		addDirection(MapDirection.NORTHWEST);
		addDirection(MapDirection.SOUTH);
		addDirection(MapDirection.SOUTHEAST);
		addDirection(MapDirection.SOUTHWEST);
		addDirection(MapDirection.WEST);
	}

	private void addDirection(MapDirection direction) {
		map.put(direction.getName(), direction);
	}

	public MapDirection getDirection(String name) {
		return map.get(name);
	}
}
