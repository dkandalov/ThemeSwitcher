package org.themeswitcher

import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.colors.EditorColorsScheme

class IdeScheme {
    var globalScheme: EditorColorsScheme?
        get() = EditorColorsManager.getInstance().globalScheme
        set(value) = EditorColorsManager.getInstance().setGlobalScheme(value)

    fun schemeByName(scheme: String): EditorColorsScheme = EditorColorsManager.getInstance().getScheme(scheme)
}