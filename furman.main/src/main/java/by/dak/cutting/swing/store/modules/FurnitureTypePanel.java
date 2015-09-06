package by.dak.cutting.swing.store.modules;

import by.dak.cutting.swing.ValueSave;
import by.dak.cutting.swing.store.tabs.FurnitureTypeTab;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.types.FurnitureType;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14.01.2010
 * Time: 11:31:19
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureTypePanel extends AEntityEditorPanel<FurnitureType>
{
    private FurnitureTypeTab furnitureTypeTab;

    public FurnitureTypePanel()
    {
        super();
        setValueSave(new FurnitureTypeValueSave());
        setShowOkCancel(false);
    }

    @Override
    protected void addTabs()
    {
        furnitureTypeTab = new FurnitureTypeTab();
        addTab(furnitureTypeTab);
        setTab(furnitureTypeTab);
    }

    public static class FurnitureTypeValueSave implements ValueSave<FurnitureType>
    {
        @Override
        public void save(FurnitureType value)
        {
            if (value != null)
            {
                FacadeContext.getFurnitureTypeFacade().save(value);
            }
        }
    }
}
