package by.dak.cutting.swing.store.modules;

import by.dak.cutting.swing.store.tabs.BorderTab;
import by.dak.persistence.entities.Border;

/**
 * User: akoyro
 * Date: 21.11.2009
 * Time: 0:35:46
 */
public class BorderPanel extends AEntityEditorPanel<Border>
{
    private BorderTab borderTab;

    @Override
    protected void addTabs()
    {
        borderTab = new BorderTab();
        setTab(borderTab);
        addTab(getResourceMap().getString("tab.border.title"), borderTab);
    }
}
