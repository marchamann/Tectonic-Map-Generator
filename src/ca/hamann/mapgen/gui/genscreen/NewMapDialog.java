package ca.hamann.mapgen.gui.genscreen;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.hamann.mapgen.MapConfiguration;

public class NewMapDialog extends JDialog {

  private int currentGridRow = 0;

  private JDialog me;
  private GeneratorScreen screen;

  private JLabel plateCountLabel;
  private JTextField plateCountField;

  private JLabel landPlateCountLabel;
  private JTextField landPlateCountField;

  private JLabel randomSeedLabel;
  private JTextField randomSeedField;

  private JLabel baseLandElevationLabel;
  private JTextField baseLandElevationField;

  private JLabel baseSeaElevationLabel;
  private JTextField baseSeaElevationField;

  private JButton cancel;
  private JButton makeMap;

  MapConfiguration config;

  public NewMapDialog(GeneratorScreen screen, String title, boolean modal)
    throws HeadlessException {
    super(screen, title, modal);
    this.screen = screen;
    config = screen.getMapConfiguration();

    init();
    pack();
    setLocationRelativeTo(screen);
    me = this;

  }

  private int getPlateCount() {
    return getPositiveIntFromField(plateCountField);
  }

  private int getLandPlateCount() {
    return getPositiveIntFromField(landPlateCountField);
  }

  private int getBaseLandElevation() {
    return getPositiveIntFromField(baseLandElevationField);
  }

  private int getBaseSeaElevation() {
    return getPositiveIntFromField(baseSeaElevationField);
  }

  private int getPositiveIntFromField(JTextField field) {
    String text = field.getText();

    if (text.trim().equals("")) {
      return -1;
    }

    return Integer.parseInt(text);
  }

  private void init() {

    Container pane = this.getContentPane();
    pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

    JPanel c = new JPanel();
    GridBagLayout layout = new GridBagLayout();
    c.setLayout(layout);

    plateCountLabel = new JLabel("Total Plates");
    plateCountField = new JTextField(4);
    plateCountField.setText("" + config.getPlateCount());

    plateCountField.setInputVerifier(new InputVerifier() {

      public boolean verify(JComponent input) {
        int value = getPlateCount();

        if (value > 2 && value <= 200) {
          return true;
        }
        return false;
      }
      public boolean shouldYieldFocus(JComponent input) {
        JTextField field = (JTextField) input;

        boolean result = verify(input);
        if (!result) {
          field.setText("" + config.getPlateCount());
        }
        return result;
      }
    });

    //    c.add(getPairPanel(plateCountLabel, plateCountField));
    addLabelFieldPair(c, plateCountLabel, plateCountField);

    landPlateCountLabel = new JLabel("Land Plates");
    landPlateCountField = new JTextField(4);
    landPlateCountField.setText("" + config.getStartingLandPlateCount());

    landPlateCountField.setInputVerifier(new InputVerifier() {

      public boolean verify(JComponent input) {
        int value = getLandPlateCount();

        if (value >= 0 && value <= getPlateCount()) {
          return true;
        }
        return false;
      }

      public boolean shouldYieldFocus(JComponent input) {
        JTextField field = (JTextField) input;

        boolean result = verify(input);
        if (!result) {
          field.setText("" + config.getStartingLandPlateCount());
        }
        return result;
      }
    });

    //    c.add(getPairPanel(landPlateCountLabel, landPlateCountField));
    addLabelFieldPair(c, landPlateCountLabel, landPlateCountField);

    baseLandElevationLabel = new JLabel("Land Elevation");
    baseLandElevationField = new JTextField(4);
    baseLandElevationField.setText("" + config.getBaseLandElevation());

    baseLandElevationField.setInputVerifier(new InputVerifier() {

      public boolean verify(JComponent input) {
        int value = getBaseLandElevation();

        if (value >= 0 && value <= 255) {
          return true;
        }
        return false;
      }

      public boolean shouldYieldFocus(JComponent input) {
        JTextField field = (JTextField) input;

        boolean result = verify(input);
        if (!result) {
          field.setText("" + config.getBaseLandElevation());
        }
        return result;
      }
    });

    //    c.add(getPairPanel(baseLandElevationLabel, baseLandElevationField));
    addLabelFieldPair(c, baseLandElevationLabel, baseLandElevationField);

    baseSeaElevationLabel = new JLabel("Sea Elevation");
    baseSeaElevationField = new JTextField(4);
    baseSeaElevationField.setText("" + config.getBaseSeaElevation());

    baseSeaElevationField.setInputVerifier(new InputVerifier() {

      public boolean verify(JComponent input) {
        int value = getBaseSeaElevation();

        if (value >= 0 && value < getBaseLandElevation()) {
          return true;
        }
        return false;
      }

      public boolean shouldYieldFocus(JComponent input) {
        JTextField field = (JTextField) input;

        boolean result = verify(input);
        if (!result) {
          field.setText("" + config.getBaseSeaElevation());
        }
        return result;
      }
    });

    //    c.add(getPairPanel(baseSeaElevationLabel, baseSeaElevationField));
    addLabelFieldPair(c, baseSeaElevationLabel, baseSeaElevationField);

    randomSeedLabel = new JLabel("Random Seed");
    randomSeedField = new JTextField(4);

    //    c.add(getPairPanel(randomSeedLabel, randomSeedField));
    addLabelFieldPair(c, randomSeedLabel, randomSeedField);

    cancel = new JButton("Cancel");

    cancel.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        me.dispose();
      }
    });

    makeMap = new JButton("Make Map");

    getRootPane().setDefaultButton(makeMap);

    makeMap.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        initConfig();
        me.dispose();
      }

    });

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridy = currentGridRow++;
    constraints.gridwidth = 2;
    pane.add(c);
    pane.add(getPairPanel(cancel, makeMap), constraints);

  }

  private void addLabelFieldPair(Container c, JLabel label, JTextField field) {
    label.setHorizontalAlignment(JLabel.RIGHT);
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridy = currentGridRow++;

    constraints.anchor = GridBagConstraints.EAST;
    c.add(label, constraints);
    field.setHorizontalAlignment(JTextField.RIGHT);

    constraints.anchor = GridBagConstraints.WEST;
    constraints.gridx = 1;
    c.add(field, constraints);
  }

  private void initConfig() {

    config.setPlateCount(Integer.parseInt(plateCountField.getText()));
    config.setStartingLandPlateCount(
      Integer.parseInt(landPlateCountField.getText()));

    config.setBaseLandElevation(
      Integer.parseInt(baseLandElevationField.getText()));
    config.setBaseSeaElevation(
      Integer.parseInt(baseSeaElevationField.getText()));

    String seedStr = randomSeedField.getText();

    long seed = 0;
    if (seedStr.trim().equals("")) {
      seed = System.currentTimeMillis();
    } else {
      seed = Long.parseLong(seedStr);
    }

    config.setStartingSeed(seed);

    screen.initMaps(config);
  }

  private Component getPairPanel(JComponent left, JComponent right) {
    JPanel fieldPanel = new JPanel();

    fieldPanel.add(left);
    fieldPanel.add(right);
    return fieldPanel;
  }
}
