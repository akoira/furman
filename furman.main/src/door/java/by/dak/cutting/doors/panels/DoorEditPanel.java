package by.dak.cutting.doors.panels;

import by.dak.cutting.doors.Doors;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 20.08.2009
 * Time: 9:56:59
 * To change this template use File | Settings | File Templates.
 */
public class DoorEditPanel extends JPanel
{
    private DoorEditTabbedPanel doorEditTabbedPanel;

    public DoorEditPanel(Doors doors)
    {
        doorEditTabbedPanel = new DoorEditTabbedPanel(doors);

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
                        .addComponent(doorEditTabbedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(doorEditTabbedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                        .addContainerGap())
        );
    }

    public DoorEditTabbedPanel getDoorEditTabbedPanel()
    {
        return doorEditTabbedPanel;
    }
}
