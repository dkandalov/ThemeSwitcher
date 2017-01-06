package org.themeswitcher;

import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import org.jetbrains.annotations.Nullable;

class ColorSchemeManagerImpl {

    public EditorColorsScheme getGlobalColorScheme() {
        return EditorColorsManager.getInstance().getGlobalScheme();
    }

    public EditorColorsScheme getScheme(String scheme) {
        return EditorColorsManager.getInstance().getScheme(scheme);
    }

    public void setGlobalScheme(@Nullable EditorColorsScheme var1) {
        EditorColorsManager.getInstance().setGlobalScheme(var1);
    }
}