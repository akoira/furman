package by.dak.swing;

import javax.swing.*;
import java.awt.*;

/**
 * User: akoyro
 * Date: 10.06.2009
 * Time: 12:56:25
 */
public interface TabsContainer
{
    public void insertTab(String title, Icon icon, Component component, String tip, int index);

    public void addTab(String title, Component component);

    public void remove(Component component);

    public void setSelectedIndex(int index);

}
