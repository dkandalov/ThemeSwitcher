package org.themeswitcher.settings;

import javax.swing.*;

class SettingsUI {
	public JSpinner lightFromTimeSpinner;
	public JSpinner lightToTimeSpinner;
	public JPanel panel;

	private void createUIComponents() {
		lightFromTimeSpinner = new JSpinner(new SpinnerDateModel());
		lightFromTimeSpinner.setEditor(new JSpinner.DateEditor(lightFromTimeSpinner, "HH:mm"));

		lightToTimeSpinner = new JSpinner(new SpinnerDateModel());
		lightToTimeSpinner.setEditor(new JSpinner.DateEditor(lightToTimeSpinner, "HH:mm"));
	}
}
