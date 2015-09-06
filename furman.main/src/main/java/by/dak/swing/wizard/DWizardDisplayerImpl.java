/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.swing.wizard;

import nl.jj.swingx.gui.modal.JModalFrame;
import org.jdesktop.application.Application;
import org.netbeans.api.wizard.displayer.WizardDisplayerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author admin
 */
public class DWizardDisplayerImpl extends WizardDisplayerImpl
{
    public static final String KEY_NAME = "DWizardDisplayerImpl.name";

    public DWizardDisplayerImpl()
    {
        super();
        setShowInFrame(true);
    }

    @Override
    protected JModalFrame createFrame()
    {
        JModalFrame frame = super.createFrame();
        initWindow(frame);
        return frame;
    }

    @Override
    protected JDialog createDialog()
    {
        final JDialog dialog = super.createDialog();

        initWindow(dialog);

        return dialog;
    }

    private void initWindow(final Window window)
    {
        String name = (String) getSettings().get(KEY_NAME);
        final String fileName = name + ".xml";
        window.setName(name);
        window.addHierarchyListener(new HierarchyListener()
        {

            public void hierarchyChanged(HierarchyEvent e)
            {
                if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0)
                {
                    if (e.getSource() instanceof Window)
                    {
                        Window secondaryWindow = (Window) e.getSource();
                        if (!secondaryWindow.isShowing())
                        {
                            saveSession(window, fileName);
                        }
                        else
                        {
                            restoreSession(window, fileName);
                        }
                    }
                }
            }
        });
    }

    private void saveSession(Window window, String fileName)
    {
        try
        {
            Application.getInstance().getContext().getSessionStorage().save(window, fileName);
        }
        catch (IOException ex)
        {
            Logger.getLogger(DWizardDisplayerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void restoreSession(Window window, String fileName)
    {
        try
        {
            Application.getInstance().getContext().getSessionStorage().restore(window, fileName);
        }
        catch (IOException ex)
        {
            Logger.getLogger(DWizardDisplayerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
