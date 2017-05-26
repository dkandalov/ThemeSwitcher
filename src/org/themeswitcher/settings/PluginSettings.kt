package org.themeswitcher.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import java.time.LocalTime
import java.time.temporal.ChronoField

@State(name = "ThemeSwitcher", storages = arrayOf(Storage("theme.switcher.settings.xml")))
data class PluginSettings(
    // Note that LocalTime doesn't have default constructor and, therefore, cannot be used in data class.
    private var lightTimeMs: Long = LocalTime.of(8, 0).toMillis(),
    private var darkTimeMs: Long = LocalTime.of(19, 0).toMillis()
): PersistentStateComponent<PluginSettings> {

    override fun getState(): PluginSettings? = this

    override fun loadState(state: PluginSettings) = XmlSerializerUtil.copyBean(state, this)

    fun time() = Pair(lightTimeMs.millisToLocalTime(), darkTimeMs.millisToLocalTime())

    fun setTime(lightTime: LocalTime, darkTime: LocalTime) {
        this.lightTimeMs = lightTime.toMillis()
        this.darkTimeMs = darkTime.toMillis()
    }

    companion object {
        val instance: PluginSettings = ServiceManager.getService(PluginSettings::class.java)
    }
}

fun LocalTime.toMillis(): Long = this[ChronoField.MILLI_OF_DAY].toLong()

fun Long.millisToLocalTime(): LocalTime = LocalTime.ofNanoOfDay(this * 1000_000)