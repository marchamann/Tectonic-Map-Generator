package ca.hamann.mapgen.gui.genscreen;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SaveImageDialog extends JDialog {

  private String imageType = "png";
  private boolean accepted;
  private JTextField filenameInput;
  private SaveImageDialog me = this;

  public SaveImageDialog(Frame owner, String title, boolean modal)
    throws HeadlessException {
    super(owner, title, modal);

    accepted = false;
    init();
    pack();
    setLocationRelativeTo(owner);
  }

  public void init() {

    Container c = getContentPane();

    c.setLayout(new GridLayout(3, 1));

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    c.add(initFilePanel());
    c.add(initImageTypePanel());
    c.add(initCancelSave());
  }

  private JPanel initImageTypePanel() {
    JPanel panel = new JPanel();

    panel.add(new JLabel("Image type:"));

    final JComboBox typeCombo = new JComboBox(new Object[] { "png", "jpg" });
    typeCombo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        imageType = (String) typeCombo.getSelectedItem();
      }
    });

    panel.add(typeCombo);

    return panel;
  }

  private JPanel initFilePanel() {
    JPanel panel = new JPanel();

    JLabel filenameInputLabel = new JLabel("File name:");

    panel.add(filenameInputLabel);

    filenameInput = new JTextField(30);

    panel.add(filenameInput);

    JButton browse = new JButton("Browse");

    browse.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();

        int returnVal = chooser.showSaveDialog(me);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          filenameInput.setText(chooser.getSelectedFile().getAbsolutePath());
        }
      }
    });

    panel.add(browse);

    return panel;
  }

  private JPanel initCancelSave() {
    JPanel panel = new JPanel();
    JButton cancel = new JButton("Cancel");

    cancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        accepted = false;
        me.dispose();
      }
    });

    panel.add(cancel);

    JButton save = new JButton("Save");

    save.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String filename = getFilename();
        if (filename != null && !filename.trim().equals("")) {
          accepted = true;
          me.dispose();
        } else {
          JOptionPane.showMessageDialog(me, "You must select a file name!");
        }

      }
    });

    panel.add(save);

    return panel;
  }

  protected String getFilename() {
    return filenameInput.getText();
  }

  protected String getImageType() {
    return imageType;
  }

  protected boolean getAccepted() {
    return accepted;
  }

}
