package by.dak.cutting.doors.panels;

import by.dak.cutting.doors.Doors;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 20.08.2009
 * Time: 9:54:04
 * To change this template use File | Settings | File Templates.
 */
public class DoorCellEditPanel extends JPanel
{
    private DoorCellEditTabbedPanel doorCellEditTabbedPanel;

    public DoorCellEditPanel(Doors doors)
    {
        doorCellEditTabbedPanel = new DoorCellEditTabbedPanel(doors);

        initComponents();
    }


    private void initComponents()
    {
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(doorCellEditTabbedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(doorCellEditTabbedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                .addContainerGap())
        );
    }

    public DoorCellEditTabbedPanel getDoorCellEditTabbedPanel()
    {
        return doorCellEditTabbedPanel;
    }
}
