package org.themeswitcher.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import java.util.*

@State(name = "SaveTimesSettings", storages = arrayOf(Storage(file = "/savetimes_settings.xml")))
data class PluginSettings(
    var timeToLightMs: String? = null,
    var timeToDarkMs: String? = null
): PersistentStateComponent<PluginSettings> {

    override fun getState(): PluginSettings? = this

    override fun loadState(state: PluginSettings) = XmlSerializerUtil.copyBean(state, this)

    fun setConfig(timeToLight: Date, timeToDark: Date) {
        this.timeToLightMs = java.lang.Long.toString(timeToLight.time)
        this.timeToDarkMs = java.lang.Long.toString(timeToDark.time)
    }

    companion object {
        val instance = ServiceManager.getService(PluginSettings::class.java)
    }
}
