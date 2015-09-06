package by.dak.cutting.doors.panels;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.doors.Door;
import by.dak.cutting.doors.Doors;
import by.dak.cutting.doors.EditDoorPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 14.08.2009
 * Time: 11:07:05
 * To change this template use File | Settings | File Templates.
 */
public class DoorCellEditTabbedPanel extends JTabbedPane
{
    private Doors doors;

    public DoorCellEditTabbedPanel(Doors doors)
    {
        setDoors(doors);
    }

    private void changeTabbed(ChangeEvent e)
    {
        int index = this.getSelectedIndex();
        if (index > 0 && index < doors.getDoors().size())
        {
            EditDoorPanel editDoorPanel;
            if (this.getComponentAt(index) instanceof EditDoorPanel || this.getComponentAt(index) != null)
            {
                editDoorPanel = (EditDoorPanel) this.getComponentAt(index);
            }
            else
            {
                editDoorPanel = new EditDoorPanel(true, doors.getDoors().get(index));
            }

//            editDoorPanel.setDrawing(doors.getDoors().get(index).getDoorDrawing().getCellDrawing());
            this.setComponentAt(index, editDoorPanel);
        }
    }

    public void setDoors(Doors doors)
    {
        this.doors = doors;

        this.removeAll();
        int numberOfDoor = 1;
        for (Door door : doors.getDoors())
        {
            EditDoorPanel editDoorPanel = new EditDoorPanel(true, door);
//            editDoorPanel.setDrawing(door.getDoorDrawing().getCellDrawing());
            this.addTab(CuttingApp.getApplication().getContext().getResourceMap(DoorEditTabbedPanel.class).getString("Door") + " " + numberOfDoor, editDoorPanel);
            numberOfDoor++;
        }
        this.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                changeTabbed(e);
            }
        });
    }

    public Doors getDoors()
    {
        for (Door door : doors.getDoors())
        {
//            for (Figure figure : door.getDoorDrawing().getDoorElementsChildren())
//            {
//                figure.setAttribute(AttributeKeys.MOVEABLE, true);
//            }
        }
        return doors;
    }
}


