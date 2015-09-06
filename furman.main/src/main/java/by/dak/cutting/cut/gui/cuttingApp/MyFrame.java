/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui.cuttingApp;

import javax.swing.*;

/**
 * @author Peca
 */
public class MyFrame extends javax.swing.JPanel
{
    private JDialog dialog = null;
    private String title;
    private int defaultCloseOperation = JDialog.DISPOSE_ON_CLOSE;

    public void setTitle(String s)
    {
        title = s;
        if (dialog != null) dialog.setTitle(title);
    }

    public void setDefaultCloseOperation(int operation)
    {
        this.defaultCloseOperation = operation;
    }

    public void showDialog()
    {
        dialog = new JDialog(null, JDialog.ModalityType.TOOLKIT_MODAL);
        dialog.setDefaultCloseOperation(this.defaultCloseOperation);
        dialog.setResizable(true);
        dialog.setModal(true);
        dialog.add(this);
        dialog.pack();
        dialog.setTitle(title);
        dialog.setVisible(true);


    }

    public void close()
    {
        if (dialog != null) dialog.setVisible(false);
    }
}










