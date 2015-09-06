/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui;

import javax.swing.*;

/**
 * @author Peca
 */
public class MyFrame extends javax.swing.JPanel
{
    JDialog dialog = null;

    public void setTitle(String s)
    {
        if (dialog != null) dialog.setTitle(s);
    }

    public void showDialog()
    {
        dialog = new JDialog();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.add(this);
        dialog.pack();
        dialog.setVisible(true);
    }

    public void showNormal()
    {
        dialog = new JDialog();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(false);
        dialog.add(this);
        dialog.pack();
        dialog.setVisible(true);
    }
}
