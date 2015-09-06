package by.dak.common.swing;

import org.jdesktop.application.ApplicationContext;

import javax.swing.*;

public interface IController<C extends JComponent> extends NeedInit
{
    ApplicationContext getApplicationContext();

    void setApplicationContext(ApplicationContext applicationContext);

    C getComponent();
}
