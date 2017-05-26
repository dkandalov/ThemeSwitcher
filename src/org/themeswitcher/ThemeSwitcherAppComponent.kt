package org.themeswitcher

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.components.ServiceManager
import org.themeswitcher.settings.PluginSettings
import java.time.LocalTime
import java.util.*
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE

class ThemeSwitcherAppComponent: ApplicationComponent {

    override fun initComponent() {
        val themeSwitcher = ThemeSwitcher().init()

        val thread = Thread {
            val calendar = Calendar.getInstance(Locale.getDefault())
            val (timeToLightMs, timeToDarkMs) = ServiceManager.getService(PluginSettings::class.java)

            while (true) {
                calendar.timeInMillis = java.lang.Long.valueOf(timeToLightMs!!)!!
                val timeToLight = LocalTime.of(calendar.get(HOUR_OF_DAY), calendar.get(MINUTE))

                calendar.timeInMillis = java.lang.Long.valueOf(timeToDarkMs!!)!!
                val timeToDark = LocalTime.of(calendar.get(HOUR_OF_DAY), calendar.get(MINUTE))

                val now = LocalTime.now()
                if (timeToLight == now || timeToDark == now) {
                    ApplicationManager.getApplication().invokeLater { themeSwitcher.switchTheme() }
                    try {
                        Thread.sleep((2 * 60 * 1000).toLong())
                    } catch (ignored: InterruptedException) {
                    }

                }
            }
        }
        thread.start()
    }

    override fun getComponentName(): String = this::class.java.canonicalName

    override fun disposeComponent() {}
}