package ca.hamann.mapgen.gui.genscreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

import ca.hamann.mapgen.gui.projections.CylindricalProjection;
import ca.hamann.mapgen.gui.projections.SinusoidalProjection;
import ca.hamann.mapgen.gui.projections.SinusoidalSeparatedProjection;

public class ProjectionsMenu extends JMenu {
  private GeneratorScreen screen;
  private JRadioButtonMenuItem sinusoidalSeparated, sinusoidal, cylindrical;
  private ButtonGroup modeButtons;

  public ProjectionsMenu(GeneratorScreen screen) {
    super("Projection");
    this.screen = screen;
    initDisplayModes();

    initButtonGroup();

    add(sinusoidalSeparated);
    add(sinusoidal);
    add(cylindrical);

  }

  private void initButtonGroup() {
    modeButtons = new ButtonGroup();
    modeButtons.add(sinusoidalSeparated);
    modeButtons.add(sinusoidal);
    modeButtons.add(cylindrical);

    modeButtons.setSelected(sinusoidalSeparated.getModel(), true);
  }

  private void initDisplayModes() {
    sinusoidalSeparated = new JRadioButtonMenuItem("Sinusoidal Separated");
    sinusoidalSeparated.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        screen.setCurrentProjection(
          new SinusoidalSeparatedProjection(screen.getGrid()));
        screen.updateImage();
      }
    });

    sinusoidal = new JRadioButtonMenuItem("Sinusoidal");
    sinusoidal.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        screen.setCurrentProjection(new SinusoidalProjection(screen.getGrid()));
        screen.updateImage();
      }
    });

    cylindrical = new JRadioButtonMenuItem("Cylindrical");
    cylindrical.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        screen.setCurrentProjection(new CylindricalProjection(screen.getGrid()));
        screen.updateImage();
      }
    });

  }

}
