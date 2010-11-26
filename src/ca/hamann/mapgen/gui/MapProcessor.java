package ca.hamann.mapgen.gui;

import ca.hamann.mapgen.gui.genscreen.GeneratorScreen;
import ca.hamann.mapgen.gui.genscreen.MapProcess;
import ca.hamann.mapgen.tectonic.*;

public interface MapProcessor {
  TectonicMap processMap();
  void afterProcess(GeneratorScreen screen);
  String getProcessName();
  void setProcess(MapProcess process);
}
