package by.dak.cutting.swing;

import org.bushe.swing.event.EventSubscriber;
import org.jdesktop.beansbinding.BindingGroup;

import javax.swing.*;
import java.util.List;

/**
 * @author admin
 * @version 0.1 24.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
@Deprecated
public class EntityTableHelper<ENTITY, EVENT> implements EventSubscriber<EVENT>
{
    private BindingGroup bindingGroup = new BindingGroup();
    private List<ENTITY> list;
    private JTableUtils.AddEntity<ENTITY> addEntity;
    private JTable table;

    public EntityTableHelper(JTable table)
    {
        this.table = table;
    }

    public BindingGroup getBindingGroup()
    {
        return bindingGroup;
    }

    public List<ENTITY> getList()
    {
        return list;
    }

    public void setList(List<ENTITY> list)
    {
        this.list = list;
        addEntity = new JTableUtils.AddEntity<ENTITY>(getList(), table);
    }


    public void onEvent(EVENT event)
    {
        //addEntity.add(event.getEntity());
    }
}
