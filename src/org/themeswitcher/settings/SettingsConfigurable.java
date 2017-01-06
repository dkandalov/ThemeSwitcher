package org.themeswitcher.settings;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SettingsConfigurable implements SearchableConfigurable {
	private SettingsUI settingsUI;

	@NotNull @Override public String getId() {
		return "Theme Switcher";
	}

	@Nls @Override public String getDisplayName() {
		return getId();
	}

	@Nullable @Override public JComponent createComponent() {
		settingsUI = new SettingsUI();
		return settingsUI.panel;
	}

	@Override public void disposeUIResources() {
		settingsUI = null;
	}

	@Nullable @Override public String getHelpTopic() {
		return null;
	}

	@Override public boolean isModified() {
		return !uiIsDisposed() && settingsUI.isNotEqual(ServiceManager.getService(PluginSettings.class));
	}

	@Override public void apply() throws ConfigurationException {
		if (uiIsDisposed()) return;
		settingsUI.saveState(ServiceManager.getService(PluginSettings.class));
	}

	@Override public void reset() {
		if (uiIsDisposed()) return;
		settingsUI.loadState(ServiceManager.getService(PluginSettings.class));
	}

	private boolean uiIsDisposed() {
		return settingsUI == null;
	}
}
