package ca.hamann.mapgen.gui.genscreen.panels;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import ca.hamann.mapgen.gui.genscreen.GeneratorScreen;
import ca.hamann.mapgen.gui.genscreen.mapactions.DriftAction;
import ca.hamann.mapgen.gui.genscreen.mapactions.ErosionAction;

public class IteratedOperationsPanel extends ControlPanel {


	public IteratedOperationsPanel(GeneratorScreen screen) {
		super(screen);

		setBorder(new EtchedBorder());

		add(new JLabel("Iterated Actions"));

		add(createdIteratedOperationsButtons());
		add(createIterationsInputPanel());
	}

	private ControlPanel createIterationsInputPanel() {
		ControlPanel iterationsInputPanel = new ControlPanel(screen);
		iterationsInputPanel.setBorder(new EtchedBorder());
		iterationsInputPanel.add(new JLabel("Iterations"));
		iterationsInputPanel.add(initIterationsInput());
		return iterationsInputPanel;
	}

	private ControlPanel createdIteratedOperationsButtons() {
		ControlPanel iteratedOperationsButtons = new ControlPanel(screen);

		iteratedOperationsButtons.add(initDriftButton());
		iteratedOperationsButtons.add(initErosionButton());
		return iteratedOperationsButtons;
	}

	private JButton initDriftButton() {
		return createActionButton("Drift", new DriftAction(screen));
	}

	private JButton initErosionButton() {
		return createActionButton("Erosion", new ErosionAction(screen));
	}

	private JTextField initIterationsInput() {
		JTextField iterationsInput = new JTextField(6);
		iterationsInput.setText("1");
		iterationsInput.setHorizontalAlignment(JTextField.RIGHT);
		iterationsInput.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				int parseInt = 1;
				JTextField field = (JTextField) (e.getSource());
				try {
					parseInt = Integer.parseInt(field.getText().trim());
				} catch (NumberFormatException nfe) {
				}
				field.setText(Integer.toString(parseInt));
				screen.setIterations(parseInt);
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		addActiveComponent(iterationsInput);
		return iterationsInput;
	}

}
