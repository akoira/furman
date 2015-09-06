package by.dak.cutting;

import by.dak.common.swing.ExceptionHandler;
import org.aspectj.lang.annotation.AfterThrowing;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 18.01.2009
 * Time: 14:26:23
 * To change this template use File | Settings | File Templates.
 */
public class DefaultExceptionHandler implements ExceptionHandler
{
    @Override
    @AfterThrowing(argNames = "e")
    public void handle(final Throwable e)
    {
        Logger.getLogger(CuttingApp.getApplication().getClass().getName()).log(Level.SEVERE, null, e);
        Runnable runnable = new Runnable()
        {
            public void run()
            {
                JOptionPane.showMessageDialog(FocusManager.getCurrentManager().getActiveWindow(), e.getLocalizedMessage(),
                        e.getLocalizedMessage(), JOptionPane.ERROR_MESSAGE);
            }
        };
        SwingUtilities.invokeLater(runnable);
    }

    @Override
    public void handle(Object o, final Throwable e)
    {
        Logger.getLogger(o.getClass().getName()).log(Level.SEVERE, null, e);
        Runnable runnable = new Runnable()
        {
            public void run()
            {
                JOptionPane.showMessageDialog(FocusManager.getCurrentManager().getActiveWindow(), e.getLocalizedMessage(),
                        e.getLocalizedMessage(), JOptionPane.ERROR_MESSAGE);
            }
        };
        SwingUtilities.invokeLater(runnable);
    }

}
