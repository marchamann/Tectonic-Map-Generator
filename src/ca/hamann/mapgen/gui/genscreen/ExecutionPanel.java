package ca.hamann.mapgen.gui.genscreen;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import ca.hamann.mapgen.Rotater;
import ca.hamann.mapgen.drainage.DrainageProcessor;
import ca.hamann.mapgen.tectonic.Eroder;

public class ExecutionPanel extends JPanel {
  private GeneratorScreen screen;

  private JButton drift, erosion, rotateEast, rotateWest, addRivers;
  private JTextField iterationsInput;

  public ExecutionPanel(GeneratorScreen screen) {
    this.screen = screen;
    init();
  }

  private void init() {
    initDriftButton();
    initErosionButton();
    initRotateEastButton();
    initRotateWestButton();
    initAddRiversButton();

    iterationsInput = new JTextField(6);
    iterationsInput.setText("1");
    iterationsInput.setHorizontalAlignment(JTextField.RIGHT);

    JPanel iteratedOperationsButtons = new JPanel();

    iteratedOperationsButtons.add(drift);
    iteratedOperationsButtons.add(erosion);

    JPanel iteratedOperations = new JPanel();
    iteratedOperations.setBorder(new EtchedBorder());

    iteratedOperations.add(new JLabel("Iterated Actions"));
    iteratedOperations.add(iteratedOperationsButtons);

    JPanel iterationsInputPanel = new JPanel();
    iterationsInputPanel.setBorder(new EtchedBorder());
    iterationsInputPanel.add(new JLabel("Iterations"));
    iterationsInputPanel.add(iterationsInput);

    iteratedOperations.add(iterationsInputPanel);

    JPanel rivers = new JPanel();
    rivers.setBorder(new EtchedBorder());
    rivers.add(new JLabel("Rivers"));
    rivers.add(addRivers);

    JPanel rotations = new JPanel();
    rotations.setBorder(new EtchedBorder());
    rotations.setLayout(new GridBagLayout());

    GridBagConstraints constraints = new GridBagConstraints();

    constraints.gridx = 0;
    constraints.gridy = 1;
    rotations.add(rotateWest, constraints);
    constraints.gridx = 1;
    rotations.add(rotateEast, constraints);

    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.gridwidth = 2;
    rotations.add(new JLabel("Rotate"), constraints);

    add(iteratedOperations);
    add(rivers);
    add(rotations);

  }

  private void initDriftButton() {
    drift = new JButton("Drift");
    drift.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        screen.setEnabledControls(false);
        MapProcess driftProcess = screen.getNewMapProcess();
        driftProcess.setProcessor(screen.getDrifter());
        driftProcess.setIterations(getIterations());
        driftProcess.start();
      }
    });
  }

  private void initErosionButton() {
    erosion = new JButton("Erosion");

    erosion.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        screen.setEnabledControls(false);
        MapProcess erosionProcess = screen.getNewMapProcess();
        erosionProcess.setProcessor(new Eroder(screen.getTectonicMap()));
        erosionProcess.setIterations(getIterations());
        erosionProcess.start();
      }
    });
  }

  private void initRotateEastButton() {
    rotateEast = new JButton("East");

    rotateEast.setMnemonic(KeyEvent.VK_PERIOD);

    rotateEast.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        screen.setEnabledControls(false);
        MapProcess rotateProcess = screen.getNewMapProcess();
        rotateProcess.setProcessor(new Rotater(screen.getTectonicMap(), true));
        rotateProcess.setIterations(1);
        rotateProcess.start();
      }
    });
  }

  private void initRotateWestButton() {
    rotateWest = new JButton("West");

    rotateWest.setMnemonic(KeyEvent.VK_COMMA);

    rotateWest.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        screen.setEnabledControls(false);
        MapProcess rotateProcess = screen.getNewMapProcess();
        rotateProcess.setProcessor(new Rotater(screen.getTectonicMap(), false));
        rotateProcess.setIterations(1);
        rotateProcess.start();
      }
    });
  }

  private void initAddRiversButton() {
    addRivers = new JButton("Add Rivers");

    addRivers.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        screen.setEnabledControls(false);

        MapProcess riverProcess = screen.getNewMapProcess();
        riverProcess.setProcessor(
          new DrainageProcessor(screen.getTectonicMap()));
        riverProcess.setIterations(1);
        riverProcess.start();
      }
    });
  }

  public void setEnableControls(boolean enabled) {
    drift.setEnabled(enabled);
    erosion.setEnabled(enabled);
    iterationsInput.setEnabled(enabled);

    rotateEast.setEnabled(enabled);
    rotateWest.setEnabled(enabled);

    addRivers.setEnabled(enabled);
  }

  public int getIterations() {
    return Integer.parseInt(iterationsInput.getText().trim());
  }
}
