package by.dak.cutting.swing.archive;

import by.dak.test.TestUtils;

import javax.swing.*;
import java.awt.*;

/**
 * User: akoyro
 * Date: 20.08.2009
 * Time: 21:46:35
 */
public class TOrderStatusEditor
{
    public static void main(String[] args)
    {
        JPanel panel = new JPanel(new BorderLayout());
        JTable jTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(jTable);
        panel.add(scrollPane);
        TestUtils.showFrame(panel, "Test");
    }
}
