package org.themeswitcher.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import java.time.LocalTime

@State(name = "ThemeSwitcher", storages = arrayOf(Storage("theme.switcher.settings.xml")))
data class PluginSettings(
    var lightTime: LocalTime = LocalTime.of(8, 0),
    var darkTime: LocalTime = LocalTime.of(19, 0)
): PersistentStateComponent<PluginSettings> {

    override fun getState(): PluginSettings? = this

    override fun loadState(state: PluginSettings) = XmlSerializerUtil.copyBean(state, this)

    fun setState(lightTime: LocalTime, darkTime: LocalTime) {
        this.lightTime = lightTime
        this.darkTime = darkTime
    }

    companion object {
        val instance: PluginSettings = ServiceManager.getService(PluginSettings::class.java)
    }
}
