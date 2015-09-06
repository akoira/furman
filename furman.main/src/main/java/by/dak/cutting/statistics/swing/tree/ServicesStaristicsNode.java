package by.dak.cutting.statistics.swing.tree;

import by.dak.cutting.statistics.StatisticFilter;
import by.dak.persistence.entities.predefined.ServiceType;

/**
 * User: akoyro
 * Date: 18.05.2010
 * Time: 17:18:25
 */
public class ServicesStaristicsNode extends AStatisticsNode
{
    protected ServicesStaristicsNode(StatisticFilter filter)
    {
        super(filter);
        setUserObject(getResourceMap().getString("ServicesStaristicsNode.name"));
    }

    @Override
    protected void initChildren()
    {
        ServiceType[] serviceTypes = ServiceType.values();
        if (getFilter().getServiceType() != null)
        {
            serviceTypes = new ServiceType[]{getFilter().getServiceType()};
        }

        for (ServiceType serviceType : serviceTypes)
        {
            ServiceTypeNode serviceTypeNode = new ServiceTypeNode(getFilter(), serviceType);
            add(serviceTypeNode);
        }
    }
}
