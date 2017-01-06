package org.themeswitcher.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

@State(name = "SaveTimesSettings", storages = {@Storage(file = "/savetimes_settings.xml")})
public class PluginSettings implements PersistentStateComponent<PluginSettings> {

	public String timeToLightMs;
	public String timeToDarkMs;

	@Nullable @Override public PluginSettings getState() {
		return this;
	}

	@Override public void loadState(PluginSettings state) {
		XmlSerializerUtil.copyBean(state, this);
	}

	public void setConfig(Date timeToLight, Date timeToDark) {
		this.timeToLightMs = Long.toString(timeToLight.getTime());
		this.timeToDarkMs = Long.toString(timeToDark.getTime());
	}

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PluginSettings that = (PluginSettings) o;

		return (timeToLightMs != null ? timeToLightMs.equals(that.timeToLightMs) : that.timeToLightMs == null) &&
				(timeToDarkMs != null ? timeToDarkMs.equals(that.timeToDarkMs) : that.timeToDarkMs == null);
	}

	@Override public int hashCode() {
		int result = timeToLightMs != null ? timeToLightMs.hashCode() : 0;
		result = 31 * result + (timeToDarkMs != null ? timeToDarkMs.hashCode() : 0);
		return result;
	}
}
