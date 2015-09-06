package by.dak.cutting.swing.store.helpers;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.store.tabs.AStoreElementTab;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Furniture;
import by.dak.swing.ActionsPanel;
import by.dak.swing.WindowCallback;
import org.jdesktop.application.AbstractBean;
import org.jdesktop.application.Application;

import javax.swing.*;

/**
 * User: akoyro
 * Date: 11.11.2010
 * Time: 15:27:59
 */
public class ReplaceHelper extends AbstractBean
{
    private WindowCallback windowCallback = new WindowCallback();
    private AStoreElementTab<Furniture> valueTab = new AStoreElementTab<Furniture>();
    private ActionsPanel<AStoreElementTab> actionsPanel = new ActionsPanel<AStoreElementTab>();

    private Furniture source;
    private JComponent relatedComponent;


    public void init()
    {

        Furniture furniture = new Furniture();
        furniture.setFurnitureType(source.getFurnitureType());
        furniture.setFurnitureCode(source.getFurnitureCode());

        valueTab.setVisibleProperties(new String[]{Furniture.PROPERTY_furnitureType, Furniture.PROPERTY_furnitureCode});
        valueTab.setValueClass(Furniture.class);
        valueTab.init();
        valueTab.setValue(furniture);

        actionsPanel.setName("furnitureReplace");
        actionsPanel.setContentComponent(valueTab);
        actionsPanel.setActions("replace", "cancel");
        actionsPanel.setSourceActionMap(Application.getInstance().getContext().getActionMap(ReplaceHelper.class, this));
        actionsPanel.init();

    }

    public void show()
    {
        DialogShowers.showBy(actionsPanel, getRelatedComponent(), windowCallback, true);
    }


    @org.jdesktop.application.Action
    public void replace()
    {
        FacadeContext.getFurnitureFacade().replace(getSource(), valueTab.getValue().getFurnitureType(), valueTab.getValue().getFurnitureCode());
        firePropertyChange(AEntityNEDActions.PROPERTY_updateGui, null, getSource());
        disposeWindows();
    }

    @org.jdesktop.application.Action
    public void cancel()
    {
        disposeWindows();
    }

    private void disposeWindows()
    {
        if (windowCallback.getWindow() != null)
        {
            windowCallback.dispose();
        }
    }

    public Furniture getSource()
    {
        return source;
    }

    public void setSource(Furniture source)
    {
        this.source = source;
    }

    public JComponent getRelatedComponent()
    {
        return relatedComponent;
    }

    public void setRelatedComponent(JComponent relatedComponent)
    {
        this.relatedComponent = relatedComponent;
    }
}
