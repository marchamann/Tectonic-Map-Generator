package ca.hamann.mapgen.gui.genscreen;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import ca.hamann.mapgen.Rotater;
import ca.hamann.mapgen.drainage.DrainageProcessor;
import ca.hamann.mapgen.tectonic.Eroder;

public class ExecutionPanel extends JPanel {
	private GeneratorScreen screen;

	private List<JComponent> activeComponents = new ArrayList<JComponent>();

	private JTextField iterationsInput;

	public ExecutionPanel(GeneratorScreen screen) {
		this.screen = screen;
		add(createIteratedOperationsPanel());
		add(createRiversPanel());
		add(createRotationsPanel());
	}

	private JPanel createIteratedOperationsPanel() {
		iterationsInput = initIterationsInput();
		activeComponents.add(iterationsInput);

		JPanel iteratedOperations = new JPanel();
		iteratedOperations.setBorder(new EtchedBorder());

		iteratedOperations.add(new JLabel("Iterated Actions"));
		iteratedOperations.add(createdIteratedOperationsButtons());

		iteratedOperations.add(createIterationsInputPanel());
		return iteratedOperations;
	}

	private JPanel createIterationsInputPanel() {
		JPanel iterationsInputPanel = new JPanel();
		iterationsInputPanel.setBorder(new EtchedBorder());
		iterationsInputPanel.add(new JLabel("Iterations"));
		iterationsInputPanel.add(iterationsInput);
		return iterationsInputPanel;
	}

	private JPanel createdIteratedOperationsButtons() {
		JPanel iteratedOperationsButtons = new JPanel();

		iteratedOperationsButtons.add(initDriftButton());
		iteratedOperationsButtons.add(initErosionButton());
		return iteratedOperationsButtons;
	}

	private JPanel createRiversPanel() {
		JPanel rivers = new JPanel();
		rivers.setBorder(new EtchedBorder());
		rivers.add(new JLabel("Rivers"));
		rivers.add(initAddRiversButton());
		return rivers;
	}

	private JPanel createRotationsPanel() {
		JPanel rotations = new JPanel();
		rotations.setBorder(new EtchedBorder());
		rotations.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 1;
		rotations.add(initRotateWestButton(), constraints);
		constraints.gridx = 1;
		rotations.add(initRotateEastButton(), constraints);

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		rotations.add(new JLabel("Rotate"), constraints);
		return rotations;
	}

	private JTextField initIterationsInput() {
		JTextField iterationsInput = new JTextField(6);
		iterationsInput.setText("1");
		iterationsInput.setHorizontalAlignment(JTextField.RIGHT);
		activeComponents.add(iterationsInput);
		return iterationsInput;
	}

	private JButton initDriftButton() {
		JButton drift = new JButton("Drift");
		drift.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setEnabledControls(false);
				MapProcess driftProcess = screen.getNewMapProcess();
				driftProcess.setProcessor(screen.getDrifter());
				driftProcess.setIterations(getIterations());
				driftProcess.start();
			}
		});
		activeComponents.add(drift);
		return drift;
	}

	private JButton initErosionButton() {
		JButton erosion = new JButton("Erosion");

		erosion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setEnabledControls(false);
				MapProcess erosionProcess = screen.getNewMapProcess();
				erosionProcess.setProcessor(new Eroder(screen.getTectonicMap()));
				erosionProcess.setIterations(getIterations());
				erosionProcess.start();
			}
		});
		activeComponents.add(erosion);
		return erosion;
	}

	private JButton initRotateEastButton() {
		JButton rotateEast = new JButton("East");

		rotateEast.setMnemonic(KeyEvent.VK_PERIOD);

		rotateEast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setEnabledControls(false);
				MapProcess rotateProcess = screen.getNewMapProcess();
				rotateProcess.setProcessor(new Rotater(screen.getTectonicMap(),
						true));
				rotateProcess.setIterations(1);
				rotateProcess.start();
			}
		});
		activeComponents.add(rotateEast);
		return rotateEast;
	}

	private JButton initRotateWestButton() {
		JButton rotateWest = new JButton("West");

		rotateWest.setMnemonic(KeyEvent.VK_COMMA);

		rotateWest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setEnabledControls(false);
				MapProcess rotateProcess = screen.getNewMapProcess();
				rotateProcess.setProcessor(new Rotater(screen.getTectonicMap(),
						false));
				rotateProcess.setIterations(1);
				rotateProcess.start();
			}
		});

		activeComponents.add(rotateWest);
		return rotateWest;
	}

	private JButton initAddRiversButton() {
		JButton addRivers = new JButton("Add Rivers");

		addRivers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setEnabledControls(false);

				MapProcess riverProcess = screen.getNewMapProcess();
				riverProcess.setProcessor(new DrainageProcessor(screen
						.getTectonicMap()));
				riverProcess.setIterations(1);
				riverProcess.start();
			}
		});

		activeComponents.add(addRivers);
		return addRivers;
	}

	public void setEnableControls(boolean enabled) {
		for (JComponent c : activeComponents) {
			c.setEnabled(enabled);
		}
	}

	public int getIterations() {
		return Integer.parseInt(iterationsInput.getText().trim());
	}
}
