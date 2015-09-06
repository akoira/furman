package by.dak.cutting;

import nl.jj.swingx.gui.JSplashWindow;

import javax.swing.*;

/**
 * User: akoyro
 * Date: 31.03.2009
 * Time: 16:18:12
 */
public class TestSplashScreen
{
    public static void main(String[] args)
    {
        ImageIcon splashImage = new ImageIcon(TestSplashScreen.class.getResource("splash.jpg"));
        JSplashWindow splashWindow = new JSplashWindow(splashImage);
        splashWindow.setVisible(true);
    }
}
