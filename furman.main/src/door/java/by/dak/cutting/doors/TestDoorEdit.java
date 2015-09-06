package by.dak.cutting.doors;

import by.dak.cutting.doors.panels.DoorEditTabbedPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 07.08.2009
 * Time: 17:06:53
 * To change this template use File | Settings | File Templates.
 */
public class TestDoorEdit extends JFrame
{
    private DoorEditTabbedPanel jTabbedPane1;
    private Doors doors;

    public TestDoorEdit(Doors doors)
    {
        this.doors = doors;
        initComponents();
    }

    private void initComponents()
    {
        jTabbedPane1 = new DoorEditTabbedPanel(doors);
        setTitle("Draw Door");

        JButton button = new JButton("Next");

        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                actionButton(e);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                                .addComponent(button)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                                .addComponent(button)
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

                TestDoorEdit doorEdit = new TestDoorEdit(doors);
                doorEdit.addWindowListener(new java.awt.event.WindowAdapter()
                {
                    public void windowClosing(java.awt.event.WindowEvent e)
                    {
                        java.lang.System.exit(0);
                    }
                });
                doorEdit.setVisible(true);
            }
        });
    }


    public void actionButton(ActionEvent e)
    {
        TestDoorCellEdit testDoorCellEdit = new TestDoorCellEdit(jTabbedPane1.getDoors());
        testDoorCellEdit.setVisible(true);
    }
}
