package by.dak.cutting.doors.wizard;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.doors.Doors;
import by.dak.cutting.doors.panels.DModDoorsDetailPanel;
import by.dak.cutting.doors.panels.DModDoorsInfoPanel;
import by.dak.cutting.doors.panels.DoorCellEditPanel;
import by.dak.cutting.doors.panels.DoorEditPanel;
import by.dak.swing.wizard.DWizardPanelProvider;

import javax.swing.*;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 19.08.2009
 * Time: 11:14:09
 * To change this template use File | Settings | File Templates.
 */
public class DoorsWizardPanelProvider extends DWizardPanelProvider<DoorsController.Step>
{
    private DModDoorsInfoPanel doorsInfoPanel;

    private DModDoorsDetailPanel doorsDetailPanel;

    private DoorEditPanel doorEditPanel;

    private DoorCellEditPanel doorCellEditPanel;

    private Doors doors;

    private boolean isCancel = false;

    protected DoorsWizardPanelProvider(String title, String[] steps, String[] descriptions, Doors doors)
    {
        super(title, steps, descriptions);
        this.doors = doors;
    }

    public static DoorsWizardPanelProvider createInstance(Doors doors)
    {
        return new DoorsWizardPanelProvider(CuttingApp.getApplication().getContext().getResourceMap(DoorsWizardPanelProvider.class).getString("title"),
                new String[]{
                        DoorsController.Step.DOOR_INFO.name(),
                        DoorsController.Step.DOOR_DETAILS.name(),
                        DoorsController.Step.DOOR_DESIGN.name(),
                        DoorsController.Step.DOOR_DETAILED.name()
                },
                new String[]{
                        CuttingApp.getApplication().getContext().getResourceMap(DoorsWizardPanelProvider.class).getString(DoorsController.Step.DOOR_INFO.name()),
                        CuttingApp.getApplication().getContext().getResourceMap(DoorsWizardPanelProvider.class).getString(DoorsController.Step.DOOR_DETAILS.name()),
                        CuttingApp.getApplication().getContext().getResourceMap(DoorsWizardPanelProvider.class).getString(DoorsController.Step.DOOR_DESIGN.name()),
                        CuttingApp.getApplication().getContext().getResourceMap(DoorsWizardPanelProvider.class).getString(DoorsController.Step.DOOR_DETAILED.name())
                },
                doors);
    }

    @Override
    public JPanel getComponentBy(DoorsController.Step step)
    {
        switch (step)
        {
            case DOOR_INFO:
                return getDoorsInfoPanel();
            case DOOR_DETAILS:
                return getDoorsDetailPanel();
            case DOOR_DESIGN:
                return getDoorEditPanel();
            case DOOR_DETAILED:
                return getDoorCellEditPanel();
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public DoorsController.Step valueOf(String id)
    {
        return DoorsController.Step.valueOf(id);
    }

    public DModDoorsInfoPanel getDoorsInfoPanel()
    {
        if (doorsInfoPanel == null)
        {
            doorsInfoPanel = new DModDoorsInfoPanel(doors);
        }
        return doorsInfoPanel;
    }

    public DModDoorsDetailPanel getDoorsDetailPanel()
    {
        if (doorsDetailPanel == null)
        {
            doorsDetailPanel = new DModDoorsDetailPanel(doorsInfoPanel.getDoorsInfoPanel().getDoors());
        }
        return doorsDetailPanel;
    }

    public DoorEditPanel getDoorEditPanel()
    {
        if (doorEditPanel == null)
        {
            doorEditPanel = new DoorEditPanel(doorsDetailPanel.getDoorsDetailPanel().getDoors());
        }
        return doorEditPanel;
    }

    public DoorCellEditPanel getDoorCellEditPanel()
    {
        if (doorCellEditPanel == null)
        {
            doorCellEditPanel = new DoorCellEditPanel(doorEditPanel.getDoorEditTabbedPanel().getDoors());
        }
        return doorCellEditPanel;
    }

    @Override
    public boolean cancel(Map map)
    {
        isCancel = true;
        return super.cancel(map);
    }

    public boolean isCancel()
    {
        return isCancel;
    }
}