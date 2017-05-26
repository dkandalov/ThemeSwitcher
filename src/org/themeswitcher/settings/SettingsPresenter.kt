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
        val calendar = Calendar.getInstance(Locale.getDefault())

        calendar.timeInMillis = settings.timeToLightMs.toLong()
        settingsUI!!.lightFromTimeSpinner.value = calendar.time

        calendar.timeInMillis = settings.timeToDarkMs.toLong()
        settingsUI!!.lightToTimeSpinner.value = calendar.time
    }

    override fun enableSearch(option: String) = null

    override fun getHelpTopic() = null

    private fun uiIsDisposed() = settingsUI == null

    private fun uiStateAsSettings(): PluginSettings {
        val settings = PluginSettings()
        settings.setConfig(
            settingsUI!!.lightFromTimeSpinner.value as Date,
            settingsUI!!.lightToTimeSpinner.value as Date
        )
        return settings
    }
}
