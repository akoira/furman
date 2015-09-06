package by.dak.cutting.doors.panels;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.doors.*;
import by.dak.cutting.doors.profile.draw.HProfileJoin;
import by.dak.cutting.doors.profile.draw.VProfileJoin;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys;
import org.jhotdraw.draw.AbstractAttributedFigure;
import org.jhotdraw.draw.Figure;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 11.08.2009
 * Time: 10:48:04
 * To change this template use File | Settings | File Templates.
 */
public class DoorEditTabbedPanel extends JTabbedPane
{
    private ViewDoorsPanel viewDoorsPanel;
    private Doors doors;
    private List<DoorDrawing> drawings = new ArrayList<DoorDrawing>();

    public DoorEditTabbedPanel(Doors doors)
    {
        setDoors(doors);
    }

    private void changeTabbed(ChangeEvent e)
    {
        int index = this.getSelectedIndex();
        if (index == 0)
        {
            viewDoorsPanel.setDrawings(drawings);
            this.setComponentAt(index, viewDoorsPanel);
            drawings = viewDoorsPanel.getDrawings();
            for (DoorDrawing doorDrawing : drawings)
            {
                for (Figure figure : doorDrawing.getChildren())
                {
                    figure.set(AttributeKeys.MOVEABLE, true);
                    if (figure instanceof HProfileJoin || figure instanceof VProfileJoin)
                    {
                        ((AbstractAttributedFigure) figure).setSelectable(true);
                    }
                }
            }
        }
        else if (index <= doors.getDoors().size() && index > 0)
        {
            EditDoorPanel editDoorPanel = null;
            if (this.getComponentAt(index) instanceof EditDoorPanel)
            {
                editDoorPanel = (EditDoorPanel) this.getComponentAt(index);
                Door door = moveDoor(index - 1);
                editDoorPanel.setDoor(false, door);
//                editDoorPanel.setDrawing(door.getDoorDrawing());
            }
            else
            {
                Door door = moveDoor(index - 1);
                editDoorPanel = new EditDoorPanel(false, door);
//                editDoorPanel.setDrawing(door.getDoorDrawing());
            }
            this.setComponentAt(index, editDoorPanel);
//            for (Figure figure : doors.getDoors().get(index - 1).getDoorDrawing().getChildren())
//            {
//                figure.setAttribute(AttributeKeys.MOVEABLE, true);
//                if (figure instanceof HProfileJoin || figure instanceof VProfileJoin)
//                {
//                    ((AbstractAttributedFigure) figure).setSelectable(true);
//                }
//            }
        }
    }

    public List<DoorDrawing> getDoorDrawings()
    {
        for (int i = 0; i < drawings.size(); i++)
        {
            moveDoor(i);
        }
        return drawings;
    }

    public Doors getDoors()
    {
        getDoorDrawings();
        return doors;
    }

    public void setDoors(Doors doors)
    {
        this.doors = doors;

        viewDoorsPanel = new ViewDoorsPanel();
        viewDoorsPanel.setDoors(doors);

        this.removeAll();

        drawings = viewDoorsPanel.getDrawings();
        this.addTab(CuttingApp.getApplication().getContext().getResourceMap(DoorEditTabbedPanel.class).getString("All_Doors"), viewDoorsPanel);

        int numberOfDoor = 1;
        for (Door door : doors.getDoors())
        {
            this.addTab(CuttingApp.getApplication().getContext().getResourceMap(DoorEditTabbedPanel.class).getString("Door") + " " + numberOfDoor, new JPanel());
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

    private Door moveDoor(int index)
    {
        if (viewDoorsPanel.getDoorsDrawing().isDrowNow())
        {
            viewDoorsPanel.getDoorsDrawing().setDrowNow(false);
            viewDoorsPanel.getDoorsDrawing().splitChilds();
        }
        DoorImpl door = ((DoorImpl) doors.getDoors().get(index));
        DoorDrawing drawing = drawings.get(index);
        drawing.setDrawlineHeight(true);
        drawing.setStart(new Point2D.Double(0, 0));
//        door.setDoorDrawing(drawing);
        return door;
    }
}
