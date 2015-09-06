package by.dak.cutting.doors.mech.wizard;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.doors.mech.DoorColor;
import by.dak.cutting.doors.mech.DoorComponent;
import by.dak.cutting.doors.mech.DoorMechDef;
import by.dak.cutting.doors.mech.DoorMechType;
import by.dak.cutting.doors.mech.panels.DModMechPanel;
import by.dak.cutting.doors.mech.panels.NameDoorMechDefPanel;
import by.dak.cutting.doors.mech.panels.TablePanel;
import by.dak.swing.wizard.DWizardPanelProvider;

import javax.swing.*;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 21.08.2009
 * Time: 13:10:06
 * To change this template use File | Settings | File Templates.
 */
public class DoorMechDefWizardPanelProvider extends DWizardPanelProvider<DoorMechDefController.Step>
{
    private DModMechPanel<NameDoorMechDefPanel> nameMechPanel;

    private DModMechPanel<TablePanel> mechTypePanel;

    private DModMechPanel<TablePanel> doorColorPanel;

    private DModMechPanel<TablePanel> doorComponentPanel;

    private DoorMechDef doorMechDef;

    private boolean isCancel = false;

    protected DoorMechDefWizardPanelProvider(String title, String[] steps, String[] descriptions, DoorMechDef doorMechDef)
    {
        super(title, steps, descriptions);
        this.doorMechDef = doorMechDef;
    }

    public static DoorMechDefWizardPanelProvider createInstance(DoorMechDef doorMechDef)
    {
        return new DoorMechDefWizardPanelProvider(CuttingApp.getApplication().getContext().getResourceMap(DoorMechDefWizardPanelProvider.class).getString("title"),
                new String[]{
                        DoorMechDefController.Step.DOOR_MECH_NAME.name(),
                        DoorMechDefController.Step.DOOR_MECH_TYPE.name(),
                        DoorMechDefController.Step.DOOR_COLOR.name(),
                        DoorMechDefController.Step.DOOR_COMPONENT.name()
                },
                new String[]{
                        CuttingApp.getApplication().getContext().getResourceMap(DoorMechDefWizardPanelProvider.class).getString(DoorMechDefController.Step.DOOR_MECH_NAME.name()),
                        CuttingApp.getApplication().getContext().getResourceMap(DoorMechDefWizardPanelProvider.class).getString(DoorMechDefController.Step.DOOR_MECH_TYPE.name()),
                        CuttingApp.getApplication().getContext().getResourceMap(DoorMechDefWizardPanelProvider.class).getString(DoorMechDefController.Step.DOOR_COLOR.name()),
                        CuttingApp.getApplication().getContext().getResourceMap(DoorMechDefWizardPanelProvider.class).getString(DoorMechDefController.Step.DOOR_COMPONENT.name()),
                },
                doorMechDef);
    }

    @Override
    public JPanel getComponentBy(DoorMechDefController.Step step)
    {
        switch (step)
        {
            case DOOR_MECH_NAME:
                return getNameMechPanel();
            case DOOR_MECH_TYPE:
                return getMechTypePanel();
            case DOOR_COLOR:
                return getDoorColorPanel();
            case DOOR_COMPONENT:
                return getDoorComponentPanel();
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public DoorMechDefController.Step valueOf(String id)
    {
        return DoorMechDefController.Step.valueOf(id);
    }

    public DModMechPanel<NameDoorMechDefPanel> getNameMechPanel()
    {
        if (nameMechPanel == null)
        {
            nameMechPanel = new DModMechPanel<NameDoorMechDefPanel>(new NameDoorMechDefPanel(doorMechDef));
        }
        return nameMechPanel;
    }

    public DModMechPanel<TablePanel> getMechTypePanel()
    {
        if (mechTypePanel == null)
        {
            if (doorMechDef == null)
            {
                mechTypePanel = new DModMechPanel<TablePanel>(new TablePanel<DoorMechType>(null, TablePanel.Step.TYPE));
            }
            else
            {
                mechTypePanel = new DModMechPanel<TablePanel>(new TablePanel<DoorMechType>(doorMechDef.getDoorMechType(), TablePanel.Step.TYPE));
            }
        }
        return mechTypePanel;
    }

    public DModMechPanel<TablePanel> getDoorColorPanel()
    {
        if (doorColorPanel == null)
        {
            if (doorMechDef == null)
            {
                doorColorPanel = new DModMechPanel<TablePanel>(new TablePanel<DoorColor>(null, TablePanel.Step.COLOR));
            }
            else
            {
                doorColorPanel = new DModMechPanel<TablePanel>(new TablePanel<DoorColor>(doorMechDef.getDoorColor(), TablePanel.Step.COLOR));
            }
        }
        return doorColorPanel;
    }

    public DModMechPanel<TablePanel> getDoorComponentPanel()
    {
        if (doorComponentPanel == null)
        {
            if (doorMechDef == null)
            {
                doorComponentPanel = new DModMechPanel<TablePanel>(new TablePanel<DoorComponent>(null, TablePanel.Step.COMPONENT));
            }
            else
            {
                doorComponentPanel = new DModMechPanel<TablePanel>(new TablePanel<DoorComponent>(doorMechDef.getDoorComponent(), TablePanel.Step.COMPONENT));
            }
        }
        return doorComponentPanel;
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
