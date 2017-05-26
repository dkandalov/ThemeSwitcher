package org.themeswitcher.settings;

import javax.swing.*;

class SettingsUI {
	public JSpinner lightTimeSpinner;
	public JSpinner darkTimeSpinner;
	public JPanel panel;

	private void createUIComponents() {
		lightTimeSpinner = new JSpinner(new SpinnerDateModel());
		lightTimeSpinner.setEditor(new JSpinner.DateEditor(lightTimeSpinner, "HH:mm"));

		darkTimeSpinner = new JSpinner(new SpinnerDateModel());
		darkTimeSpinner.setEditor(new JSpinner.DateEditor(darkTimeSpinner, "HH:mm"));
	}
}
