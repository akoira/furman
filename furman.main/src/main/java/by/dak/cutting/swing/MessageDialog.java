/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.swing;

import by.dak.cutting.CuttingApp;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;

/**
 * Wrapper class for JOptionPane
 *
 * @author Rudak Alexei
 */
public class MessageDialog
{
    public static final String NO_ROW_SELECTED = "MessageDialog.NO_ROW_SELECTED";
    public static final String IS_DELETE_RECORD = "MessageDialog.IS_DELETE_RECORD";
    private static ResourceMap resourceMap = Application.getInstance(
            CuttingApp.class).getContext().getResourceMap(MessageDialog.class);

    public static void showSimpleMessage(String msg)
    {
        JOptionPane.showConfirmDialog(null,
                (resourceMap.getString(msg) != null) ? resourceMap.getString(msg) : msg,
                resourceMap.getString("MessageDialog.warning"), JOptionPane.CLOSED_OPTION);
    }

    public static Integer showConfirmationMessage(String msg)
    {
        return JOptionPane.showConfirmDialog(null,
                (resourceMap.getString(msg) != null) ? resourceMap.getString(msg) : msg,
                resourceMap.getString("MessageDialog.warning"), JOptionPane.YES_NO_OPTION);

    }
}
