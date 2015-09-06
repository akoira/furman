package by.dak.cutting.swing.dictionaries;

import by.dak.cutting.swing.DModPanel;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.PriceEntity;
import by.dak.swing.TabsContainer;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.util.List;

/**
 * User: akoyro
 * Date: 10.06.2009
 * Time: 12:45:13
 */
public class PricesConfigModPanel extends DModPanel<PriceAware>
{
    private APricedCfgPanel pricedCfgPanel;

    public PricesConfigModPanel()
    {
        setShowOkCancel(false);
    }

    /**
     * Invoke the method after all properties are set
     */
    public void init()
    {
        addTabs();
    }

    @Override
    protected void addTabs()
    {
        new TabAdder(getPricedCfgPanel(), this).add();
        getPricedCfgPanel().setWarningList(getWarningList());
    }

    public APricedCfgPanel getPricedCfgPanel()
    {
        return pricedCfgPanel;
    }

    public void setPricedCfgPanel(APricedCfgPanel pricedCfgPanel)
    {
        this.pricedCfgPanel = pricedCfgPanel;
    }

    public static class TabAdder
    {
        private JPanel tab;
        private TabsContainer tabsContainer;

        public TabAdder(JPanel tab, TabsContainer tabsContainer)
        {
            this.tab = tab;
            this.tabsContainer = tabsContainer;
        }

        public void add()
        {
            ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(tab.getClass());
            tabsContainer.addTab(resourceMap.getString("title"), this.tab);
        }
    }

    public List<PriceEntity> getPrices()
    {
        return pricedCfgPanel.getPrices();
    }

    public void setPrices(List<PriceEntity> list)
    {
        pricedCfgPanel.setPrices(list);
    }

    @Override
    public void save()
    {
    }

}
