package by.dak.cutting.swing.order;

import by.dak.cutting.afacade.swing.AFacadeModPanel;
import by.dak.cutting.agt.swing.AGTModPanel;
import by.dak.cutting.zfacade.swing.ZFacadeModPanel;
import by.dak.order.swing.tree.*;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.template.swing.TemplateFacadeModPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * User: akoyro
 * Date: 16.07.2010
 * Time: 19:51:40
 */
public class OrderItemTabFactory
{
    public final static OrderItemTabFactory ORDER_ITEM_TAB_FACTORY = new OrderItemTabFactory();

    /**
     * @param treeNode
     * @return может быть null
     */
    public JComponent getComponentBy(AOrderNode treeNode)
    {
        if (treeNode instanceof OrderItemNode)
        {
            OrderDetailsTab tab = new OrderDetailsTab();
            tab.setValue(((OrderItemNode) treeNode).getOrderItem());
            tab.setName(tab.getClass().getSimpleName());
            return tab;
        }
        else if (treeNode instanceof ZFacadeNode)
        {
            AFacadeModPanel tab = new ZFacadeModPanel();
            tab.setValue(treeNode.getOrder());
            tab.setName(tab.getClass().getSimpleName() + "." + OrderItemType.zfacade.name());
            return tab;
        }
        else if (treeNode instanceof AGTFacadeNode)
        {
            AFacadeModPanel tab = new AGTModPanel();
            tab.setValue(treeNode.getOrder());
            tab.setName(tab.getClass().getSimpleName() + "." + OrderItemType.agtfacade.name());
            return tab;
        }
        else if (treeNode instanceof TemplateItemNode)
        {
            OrderDetailsTab tab = new OrderDetailsTab();
            tab.setValue(((TemplateItemNode) treeNode).getOrderItem());
            tab.setName(tab.getClass().getSimpleName() + "." + ((TemplateItemNode) treeNode).getOrderItem().getName());
            return tab;
        }
        else if (treeNode instanceof TemplateFacadeNode)
        {
            AFacadeModPanel tab = new TemplateFacadeModPanel();
            tab.setValue(treeNode.getOrder());
            tab.setName(tab.getClass().getSimpleName() + "." + OrderItemType.templateFacade.name());
            return tab;
        }
        else
        {
            return null;
        }
    }


    @Deprecated
    public List<Component> getOrderItemTabsBy(Order order)
    {
        OrderItemType[] types = OrderItemType.values();
        ArrayList<Component> result = new ArrayList<Component>();
        for (OrderItemType type : types)
        {
            switch (type)
            {

                case first:
                case common:
                case zfacade:
                    AFacadeModPanel tab = new ZFacadeModPanel();
                    addTabTo(order, result, type, tab);
                    break;
                case agtfacade:
                    tab = new AGTModPanel();
                    addTabTo(order, result, type, tab);
                    break;
                default:
                    throw new IllegalArgumentException();
            }

        }
        return result;
    }

    private void addTabTo(Order order, ArrayList<Component> result, OrderItemType type, AFacadeModPanel tab)
    {
        tab.setValue(order);
        tab.setName(tab.getClass().getSimpleName() + "." + type.name());
        result.add(tab);
    }
}
