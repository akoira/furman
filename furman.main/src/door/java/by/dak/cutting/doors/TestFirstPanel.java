package by.dak.cutting.doors;

import by.dak.cutting.doors.panels.DoorsInfoPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 18.08.2009
 * Time: 14:25:56
 * To change this template use File | Settings | File Templates.
 */
public class TestFirstPanel extends JFrame
{
    private DoorsInfoPanel panel;

    public TestFirstPanel()
    {
        initComponents();
    }

    private void initComponents()
    {
        panel = new DoorsInfoPanel(null);
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
                DoorsImpl doors = new DoorsImpl();
                List<Door> list = new ArrayList<Door>();

                DoorImpl door = new DoorImpl();
                door.setHeight(new Long(400));
                door.setWidth(new Long(300));
                door.setDoorColor(null);
                door.setDoorMechType(null);

                list.add(door);

                door = new DoorImpl();
                door.setHeight(new Long(400));
                door.setWidth(new Long(200));
                door.setDoorColor(null);
                door.setDoorMechType(null);

                list.add(door);

                door = new DoorImpl();
                door.setHeight(new Long(400));
                door.setWidth(new Long(600));
                door.setDoorColor(null);
                door.setDoorMechType(null);

                list.add(door);

                doors.setDoors(list);

                TestFirstPanel firstPanel = new TestFirstPanel();
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
