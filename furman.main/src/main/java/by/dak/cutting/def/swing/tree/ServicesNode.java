package by.dak.cutting.def.swing.tree;

import by.dak.cutting.swing.store.helpers.ListUpdaters;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Service;
import by.dak.swing.table.ListUpdater;
import by.dak.swing.table.ListUpdaterProvider;
import by.dak.swing.tree.ATreeNode;
import by.dak.utils.convert.EntityToStringConverter;
import by.dak.utils.convert.StringValue;

import java.util.List;

/**
 * User: akoyro
 * Date: 01.12.2009
 * Time: 23:52:23
 */
@StringValue(converterClass = ServicesNode.class)
public class ServicesNode extends ATreeNode implements EntityToStringConverter, ListUpdaterProvider
{
    public ServicesNode()
    {
        setUserObject(this);
    }

    @Override
    protected void initChildren()
    {
        List<Service> services = FacadeContext.getServiceFacade().loadAllSortedBy("serviceType");

        for (Service service : services)
        {
            add(new ServiceNode(service));
        }
    }

    @Override
    public String convert(Object entity)
    {
        //todo resourceMap
        return resourceMap.getString("node.name");
    }

    @Override
    public ListUpdater getListUpdater()
    {
        return ListUpdaters.SERVICE_LIST_UPDATER;
    }
}
