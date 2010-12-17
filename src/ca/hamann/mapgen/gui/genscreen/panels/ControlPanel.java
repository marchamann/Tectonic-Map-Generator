package ca.hamann.mapgen.gui.genscreen.panels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import ca.hamann.mapgen.gui.genscreen.GeneratorScreen;
import ca.hamann.mapgen.gui.genscreen.mapactions.MapAction;

public class ControlPanel extends JPanel {

	private List<JComponent> activeComponents = new ArrayList<JComponent>();
	protected GeneratorScreen screen;

	public ControlPanel(GeneratorScreen screen) {
		super();
		this.screen = screen;
	}

	protected JButton createActionButton(String label, MapAction action) {
		JButton button = new JButton(label);
		button.addActionListener(action);
		activeComponents.add(button);
		return button;
	}

	protected List<JComponent> getActiveCompenents() {
		return activeComponents;
	}

	public void addActiveComponentPanel(ControlPanel panel) {
		activeComponents.addAll(panel.getActiveCompenents());
		add(panel);
	}

	public void addActiveComponent(JComponent component) {
		activeComponents.add(component);
	}
}
