package org.themeswitcher.settings

import com.intellij.openapi.components.PersistentStateComponent
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
        !uiIsDisposed() && stateAsSettings() != PluginSettings.instance

    override fun apply() {
        if (uiIsDisposed()) return
        saveState(PluginSettings.instance)
    }

    override fun reset() {
        if (uiIsDisposed()) return
        loadState(PluginSettings.instance)
    }

    fun loadState(settings: PluginSettings) {
        val calendar = Calendar.getInstance(Locale.getDefault())
        if (settings.timeToLightMs != null) {
            calendar.timeInMillis = settings.timeToLightMs!!.toLong()
            settingsUI!!.lightFromTimeSpinner.value = calendar.time
        }
        if (settings.timeToDarkMs != null) {
            calendar.timeInMillis = settings.timeToDarkMs!!.toLong()
            settingsUI!!.lightToTimeSpinner.value = calendar.time
        }
    }

    override fun enableSearch(option: String) = null

    override fun getHelpTopic() = null

    private fun uiIsDisposed() = settingsUI == null

    fun saveState(stateComponent: PersistentStateComponent<PluginSettings>) {
        stateComponent.loadState(stateAsSettings())
    }

    private fun stateAsSettings(): PluginSettings {
        val settings = PluginSettings()
        settings.setConfig(settingsUI!!.lightFromTimeSpinner.value as Date, settingsUI!!.lightToTimeSpinner.value as Date)
        return settings
    }
}
