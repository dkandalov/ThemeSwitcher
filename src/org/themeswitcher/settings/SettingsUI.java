package org.themeswitcher.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SettingsUI {
	private JSpinner timeToLight;
	private JSpinner timeToDark;
	public JPanel panel;

	private void createUIComponents() {
		timeToLight = new JSpinner(new SpinnerDateModel());
		timeToLight.setEditor(new JSpinner.DateEditor(timeToLight, "HH:mm"));

		timeToDark = new JSpinner(new SpinnerDateModel());
		timeToDark.setEditor(new JSpinner.DateEditor(timeToDark, "HH:mm"));
	}

	public void loadState(PluginSettings settings) {
		if (settings.getTimeToLightMs() != null) {
			Calendar calendar = Calendar.getInstance(Locale.getDefault());
			calendar.setTimeInMillis(Long.valueOf(settings.getTimeToLightMs()));
			timeToLight.setValue(calendar.getTime());
		}
		if (settings.getTimeToDarkMs() != null) {
			Calendar calendar = Calendar.getInstance(Locale.getDefault());
			calendar.setTimeInMillis(Long.valueOf(settings.getTimeToDarkMs()));
			timeToDark.setValue(calendar.getTime());
		}
	}

	public void saveState(PersistentStateComponent<PluginSettings> stateComponent) {
		stateComponent.loadState(stateAsSettings());
	}

	public boolean isNotEqual(PluginSettings settings) {
		return !stateAsSettings().equals(settings);
	}

	@NotNull private PluginSettings stateAsSettings() {
		PluginSettings settings = new PluginSettings();
		settings.setConfig((Date) timeToLight.getValue(), (Date) timeToDark.getValue());
		return settings;
	}
}
