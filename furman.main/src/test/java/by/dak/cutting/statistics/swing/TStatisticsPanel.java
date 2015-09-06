package by.dak.cutting.statistics.swing;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.SpringConfiguration;
import by.dak.test.TestUtils;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXTaskPane;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * User: akoyro
 * Date: 16.05.2010
 * Time: 17:24:58
 */
public class TStatisticsPanel
{
    public static void main(String[] args)
    {
        new SpringConfiguration();
        CuttingApp.getApplication();
        StatisticsPanel panel = new StatisticsPanel();


        TestUtils.showFrame(panel, "TStatisticsPanel");
//        testJX();

    }


    private static void testJX()
    {
        JXTaskPane cp = new JXTaskPane();
        cp.setTitle("Test");

        // JXCollapsiblePane can be used like any other container
        cp.getContentPane().setLayout(new BorderLayout());

        // the Controls panel with a textfield to filter the tree
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        controls.add(new JLabel("Search:"));
        controls.add(new JTextField(10));
        controls.add(new JButton("Refresh"));
        controls.setBorder(new TitledBorder("Filters"));
        cp.getContentPane().add("Center", controls);


        JXFrame frame = new JXFrame();
        frame.setLayout(new BorderLayout());

        // Put the "Controls" first
        frame.add("North", cp);

        // Then the tree - we assume the Controls would somehow filter the tree
        JScrollPane scroll = new JScrollPane(new JTree());
        frame.add("Center", scroll);

        // Show/hide the "Controls"
        //frame.add("South", toggle);

        frame.pack();
        frame.setVisible(true);
    }
}
