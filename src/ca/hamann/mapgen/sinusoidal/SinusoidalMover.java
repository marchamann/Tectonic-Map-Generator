package ca.hamann.mapgen.sinusoidal;

public interface SinusoidalMover {
  public SinusoidalLocation moveNorth(SinusoidalLocation loc);
  public SinusoidalLocation moveSouth(SinusoidalLocation loc);
  public SinusoidalLocation moveEast(SinusoidalLocation loc);
  public SinusoidalLocation moveWest(SinusoidalLocation loc);

}