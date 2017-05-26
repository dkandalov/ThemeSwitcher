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
import java.time.LocalTime
import javax.swing.UIManager
import javax.swing.UnsupportedLookAndFeelException


class ThemeSwitcher {

    private val colorSchemeManager = IdeScheme()

    fun init(): ThemeSwitcher {
        val (lightTime, darkTime) = PluginSettings.instance

        val now = LocalTime.now()
        val darkTheme = now < lightTime || now > darkTime

        try {
            if (darkTheme) {
                UIManager.setLookAndFeel(DarculaLaf())
            } else {
                UIManager.setLookAndFeel(AquaLookAndFeel())
            }

            val currentScheme = if (darkTheme) DARCULA_THEME else DEFAULT_THEME
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

        private fun useDarkTheme(theme: String) = theme.toLowerCase() != "default"
    }
}
