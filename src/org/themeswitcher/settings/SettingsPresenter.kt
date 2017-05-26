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
        !uiIsDisposed() && uiStateAsSettings() != PluginSettings.instance

    override fun apply() {
        if (uiIsDisposed()) return
        saveUIStateTo(PluginSettings.instance)
    }

    override fun reset() {
        if (uiIsDisposed()) return
        loadUIStateFrom(PluginSettings.instance)
    }

    private fun saveUIStateTo(settings: PluginSettings) {
        settings.loadState(uiStateAsSettings())
    }

    private fun loadUIStateFrom(settings: PluginSettings) {
        settingsUI!!.lightTimeSpinner.value = Date(settings.lightTime.get(MILLI_OF_DAY).toLong())
        settingsUI!!.darkTimeSpinner.value = Date(settings.darkTime.get(MILLI_OF_DAY).toLong())
    }

    override fun enableSearch(option: String) = null

    override fun getHelpTopic() = null

    private fun uiIsDisposed() = settingsUI == null

    private fun uiStateAsSettings(): PluginSettings {
        val settings = PluginSettings()
        settings.setState(
            LocalTime.ofNanoOfDay((settingsUI!!.lightTimeSpinner.value as Date).time * 1000),
            LocalTime.ofNanoOfDay((settingsUI!!.darkTimeSpinner.value as Date).time * 1000)
        )
        return settings
    }
}
