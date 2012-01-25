package ca.hamann.mapgen.tectonic;

import ca.hamann.mapgen.MapConfiguration;
import ca.hamann.mapgen.containers.LocationCollection;
import ca.hamann.mapgen.containers.LocationIterator;
import ca.hamann.mapgen.containers.traversal.AbstractLocationVisitor;
import ca.hamann.mapgen.containers.traversal.LocationContainerTraversal;
import ca.hamann.mapgen.gui.MapProcessor;
import ca.hamann.mapgen.gui.genscreen.GeneratorScreen;
import ca.hamann.mapgen.gui.genscreen.MapProcess;
import ca.hamann.mapgen.sinusoidal.SinusoidalLocation;

public class Eroder implements MapProcessor {

  private TectonicMap tectMap;
  private MapConfiguration config;

  public Eroder(TectonicMap tectMap) {
    this.tectMap = tectMap;
    config = tectMap.getConfiguration();
  }

  public int erodeLocation(SinusoidalLocation loc) {
    int elevation = tectMap.getElevation(loc);

    tectMap.setAccumulatedWater(loc, 0);
    tectMap.setFlow(loc, 0);

    LocationCollection neighbours = getElevationOrderedNeighbours(loc);
    LocationIterator iterator = neighbours.iterator();
    SinusoidalLocation neighbour = null;

    int erosionCount = 0;

    while (iterator.hasNext() && erosionCount < config.getErosionLimit()) {
      neighbour = iterator.next();
      int neighbourElevation = tectMap.getElevation(neighbour);

      if (neighbourElevation < elevation) {
        erosionCount++;
        tectMap.setElevation(loc, --elevation);
        tectMap.setElevation(neighbour, ++neighbourElevation);
      }
    }

    return elevation;

  }

  private LocationCollection getElevationOrderedNeighbours(SinusoidalLocation loc) {
    return tectMap.getNeighbourhoods().getElevationOrderedNeighbours(loc);
  }

  public TectonicMap erodeMap() {
    LocationContainerTraversal traversal =
      new LocationContainerTraversal(tectMap.getGrid());

    traversal.traverse(new AbstractLocationVisitor() {

      public void visitLocation(SinusoidalLocation loc) {
        erodeLocation(loc);
      }
    });

    return tectMap;
  }

  public TectonicMap processMap() {
    return erodeMap();
  }

  public String getProcessName() {
    return "Eroding";
  }

  public void setProcess(MapProcess process) {
  }

  public void afterProcess(GeneratorScreen screen) {
  }

}
