package by.dak.cutting;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


/*
  Обертка поверх JOptionPane
 */

public class MessageBox
{
    private static MessageBox instance;
    private static ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(MessageBox.class);

    private MessageBox()
    {
    }

    public static MessageBox getInstance()
    {
        if (instance == null)
        {
            instance = new MessageBox();
        }
        return instance;
    }

    /**
     * Shows Yes/No dialog
     *
     * @param question dialog question
     * @return <code>true</code> if Yes
     */
    public static boolean confirmYesNo(String question)
    {
        final JButton okButton = new JButton(resourceMap.getString("button.ok.text"));
        final JButton noButton = new JButton(resourceMap.getString("button.cancel.text"));

        Object[] options = {okButton, noButton};
        final JOptionPane pane = new JOptionPane();
        pane.setOptions(options);
        pane.setOptionType(JOptionPane.YES_NO_OPTION);
        pane.setMessageType(JOptionPane.QUESTION_MESSAGE);
        pane.setMessage(question);
        final JDialog dialog = pane.createDialog(getActiveWindow(), resourceMap.getString("title"));
        noButton.requestFocus();


        ActionMap amap = noButton.getActionMap();
        InputMap imap = noButton.getInputMap();
        Object tabMap = new Object();
        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), tabMap);
        amap.put(tabMap, new AbstractAction()
        {
            public void actionPerformed(ActionEvent ev)
            {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
            }
        });
        ActionMap amap1 = okButton.getActionMap();
        InputMap imap1 = okButton.getInputMap();
        Object tabMap1 = new Object();
        imap1.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), tabMap1);
        amap1.put(tabMap1, new AbstractAction()
        {
            public void actionPerformed(ActionEvent ev)
            {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
            }
        });

        okButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                pane.setValue(okButton);
                dialog.dispose();
            }
        });
        noButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                pane.setValue(noButton);
                dialog.dispose();
            }
        });
        dialog.setVisible(true);
        Object selectedValue = pane.getValue();
        return selectedValue != null && selectedValue.equals(okButton);
    }

    public static boolean confirmYesNoDeletion()
    {
        return confirmYesNo(resourceMap.getString("message"));
    }

    public static void warning(String message, String title)
    {
        JOptionPane.showMessageDialog(getActiveWindow(), message, title, JOptionPane.WARNING_MESSAGE);
    }


    public static Window getActiveWindow()
    {
        return activeWindow;
    }

    public void setActiveWindow(Window activeWindow)
    {
        MessageBox.activeWindow = activeWindow;
    }

    private static Window activeWindow;


    public final static int RESULT_YES = 0;
    public final static int RESULT_NO = 1;
    public final static int RESULT_CANCEL = 3;
}
