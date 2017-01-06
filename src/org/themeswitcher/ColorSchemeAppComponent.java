package org.themeswitcher;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.ServiceManager;
import org.jetbrains.annotations.NotNull;
import org.themeswitcher.settings.PluginSettings;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Locale;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;

public class ColorSchemeAppComponent implements ApplicationComponent {

	@Override public void initComponent() {
		ThemeSwitcher themeSwitcher = new ThemeSwitcher();
		themeSwitcher.initTheme();

		Thread thread = new Thread(() -> {
			Calendar calendar = Calendar.getInstance(Locale.getDefault());
			PluginSettings settings = ServiceManager.getService(PluginSettings.class);

			while (true) {
				calendar.setTimeInMillis(Long.valueOf(settings.timeToLightMs));
				LocalTime timeToLight = LocalTime.of(calendar.get(HOUR_OF_DAY), calendar.get(MINUTE));

				calendar.setTimeInMillis(Long.valueOf(settings.timeToDarkMs));
				LocalTime timeToDark = LocalTime.of(calendar.get(HOUR_OF_DAY), calendar.get(MINUTE));

				if (timeToLight.equals(LocalTime.now()) || timeToDark.equals(LocalTime.now())) {
					ApplicationManager.getApplication().invokeLater(themeSwitcher::switchTheme);
					try {
						Thread.sleep(2 * 60 * 1000);
					} catch (InterruptedException ignored) {
					}
				}
			}
		});
		thread.start();
	}

	@Override public void disposeComponent() {
	}

	@NotNull public String getComponentName() {
		return getClass().getCanonicalName();
	}
}