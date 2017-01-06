package org.themeswitcher;

public class ChangeThemeThread implements Runnable {
    @Override
    public void run() {
        new SwitchTheme().setTheme();
    }
}
