package ca.hamann.mapgen.direction;

import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.sinusoidal.SinusoidalMover;

public abstract class MapDirection {
  public MapDirection() {
  }

  public static final MapDirection EAST = new MapDirection() {
    public SinusoidalLocation move(
      SinusoidalMover map,
      SinusoidalLocation location) {
      return map.moveEast(location);
    }

    public String getName() {
      return "EAST";
    }
  };

  public static final MapDirection WEST = new MapDirection() {
    public SinusoidalLocation move(
      SinusoidalMover map,
      SinusoidalLocation location) {
      return map.moveWest(location);
    }
    public String getName() {
      return "WEST";
    }
  };

  public static final MapDirection SOUTH = new MapDirection() {
    public SinusoidalLocation move(
      SinusoidalMover map,
      SinusoidalLocation location) {
      return map.moveSouth(location);
    }

    public String getName() {
      return "SOUTH";
    }

    public MapDirection invertNorthSouth() {
      return NORTH;
    }
  };

  public static final MapDirection NORTH = new MapDirection() {
    public SinusoidalLocation move(
      SinusoidalMover map,
      SinusoidalLocation location) {
      return map.moveNorth(location);
    }

    public String getName() {
      return "NORTH";
    }

    public MapDirection invertNorthSouth() {
      return SOUTH;
    }
  };

  public static final MapDirection NORTHEAST = new MapDirection() {
    public SinusoidalLocation move(
      SinusoidalMover map,
      SinusoidalLocation location) {
      return map.moveEast(map.moveNorth(location));
    }

    public String getName() {
      return "NORTHEAST";
    }

    public MapDirection invertNorthSouth() {
      return SOUTHEAST;
    }
  };

  public static final MapDirection SOUTHWEST = new MapDirection() {
    public SinusoidalLocation move(
      SinusoidalMover map,
      SinusoidalLocation location) {
      return map.moveWest(map.moveSouth(location));
    }

    public String getName() {
      return "SOUTHWEST";
    }

    public MapDirection invertNorthSouth() {
      return NORTHWEST;
    }
  };

  public static final MapDirection NORTHWEST = new MapDirection() {
    public SinusoidalLocation move(
      SinusoidalMover map,
      SinusoidalLocation location) {
      return map.moveNorth(map.moveWest(location));
    }

    public String getName() {
      return "NORTHWEST";
    }

    public MapDirection invertNorthSouth() {
      return SOUTHWEST;
    }
  };

  public static final MapDirection SOUTHEAST = new MapDirection() {
    public SinusoidalLocation move(
      SinusoidalMover map,
      SinusoidalLocation location) {
      return map.moveSouth(map.moveEast(location));
    }

    public String getName() {
      return "SOUTHEAST";
    }

    public MapDirection invertNorthSouth() {
      return NORTHEAST;
    }
  };

  public static final MapDirection IDENTITY = new MapDirection() {
    public SinusoidalLocation move(
      SinusoidalMover map,
      SinusoidalLocation location) {
      return location;
    }

    public String getName() {
      return "IDENTITY";
    }

  };

  public abstract SinusoidalLocation move(
    SinusoidalMover map,
    SinusoidalLocation location);

  public MapDirection invertNorthSouth() {
    return this;
  }

  public String getName() {
    return null;
  }

  public String toString() {
    return "MapDirection: " + getName();
  }

}