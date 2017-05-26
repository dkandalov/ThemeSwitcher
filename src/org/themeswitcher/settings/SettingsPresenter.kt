package org.themeswitcher.settings

import com.intellij.openapi.options.SearchableConfigurable
import org.jetbrains.annotations.Nls
import java.time.LocalTime
import java.time.temporal.ChronoField.MILLI_OF_DAY
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
        lightTimeSpinner.value = Date(settings.lightTime.get(MILLI_OF_DAY).toLong())
        darkTimeSpinner.value = Date(settings.darkTime.get(MILLI_OF_DAY).toLong())
    }

    private fun SettingsUI.toPluginSettings(): PluginSettings {
        return PluginSettings().apply {
            setState(
                LocalTime.ofNanoOfDay((lightTimeSpinner.value as Date).time * 1000),
                LocalTime.ofNanoOfDay((darkTimeSpinner.value as Date).time * 1000)
            )
        }
    }
}
