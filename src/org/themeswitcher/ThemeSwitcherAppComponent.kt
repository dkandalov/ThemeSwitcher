package org.themeswitcher

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ApplicationComponent
import org.themeswitcher.settings.PluginSettings
import java.time.LocalTime

class ThemeSwitcherAppComponent: ApplicationComponent {

    override fun initComponent() {
        val themeSwitcher = ThemeSwitcher().init()

        val thread = Thread {
            val (lightTime, darkTime) = PluginSettings.instance.time()

            while (true) {
                val now = LocalTime.now()
                if (lightTime == now || darkTime == now) {
                    ApplicationManager.getApplication().invokeLater { themeSwitcher.switchTheme() }
                    try {
                        Thread.sleep(2 * 60 * 1000L)
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