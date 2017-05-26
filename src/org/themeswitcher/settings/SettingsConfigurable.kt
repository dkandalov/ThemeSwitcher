package org.themeswitcher.settings

import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.options.SearchableConfigurable
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

class SettingsConfigurable: SearchableConfigurable {
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
        !uiIsDisposed() && settingsUI!!.isNotEqual(ServiceManager.getService(PluginSettings::class.java))

    @Throws(ConfigurationException::class)
    override fun apply() {
        if (uiIsDisposed()) return
        settingsUI!!.saveState(ServiceManager.getService(PluginSettings::class.java))
    }

    override fun reset() {
        if (uiIsDisposed()) return
        settingsUI!!.loadState(ServiceManager.getService(PluginSettings::class.java))
    }

    override fun enableSearch(option: String) = null

    override fun getHelpTopic() = null

    private fun uiIsDisposed() = settingsUI == null
}
