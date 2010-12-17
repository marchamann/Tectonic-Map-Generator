package ca.hamann.mapgen.gui.genscreen.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;

import ca.hamann.mapgen.gui.genscreen.GeneratorScreen;
import ca.hamann.mapgen.gui.genscreen.mapactions.AddRiversAction;
import ca.hamann.mapgen.gui.genscreen.mapactions.RotateAction;

public class ExecutionPanel extends ControlPanel {

	public ExecutionPanel(GeneratorScreen screen) {
		super(screen);
		addActiveComponentPanel(new IteratedOperationsPanel(screen));
		addActiveComponentPanel(createRiversPanel());
		addActiveComponentPanel(createRotationsPanel());
	}

	private ControlPanel createRiversPanel() {
		ControlPanel rivers = new ControlPanel(screen);
		rivers.setBorder(new EtchedBorder());
		rivers.add(new JLabel("Rivers"));
		rivers.add(initAddRiversButton());
		return rivers;
	}

	private ControlPanel createRotationsPanel() {
		ControlPanel rotations = new ControlPanel(screen);
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

	private JButton initRotateEastButton() {
		return createActionButton("East", new RotateAction(screen, true));
	}

	private JButton initRotateWestButton() {
		return createActionButton("West", new RotateAction(screen, false));
	}

	private JButton initAddRiversButton() {
		return createActionButton("Add Rivers", new AddRiversAction(screen));
	}

	public void setEnableControls(boolean enabled) {
		for (JComponent c : getActiveCompenents()) {
			c.setEnabled(enabled);
		}
	}

}
