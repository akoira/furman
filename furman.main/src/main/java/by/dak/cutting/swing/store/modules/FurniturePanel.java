package by.dak.cutting.swing.store.modules;

import by.dak.cutting.swing.store.tabs.FurnitureTab;
import by.dak.persistence.entities.Furniture;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 29.01.2010
 * Time: 14:41:42
 * To change this template use File | Settings | File Templates.
 */
public class FurniturePanel extends AEntityEditorPanel<Furniture>
{
    private FurnitureTab furnitureTab;


    @Override
    protected void addTabs()
    {
        furnitureTab = new FurnitureTab();
        setTab(furnitureTab);
        addTab(getResourceMap().getString("tab.furniture.title"), furnitureTab);
    }

}
