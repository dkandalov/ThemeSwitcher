package org.themeswitcher.settings

import com.intellij.openapi.options.SearchableConfigurable
import org.jetbrains.annotations.Nls
import java.util.*
import javax.swing.JComponent

class SettingsPresenter: SearchableConfigurable {
    private var settingsUI: SettingsUI? = null

    override fun getId(): String = "Theme Switcher"

    @Nls override fun getDisplayName() = id

    override fun createComponent(): JComponent? {
        settingsUI = SettingsUI()
        return settingsUI!!.panel
    }

    override fun disposeUIResources() {
        settingsUI = null
    }

    override fun isModified() =
        settingsUI?.toPluginSettings() != PluginSettings.instance

    override fun apply() {
        settingsUI?.let {
            PluginSettings.instance.loadState(it.toPluginSettings())
        }
    }

    override fun reset() {
        settingsUI?.loadStateFrom(PluginSettings.instance)
    }

    override fun enableSearch(option: String) = null

    override fun getHelpTopic() = null

    private fun SettingsUI.loadStateFrom(settings: PluginSettings) {
        val (lightTime, darkTime) = settings.time()
        lightTimeSpinner.value = Date(lightTime.toMillis())
        darkTimeSpinner.value = Date(darkTime.toMillis())
    }

    private fun SettingsUI.toPluginSettings(): PluginSettings {
        return PluginSettings().apply {
            setTime(
                (lightTimeSpinner.value as Date).time.millisToLocalTime(),
                (darkTimeSpinner.value as Date).time.millisToLocalTime()
            )
        }
    }
}
