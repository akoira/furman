package by.dak.cutting.swing.store.modules;

import by.dak.cutting.swing.DModPanel;
import by.dak.cutting.swing.store.tabs.ServiceTab;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Service;
import org.jdesktop.application.Application;

/**
 * User: akoyro
 * Date: 17.06.2009
 * Time: 16:40:06
 */
public class ServicePanel extends DModPanel<Service>
{
    private ServiceTab serviceTab = new ServiceTab();

    public ServicePanel()
    {
        setResourceMap(Application.getInstance().getContext().getResourceMap(
                ServicePanel.class));
        addTabs();
    }

    @Override
    protected void addTabs()
    {
        addTab(getResourceMap().getString("ServicePanel.serviceTab.title"), serviceTab);
    }

    @Override
    public void save()
    {
        if (validateGUI())
        {
            FacadeContext.getServiceFacade().save(getValue());
        }
    }

}
