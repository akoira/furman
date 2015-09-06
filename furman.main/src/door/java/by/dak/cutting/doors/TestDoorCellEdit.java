package by.dak.cutting.doors;

import by.dak.cutting.doors.panels.DoorCellEditTabbedPanel;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 14.08.2009
 * Time: 11:13:16
 * To change this template use File | Settings | File Templates.
 */
public class TestDoorCellEdit extends JFrame
{
    private DoorCellEditTabbedPanel jTabbedPane1;

    public TestDoorCellEdit(Doors doors)
    {
        jTabbedPane1 = new DoorCellEditTabbedPanel(doors);
        initComponents();
    }

    private void initComponents()
    {
        setTitle("Create Cell");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                                .addContainerGap())
        );
        pack();
    }
}