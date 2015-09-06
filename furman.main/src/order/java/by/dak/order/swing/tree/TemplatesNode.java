package by.dak.order.swing.tree;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.facade.Callback;
import by.dak.cutting.swing.MessageDialog;
import by.dak.cutting.swing.store.helpers.AEntityNEDActions;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.OrderItem;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.swing.WindowCallback;
import by.dak.template.TemplateOrder;
import by.dak.template.swing.TemplateOrdersTab;
import by.dak.template.swing.TemplateOrdersUpdater;
import org.apache.commons.collections.MapUtils;
import org.jdesktop.application.Action;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: akoyro
 * Date: 16.03.11
 * Time: 12:48
 */
public class TemplatesNode extends AOrderNode
{
    private static final String[] ACTIONS = new String[]{"addTemplate"};
    private NodeBuilder nodeBuilder;


    protected TemplatesNode()
    {
        setUserObject(OrderItemType.common);
        setAllowsChildren(true);
    }

    @Override
    protected void initChildren()
    {
        Map<AOrder, TemplateOrderNode> map = new HashMap<>();
        List<OrderItem> orderItems = FacadeContext.getOrderItemFacade().findBy(getOrder(), OrderItemType.common);
        Builder builder = new Builder();
        for (OrderItem orderItem : orderItems)
        {
            builder.build(orderItem);
        }
    }

    @Override
    public String[] getActions()
    {
        return ACTIONS;
    }

    @Action
    public void addTemplate()
    {
        final WindowCallback windowCallback = new WindowCallback();
        TemplateOrdersTab templateOrdersTab = new TemplateOrdersTab();
        ((TemplateOrdersUpdater) templateOrdersTab.getListNaviTable().getListUpdater()).setNewEditDeleteActions(new AEntityNEDActions<TemplateOrder>()
        {

            @Override
            public void openValue()
            {
                if (getSelectedElement() != null)
                {
                    final Builder builder = new Builder();
                    TemplateOrder source = FacadeContext.getTemplateOrderFacade().findBy(getSelectedElement().getId());
                    FacadeContext.getTemplateOrderFacade().copy(source, getOrder(), new Callback<OrderItem>()
                    {
                        @Override
                        public void callback(OrderItem entity)
                        {
                            TemplateItemNode node = builder.build(entity);
                            TreePath path = new TreePath(node.getPath());
                            getTree().setSelectionPath(path);
                        }
                    });

                    windowCallback.dispose();
                }
                else
                {
                    MessageDialog.showSimpleMessage(MessageDialog.NO_ROW_SELECTED);
                }
            }
        });
        templateOrdersTab.init();
        templateOrdersTab.setValue(null);
        DialogShowers.showBy(templateOrdersTab, getTree(), windowCallback, true);
    }

    public NodeBuilder getNodeBuilder()
    {
        return nodeBuilder;
    }

    public void setNodeBuilder(NodeBuilder nodeBuilder)
    {
        this.nodeBuilder = nodeBuilder;
    }

    public class Builder
    {
        private Map<AOrder, TemplateOrderNode> map = new HashMap<>();

        public TemplateItemNode build(OrderItem orderItem)
        {
            TemplateItemNode itemNode = nodeBuilder.build(TemplateItemNode.class, MapUtils.putAll(new HashMap<String, Object>(), new Object[]{"orderItem", orderItem}));
            if (orderItem.getSource() != null)
            {
                AOrder order = orderItem.getSource().getOrder();
                TemplateOrderNode node = map.get(order);
                if (node == null)
                {
                    node = nodeBuilder.build(TemplateOrderNode.class, MapUtils.putAll(new HashMap<String, Object>(), new Object[]{"order", order, "userObject", order.getName()}));
                    map.put(order, node);
                    ((DefaultTreeModel) getTree().getModel()).insertNodeInto(node, TemplatesNode.this, TemplatesNode.this.getChildCount());
                }
                ((DefaultTreeModel) getTree().getModel()).insertNodeInto(itemNode, node, node.getChildCount());
            }
            else
            {
                TemplatesNode.this.add(itemNode);
            }
            return itemNode;
        }
    }
}
