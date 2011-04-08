package ca.hamann.mapgen.sinusoidal;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ca.hamann.mapgen.containers.LocationCollectionIterator;
import ca.hamann.mapgen.containers.LocationContainer;
import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.gui.colourers.LocationColourer;
import ca.hamann.mapgen.gui.projections.Projection;
import ca.hamann.mapgen.neighbours.Neighbourhoods;

public class SinusoidalGrid implements LocationContainer {

  private int halfSpearWidth;
  private int maxY;
  private int[] xAtY;
  private double degreesPerY;

  private BasicMover mover;
  private Neighbourhoods neighbourhoods;
	private ArrayList<SinusoidalLocation> iterationList;
  private NonLinearMapStorage ordinals;

  public SinusoidalGrid(int halfSpearWidth) {
    this.halfSpearWidth = halfSpearWidth;
    maxY = getFullSpearWidth() * 2;
    degreesPerY = 90d / (double) maxY;

    xAtY = new int[maxY + 1];
    xAtY[maxY] = 0;
    for (int i = 0; i < maxY; i++) {
      xAtY[i] = calculateMaxXatY(i);
    }

    mover = new BasicMover(this);
    neighbourhoods = new Neighbourhoods(mover);
    initIterationList();
  }

  private void initIterationList() {
    ordinals = new NonLinearMapStorage(this);
		iterationList = new ArrayList<SinusoidalLocation>();
    SinusoidalLocation next = getInitialLocation();
    while (next != null) {
      int ordinal = iterationList.size();
      next.setGridOrdinal(ordinal);
      ordinals.putValue(next, ordinal);
      iterationList.add(next);

      next = getNextIteratedLocation(next);
    }

  }

  public int getFullSpearWidth() {
    return (halfSpearWidth * 2 + 1);
  }

  public int getHeight() {
    return getFullSpearWidth() * 4 + 1;
  }

  public int getWidth() {
    return getFullSpearWidth() * 8;
  }

  public int getWidthAtY(int y) {
    return getSpearWidthAtY(y) * 8;
  }

  public int getHalfWidthAtY(int y) {
    return getSpearWidthAtY(y) * 4;
  }

  public int getSpearWidthAtY(int y) {
    return getMaxXatY(y) * 2 + 1;
  }

  public int getNormalizedX(int x, int y) {
    return x + getMaxXatY(y);
  }

  public int moveOneY(int currentY, boolean negative) {
    int boundaryY = maxY;
    int increment = 1;
    if (negative) {
      boundaryY = -boundaryY;
      increment = -increment;
    }

    if (currentY == boundaryY) {
      return boundaryY;
    }

    return currentY + increment;
  }

  public int getMaxXatY(int y) {
    return xAtY[Math.abs(y)];
  }

  private int calculateMaxXatY(int y) {
    return (int) Math.ceil(getCosine(y) * halfSpearWidth);
  }

  public double getCosine(int y) {
    return Math.cos(Math.toRadians(degreesPerY * y));
  }

  public int moveHorizontalOneSpear(int currentSpear, boolean east) {
    int increment = 7;

    if (east) {
      increment = 1;
    }
    return (currentSpear + increment) % 8;
  }

  public SinusoidalLocation getNextIteratedLocation(SinusoidalLocation loc) {
    SinusoidalLocation result = mover.moveEastFirstTime(loc);

    if (loc.getSpear() == 7) {
      if (loc.getY() == -maxY) {
        return null;
      } else if (result.getSpear() == 0) {
        int newY = moveOneY(loc.getY(), true);
        result = new SinusoidalLocation(0, newY, -getMaxXatY(newY));
      }
    }
    return result;
  }

  public int getMaxY() {
    return maxY;
  }

  public SinusoidalLocation getInitialLocation() {
    if (iterationList == null || iterationList.isEmpty()) {
      return new SinusoidalLocation(0, maxY, 0);
    }
    return getLocation(0, maxY, 0);
  }

  public LocationIterator iterator() {
    return new LocationCollectionIterator(iterationList.iterator());
  }

  public boolean isValidLocation(SinusoidalLocation loc) {
    int spear = loc.getSpear();
    int deltaY = Math.abs(loc.getY());
    int deltaX = Math.abs(loc.getX());
    return spear <= 7
      && spear >= 0
      && deltaY <= maxY
      && deltaX <= getMaxXatY(deltaY);
  }

  public SinusoidalLocation getLocation(int spear, int y, int x) {
    int ordinal = ordinals.getValue(spear, y, x);
    return (SinusoidalLocation) iterationList.get(ordinal);
  }

  public int getHalfSpearWidth() {
    return halfSpearWidth;
  }

  public int getWidthOrdinal(SinusoidalLocation loc) {
    int y = loc.getY();
    return loc.getSpear() * getSpearWidthAtY(y) + getNormalizedX(loc.getX(), y);
  }

  public int getTotalLocations() {
    int result = getFullSpearWidth() * 8;

    for (int i = 1; i < xAtY.length; i++) {
      result += (xAtY[i] * 2 + 1) * 16;
    }

    return result;
  }

  public BufferedImage convertToImage(
    LocationColourer colourer,
    Projection projection) {

    BufferedImage image =
      new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

    LocationIterator iterator = iterator();
    while (iterator.hasNext()) {
      projection.colourLocation(colourer, image, iterator.next());
    }

    return image;
  }

  public boolean contains(SinusoidalLocation loc) {
    return isValidLocation(loc);
  }

  public boolean isEmpty() {
    return false;
  }

  public int size() {
    return getTotalLocations();
  }

  public BasicMover getMover() {
    return mover;
  }

  public Neighbourhoods getNeighbourhoods() {
    return neighbourhoods;
  }

  public SinusoidalLocation getLocationByOrdinal(int i) {
    return (SinusoidalLocation) iterationList.get(i);
  }

}
