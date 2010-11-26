package ca.hamann.mapgen.drainage;

import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.containers.LocationContainer;
import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.containers.LocationList;
import ca.hamann.mapgen.containers.traversal.LocationContainerTraversal;
import ca.hamann.mapgen.drainage.filters.*;
import ca.hamann.mapgen.gui.MapProcessor;
import ca.hamann.mapgen.gui.genscreen.GeneratorScreen;
import ca.hamann.mapgen.gui.genscreen.MapProcess;
import ca.hamann.mapgen.neighbours.ElevationComparator;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;
import ca.hamann.mapgen.tectonic.TectonicMap;

public class DrainageProcessor implements MapProcessor {

  private MapProcess process;
  private TectonicMap tectMap;
  private DrainageGraph drainageGraph;

  private String currentProcessMessage = "Making Lakes and Rivers";

  private LocationCollection landLocations, seaDrainedLand;

  public DrainageProcessor(TectonicMap tectMap) {
    this.tectMap = tectMap;
    drainageGraph = new DrainageGraph(this);
  }

  public int getElevation(SinusoidalLocation loc) {
    return tectMap.getElevation(loc);
  }

  public LocationContainer getAllLocations() {
    return tectMap.getGrid();
  }

  public LocationContainerTraversal getNeighboursTraversal(SinusoidalLocation loc) {
    return tectMap.getNeighbourhoods().getNeighboursTraversal(loc);
  }

  public boolean isSea(SinusoidalLocation loc) {
    return getElevation(loc) <= tectMap.getSeaLevel();
  }

  public boolean isLand(SinusoidalLocation loc) {
    return !isSea(loc);
  }

  public boolean isNextToSea(SinusoidalLocation loc) {
    LocationContainerTraversal traversal = getNeighboursTraversal(loc);

    LocationCollection result = traversal.filter(new SeaFilter(this));

    return !result.isEmpty();
  }

  public LocationCollection getLand() {
    if (landLocations == null) {
      landLocations = new LandSeaDivider(this).getLand();
    }
    return landLocations;
  }

  public boolean isNotHigherThan(
    SinusoidalLocation neighbour,
    SinusoidalLocation loc) {
    ElevationComparator comparator = new ElevationComparator(tectMap);
    return comparator.isNotHigherThan(neighbour, loc);
  }

  public LocationList getNotHigherNeighbours(SinusoidalLocation loc) {
    LocationList neighbours =
      tectMap.getNeighbourhoods().getElevationOrderedNeighbours(loc);

    LocationList result = new LocationList();

    LocationIterator iterator = neighbours.iterator();

    while (iterator.hasNext()) {
      SinusoidalLocation next = iterator.next();
      if (isNotHigherThan(next, loc)) {
        result.add(next);
      }
    }

    return result;
  }

  public TectonicMap processMap() {
    SeaDrainageDivider seaDivider = new SeaDrainageDivider(this);

    CoastInlandDivider ciDivider = new CoastInlandDivider(this);
    setCurrentProcessMessage("Finding coastline...");
    ciDivider.divideCoastFromInland(getLand());

    LocationCollection inland = ciDivider.getInland();
    LocationCollection coast = ciDivider.getCoastalLand();

    setCurrentProcessMessage("Extending sea drainage...");
    seaDivider.divideDrainageUntilFixedPoint(coast, inland);

    if (!validateGraph()) {
      throw new RuntimeException("Invalid graph");
    }

    LakeFiller filler = new LakeFiller(this, seaDivider.getUndrained());

    setCurrentProcessMessage("Filling lakes...");
    filler.fillLakes();

    if (!validateGraph()) {
      throw new RuntimeException("Invalid graph");
    }

    RiverMaker maker = new RiverMaker(this);

    setCurrentProcessMessage("Making rivers...");
    maker.initializeRainfall();
    maker.correctDrainage();

    if (!validateGraph()) {
      throw new RuntimeException("Invalid graph");
    }

    maker.drainAllLand();

    if (!validateFlow()) {
      throw new RuntimeException("Bad flow");
    }

    return tectMap;
  }

  private void setCurrentProcessMessage(String string) {
    currentProcessMessage = string;
    process.updateGui();
  }

  public void afterProcess(GeneratorScreen screen) {
  }

  public String getProcessName() {
    return currentProcessMessage;
  }

  public void addDrainageArrow(
    SinusoidalLocation from,
    SinusoidalLocation to) {
    drainageGraph.addDrainageArrow(from, to);
  }

  public SinusoidalLocation getDrainageLocation(SinusoidalLocation loc) {
    return drainageGraph.getDrainageLocation(loc);
  }

  public ElevationComparator getElevationComparator() {
    return new ElevationComparator(tectMap);
  }

  public TectonicMap getTectonicMap() {
    return tectMap;
  }

  public void setAccummulatedWater(SinusoidalLocation loc, int value) {
    tectMap.setAccummulatedWater(loc, value);
  }

  public int getFlow(SinusoidalLocation loc) {
    return tectMap.getFlow(loc);
  }

  public void setFlow(SinusoidalLocation loc, int value) {
    tectMap.setFlow(loc, value);
  }

  public boolean validateGraph() {
    return drainageGraph.validate();
  }

  public boolean validateFlow() {
    return drainageGraph.checkIncreasingFlow();
  }

  public boolean hasTransitiveTerminus(SinusoidalLocation loc) {
    return drainageGraph.getTransitiveTerminus(loc) != null;
  }

  public SinusoidalLocation getTransitiveTerminus(SinusoidalLocation loc) {
    return drainageGraph.getTransitiveTerminus(loc);
  }

  public LocationList getNotLowerNeighbours(SinusoidalLocation loc) {
    LocationList neighbours =
      tectMap.getNeighbourhoods().getElevationOrderedNeighbours(loc);

    LocationList result = new LocationList();

    LocationIterator iterator = neighbours.iterator();

    while (iterator.hasNext()) {
      SinusoidalLocation next = iterator.next();
      if (isNotLowerThan(next, loc)) {
        result.add(next);
      }
    }

    return result;
  }

  private boolean isNotLowerThan(
    SinusoidalLocation neighbour,
    SinusoidalLocation loc) {
    ElevationComparator comparator = new ElevationComparator(tectMap);
    return comparator.isNotLowerThan(neighbour, loc);
  }

  public LocationCollection getSeaDrainedLand() {
    return seaDrainedLand;
  }

  public void setSeaDrainedLand(LocationCollection collection) {
    seaDrainedLand = collection;
  }

  public LocationCollection getNeighbours(SinusoidalLocation loc) {
    return tectMap.getNeighbourhoods().getNeighbours(loc);
  }

  public SinusoidalLocation getLowestNeighbour(SinusoidalLocation loc) {
    return tectMap.getNeighbourhoods().getLowestNeighbour(loc);
  }

  public void setProcess(MapProcess process) {
    this.process = process;
  }

}
