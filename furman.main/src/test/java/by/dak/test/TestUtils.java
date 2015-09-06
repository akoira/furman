package by.dak.test;

import by.dak.common.swing.NeedInit;
import by.dak.cutting.swing.AListTab;
import by.dak.swing.wizard.DWizardController;
import by.dak.swing.wizard.WizardDisplayerHelper;
import org.apache.commons.beanutils.PropertyUtils;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.logging.Level;

/**
 * User: akoyro
 * Date: 14.05.2009
 * Time: 12:48:55
 */
public class TestUtils
{

    public static class RunContext
    {
        JPanel mainPanel;
        String title;
    }

    public static class TestApplication extends SingleFrameApplication
    {
        JPanel mainPanel;
        String title;

        public TestApplication()
        {
        }

        @Override
        protected void startup()
        {
            JFrame frame = new JFrame();
            setMainFrame(frame);


            if (mainPanel instanceof AListTab)
            {
                ((AListTab) mainPanel).init();
            }
            frame.setContentPane(mainPanel);
            frame.setTitle(title);
            show(getMainFrame());
        }

        @Override
        protected void initialize(String[] args)
        {
            RunContext runContext = (RunContext) System.getProperties().get("RunContext");
            mainPanel = runContext.mainPanel;
            title = runContext.title;
            initPanel();
        }

        private void initPanel()
        {
            if (mainPanel instanceof NeedInit)
            {
                initProperties();
                ((NeedInit) mainPanel).init();
            }
        }

        private void initProperties()
        {
            try
            {
                PropertyDescriptor d = PropertyUtils.getPropertyDescriptor(mainPanel, "resourceMap");
                if (d != null && d.getWriteMethod() != null)
                {
                    PropertyUtils.setProperty(mainPanel, "resourceMap", Application.getInstance().getContext().getResourceMap(mainPanel.getClass()));
                }
            }
            catch (Throwable e)
            {
                JXErrorPane errorPane = new JXErrorPane();
                errorPane.setErrorInfo(new ErrorInfo(e.getMessage(), e.getMessage(), e.getMessage(), e.getMessage(), e, Level.SEVERE, Collections.<String, String>emptyMap()));
            }
        }
    }

    public static void showFrame(final JPanel mainPanel, final String title)
    {
        RunContext runContext = new RunContext();
        runContext.mainPanel = mainPanel;
        runContext.title = title;
        System.getProperties().put("RunContext", runContext);
        SingleFrameApplication.launch(TestApplication.class, null);

    }

    public static void showFrameWithButtonAction(Action action)
    {
        JPanel jPanel = new JPanel(new BorderLayout());
        JButton button = new JButton("show");
        button.addActionListener(action);
        jPanel.add(button, BorderLayout.CENTER);
        showFrame(jPanel, "Test");
    }


    public static Action createWizardAction(final DWizardController wizardController)
    {
        return new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                WizardDisplayerHelper helper = new WizardDisplayerHelper(wizardController.getProvider(), wizardController.getGenericWizardObserver());
                helper.showWizard();
            }
        };
    }


}
