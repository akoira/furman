package by.dak.cutting.swing.order;


import by.dak.cutting.swing.renderer.EntityListRenderer;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BorderDefEntity;

import javax.swing.*;
import java.util.List;
import java.util.Vector;

public class BorderSearchInsertPanel extends SearchInsertPanel
{

    private List<BorderDefEntity> cacheBorderList;

    public BorderSearchInsertPanel(boolean isRenderer)
    {
        super(isRenderer);
    }

    public BorderSearchInsertPanel()
    {
        super(false);
    }

    @Override
    protected void bind()
    {
        if (cacheBorderList == null)
            cacheBorderList = FacadeContext.getBorderDefFacade().loadAll();
        dComboBox.setRenderer(new EntityListRenderer<BorderDefEntity>());
        dComboBox.setModel(new DefaultComboBoxModel(new Vector<BorderDefEntity>(cacheBorderList)));
        dComboBox.setSelectedItem(null);
    }

    @Override
    protected void showDialog()
    {
        //todo need show wizart
    }
}
