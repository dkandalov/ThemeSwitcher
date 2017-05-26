package org.themeswitcher

import apple.laf.AquaLookAndFeel
import com.intellij.ide.ui.UISettings
import com.intellij.ide.ui.laf.darcula.DarculaLaf
import com.intellij.openapi.actionSystem.impl.ActionToolbarImpl
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.colors.EditorColorsScheme
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.JBColor
import org.themeswitcher.settings.PluginSettings
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.swing.UIManager
import javax.swing.UnsupportedLookAndFeelException


class ThemeSwitcher {

    private val colorSchemeManager = IdeScheme()

    fun init(): ThemeSwitcher {
        val (timeToLightMs, timeToDarkMs) = PluginSettings.instance
        val df = SimpleDateFormat("HH:mm")

        val calendar = Calendar.getInstance(Locale.getDefault())
        val nowTime = df.format(calendar.time)
        calendar.timeInMillis = java.lang.Long.valueOf(timeToLightMs)!!

        val lightTime = df.format(calendar.time)

        calendar.timeInMillis = java.lang.Long.valueOf(timeToDarkMs)!!
        val darkTime = df.format(calendar.time)

        var darkTheme = false
        try {
            darkTheme = !isTimeBetweenTwoTime(lightTime, darkTime, nowTime)
        } catch (ignored: ParseException) {
        }

        val currentScheme = if (darkTheme) DARCULA_THEME else DEFAULT_THEME

        try {
            if (darkTheme) {
                UIManager.setLookAndFeel(DarculaLaf())
            } else {
                UIManager.setLookAndFeel(AquaLookAndFeel())
            }

            JBColor.setDark(useDarkTheme(currentScheme))
            IconLoader.setUseDarkIcons(useDarkTheme(currentScheme))
        } catch (ignored: UnsupportedLookAndFeelException) {
        }

        val makeActiveScheme = if (darkTheme) DARCULA_THEME else DEFAULT_THEME

        colorSchemeManager.globalScheme = colorSchemeManager.schemeByName(makeActiveScheme)

        if (darkTheme) {
            UISettings.getInstance().fireUISettingsChanged()
            ActionToolbarImpl.updateAllToolbarsImmediately()
        }

        return this
    }

    fun switchTheme() {
        val currentScheme = colorSchemeManager.globalScheme.name

        try {
            if (currentScheme == DEFAULT_THEME) {
                UIManager.setLookAndFeel(DarculaLaf())
            } else {
                UIManager.setLookAndFeel(AquaLookAndFeel())
            }
            JBColor.setDark(useDarkTheme(currentScheme))
            IconLoader.setUseDarkIcons(useDarkTheme(currentScheme))
        } catch (ignored: UnsupportedLookAndFeelException) {
        }

        var makeActiveScheme = DEFAULT_THEME

        if (currentScheme == DEFAULT_THEME) {
            makeActiveScheme = DARCULA_THEME
        }

        colorSchemeManager.globalScheme = colorSchemeManager.schemeByName(makeActiveScheme)

        UISettings.getInstance().fireUISettingsChanged()
        ActionToolbarImpl.updateAllToolbarsImmediately()
    }


    class IdeScheme {
        var globalScheme: EditorColorsScheme
            get() = EditorColorsManager.getInstance().globalScheme
            set(value) {
                EditorColorsManager.getInstance().globalScheme = value
            }

        fun schemeByName(scheme: String): EditorColorsScheme = EditorColorsManager.getInstance().getScheme(scheme)
    }


    companion object {
        private val DARCULA_THEME = "Darcula"
        private val DEFAULT_THEME = "Default"

        private fun useDarkTheme(theme: String): Boolean {
            return theme.toLowerCase() != "default"
        }

        private fun isTimeBetweenTwoTime(initialTime: String, finalTime: String, currentTime: String): Boolean {
            val reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9])$"
            if (!initialTime.matches(reg.toRegex()) || !finalTime.matches(reg.toRegex()) || !currentTime.matches(reg.toRegex())) {
                throw IllegalArgumentException("Not a valid time, expecting HH:MM format")
            }

            //Start Time
            val inTime = SimpleDateFormat("HH:mm").parse(initialTime)
            val calendar1 = Calendar.getInstance()
            calendar1.time = inTime

            //Current Time
            val checkTime = SimpleDateFormat("HH:mm").parse(currentTime)
            val calendar3 = Calendar.getInstance()
            calendar3.time = checkTime

            //End Time
            val finTime = SimpleDateFormat("HH:mm").parse(finalTime)
            val calendar2 = Calendar.getInstance()
            calendar2.time = finTime

            if (finalTime < initialTime) {
                calendar2.add(Calendar.DATE, 1)
                calendar3.add(Calendar.DATE, 1)
            }

            var valid = false
            val actualTime = calendar3.time
            if ((actualTime.after(calendar1.time) || actualTime.compareTo(calendar1.time) == 0) && actualTime.before(calendar2.time)) {
                valid = true
            }
            return valid
        }
    }
}
