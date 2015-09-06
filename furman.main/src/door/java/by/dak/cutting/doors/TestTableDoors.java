package by.dak.cutting.doors;

import by.dak.cutting.doors.panels.DoorsDetailPanel;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 18.08.2009
 * Time: 15:01:04
 * To change this template use File | Settings | File Templates.
 */
public class TestTableDoors extends JFrame
{
    private DoorsDetailPanel panel;

    public TestTableDoors(Long countDoors, Long heightDoors, Long widthDoors)
    {
//        panel = new DoorsDetailsPanel(countDoors, heightDoors, widthDoors);
        initComponents();
    }

    private void initComponents()
    {
        setTitle("First panel");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panel)
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panel)
                        .addContainerGap())
        );
        pack();
    }

    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                Long countDoors = new Long(3);
                Long heightDoors = new Long(400);
                Long widthDoors = new Long(900);

                TestTableDoors firstPanel = new TestTableDoors(countDoors, heightDoors, widthDoors);
                firstPanel.addWindowListener(new java.awt.event.WindowAdapter()
                {
                    public void windowClosing(java.awt.event.WindowEvent e)
                    {
                        java.lang.System.exit(0);
                    }
                });
                firstPanel.setVisible(true);
            }
        });
    }

}