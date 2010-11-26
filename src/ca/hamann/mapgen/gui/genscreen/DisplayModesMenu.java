package ca.hamann.mapgen.gui.genscreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

import ca.hamann.mapgen.gui.colourers.CollisionsColourer;
import ca.hamann.mapgen.gui.colourers.ElevationColourer;
import ca.hamann.mapgen.gui.colourers.PlateColourer;
import ca.hamann.mapgen.gui.colourers.RiversColourer;

public class DisplayModesMenu extends JMenu {

  private GeneratorScreen screen;
  private JRadioButtonMenuItem plateMode, collisionMode,
  //    landSeaMode,
  elevationMode, riversMode;
  private ButtonGroup modeButtons;

  public DisplayModesMenu(GeneratorScreen screen) {
    super("View");
    this.screen = screen;
    initDisplayModes();

    initButtonGroup();

    add(riversMode);
    add(elevationMode);
    add(plateMode);
    add(collisionMode);
    //    add(landSeaMode);

  }

  private void initButtonGroup() {
    modeButtons = new ButtonGroup();
    modeButtons.add(riversMode);
    modeButtons.add(elevationMode);
    modeButtons.add(plateMode);
    modeButtons.add(collisionMode);
    //    modeButtons.add(landSeaMode);

    activateRiverMode();
  }

  private void initDisplayModes() {
    plateMode = new JRadioButtonMenuItem("Plates");
    plateMode.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        screen.setCurrentColourer(new PlateColourer(screen.getTectonicMap()));
        screen.updateImage();
      }
    });

    collisionMode = new JRadioButtonMenuItem("Collisions");
    collisionMode.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        screen.setCurrentColourer(
          new CollisionsColourer(screen.getTectonicMap()));
        screen.updateImage();
      }
    });

    //    landSeaMode = new JRadioButtonMenuItem("Land/Sea");
    //    landSeaMode.addActionListener(new ActionListener() {
    //
    //      public void actionPerformed(ActionEvent e) {
    //        screen.setCurrentColourer(new LandSeaColourer(screen.getTectonicMap()));
    //        screen.updateImage();
    //      }
    //    });

    elevationMode = new JRadioButtonMenuItem("Elevation");
    elevationMode.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        screen.setCurrentColourer(
          new ElevationColourer(screen.getTectonicMap()));
        screen.updateImage();
      }
    });

    riversMode = new JRadioButtonMenuItem("Elevation/Rivers");
    riversMode.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        screen.setCurrentColourer(new RiversColourer(screen.getTectonicMap()));
        screen.updateImage();
      }
    });

  }

  public void activateRiverMode() {
    modeButtons.setSelected(riversMode.getModel(), true);
  }

  public void activatePlateMode() {
    modeButtons.setSelected(plateMode.getModel(), true);
  }

}
