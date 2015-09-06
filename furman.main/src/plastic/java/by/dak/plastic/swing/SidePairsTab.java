package by.dak.plastic.swing;

import by.dak.cutting.swing.AListTab;
import by.dak.cutting.swing.order.cellcomponents.editors.CheckBoxCellEditor;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.ListUpdater;
import org.jdesktop.application.ResourceMap;

import javax.swing.table.TableCellEditor;

/**
 * User: akoyro
 * Date: 28.11.11
 * Time: 17:59
 */
public class SidePairsTab extends AListTab<SidePair, DSPPlasticValue>
{

    @Override
    protected void initBindingListeners()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void valueChanged()
    {
        getListNaviTable().reload();
    }

    @Override
    protected void initEnvironment()
    {
        super.initEnvironment();

        ListUpdater<SidePair> updater = new AListUpdater<SidePair>()
        {
            private EditorsProvider editorsProvider = new EditorsProvider(getList());

            @Override
            public void update()
            {
                getList().clear();
                if (getValue() != null)
                    getList().addAll(getValue().getSidePairs());
            }


            @Override
            public String[] getVisibleProperties()
            {
                return new String[]{SidePair.PROPERTY_need,
                        SidePair.PROPERTY_first + "." + TextureBoardDefPair.PROPERTY_texture,
                        SidePair.PROPERTY_second + "." + TextureBoardDefPair.PROPERTY_texture};
            }

            @Override
            public ResourceMap getResourceMap()
            {
                return SidePairsTab.this.getResourceMap();
            }

            @Override
            public String[] getEditableProperties()
            {
                return new String[]{SidePair.PROPERTY_need, SidePair.PROPERTY_second + "." + TextureBoardDefPair.PROPERTY_boardDef, SidePair.PROPERTY_second + "." + TextureBoardDefPair.PROPERTY_texture};
            }


            @Override
            public TableCellEditor getTableCellEditor(String propertyName)
            {
                if (propertyName.equals(SidePair.PROPERTY_second + "." + TextureBoardDefPair.PROPERTY_boardDef))
                {
                    return editorsProvider.getCodeCellEditor();
                }
                else if (propertyName.equals(SidePair.PROPERTY_second + "." + TextureBoardDefPair.PROPERTY_texture))
                {
                    return editorsProvider.getCodeCellEditor();
                }
                else if (propertyName.equals(SidePair.PROPERTY_need))
                {
                    return new CheckBoxCellEditor();
                }
                return super.getTableCellEditor(propertyName);
            }

            @Override
            public int getCount()
            {
                return getValue() != null ? getValue().getSidePairs().size() : 0;
            }


        };
        getListNaviTable().setListUpdater(updater);
        getListNaviTable().init();

    }
}
