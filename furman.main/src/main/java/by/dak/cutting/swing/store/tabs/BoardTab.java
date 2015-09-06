package by.dak.cutting.swing.store.tabs;

import by.dak.persistence.entities.Board;
import by.dak.persistence.entities.BoardDef;
import by.dak.swing.FocusToFirstComponent;
import by.dak.utils.BindingAdapter;
import org.jdesktop.beansbinding.Binding;

/**
 * User: akoyro
 * Date: 20.11.2009
 * Time: 19:56:54
 */
public class BoardTab extends AStoreElementTab<Board> implements FocusToFirstComponent
{

    @Override
    public void init()
    {
        super.init();
        getBindingGroup().addValueBindingListener(new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                Binding.ValueResult valueResult = binding.getTargetValueForSource();
                if (getValue() == null || valueResult.failed())
                {
                    return;
                }
                if (valueResult.getValue() instanceof BoardDef)
                {
                    getValue().setLength(((BoardDef) valueResult.getValue()).getDefaultLength());
                    getValue().setWidth(((BoardDef) valueResult.getValue()).getDefaultWidth());
                }
            }
        });
    }

    @Override
    public void setFocusToFirstComponent()
    {
        getEditors().get("boardDef").requestFocusInWindow();
    }
}
