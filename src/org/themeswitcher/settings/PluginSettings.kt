package org.themeswitcher.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import java.util.*

@State(name = "ThemeSwitcher", storages = arrayOf(Storage("theme.switcher.settings.xml")))
data class PluginSettings(
    var timeToLightMs: String = "08:00",
    var timeToDarkMs: String = "19:00"
): PersistentStateComponent<PluginSettings> {

    override fun getState(): PluginSettings? = this

    override fun loadState(state: PluginSettings) = XmlSerializerUtil.copyBean(state, this)

    fun setConfig(timeToLight: Date, timeToDark: Date) {
        timeToLightMs = timeToLight.time.toString()
        timeToDarkMs = timeToDark.time.toString()
    }

    companion object {
        val instance: PluginSettings = ServiceManager.getService(PluginSettings::class.java)
    }
}
