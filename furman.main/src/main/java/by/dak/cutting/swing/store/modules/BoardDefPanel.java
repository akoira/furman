/*
 * To change this template, choose Tools | Templates and open the template in the draw.
 */

package by.dak.cutting.swing.store.modules;

import by.dak.cutting.swing.store.tabs.BoardDefCompositeTypeTab;
import by.dak.cutting.swing.store.tabs.BoardDefTab;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;
import by.dak.utils.BindingAdapter;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingListener;

import javax.swing.*;

/**
 * Furniture Def Panel
 *
 * @author Rudak Alexei
 */
public class BoardDefPanel extends AEntityEditorPanel<BoardDef>
{
    private BoardDefTab boardDefTab;
    private BoardDefCompositeTypeTab boardDefCompositeTab;


    @Override
    protected void addTabs()
    {
        boardDefTab = new BoardDefTab();
        boardDefCompositeTab = new BoardDefCompositeTypeTab(null);
        final String compositePanelName = getResourceMap().getString("FurnitureDefPanel.furnitureDefCompositeTypeTab.title");

        BindingListener compositeListener = new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                Binding.ValueResult valueResult = binding.getTargetValueForSource();
                if (getValue() == null || valueResult.failed())
                {
                    return;
                }
                if (BoardDef.PROPERTY_composite.equals(binding.getName()))
                {
                    if (valueResult.getValue() != null &&
                            valueResult.getValue() instanceof Boolean &&
                            ((Boolean) valueResult.getValue()))
                    {
                        Runnable runnable = new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                insertTab(compositePanelName, null, boardDefCompositeTab, null, 1);
                                setSelectedIndex(1);
                            }
                        };
                        SwingUtilities.invokeLater(runnable);
                    }
                    else
                    {
                        getValue().setSimpleType1(null);
                        getValue().setSimpleType2(null);
                        remove(boardDefCompositeTab);
                    }
                }
            }
        };
        boardDefTab.getBindingGroup().addBindingListener(compositeListener);
        addTab(getResourceMap().getString("FurnitureDefPanel.furnitureDefTab.title"), boardDefTab);
        boardDefCompositeTab = new BoardDefCompositeTypeTab(getValue());
    }

    @Override
    public void save()
    {
        FacadeContext.getBoardDefFacade().save(getValue());
    }
}
