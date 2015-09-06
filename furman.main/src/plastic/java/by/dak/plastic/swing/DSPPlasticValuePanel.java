package by.dak.plastic.swing;

import by.dak.cutting.swing.DModPanel;

/**
 * User: akoyro
 * Date: 28.11.11
 * Time: 18:03
 */
public class DSPPlasticValuePanel extends DModPanel<DSPPlasticValue>
{
    private DSPPlasticValueTab plasticValueTab = new DSPPlasticValueTab();
    private SidePairsTab sidePairsTab = new SidePairsTab();


    public DSPPlasticValuePanel()
    {
        addTabs();
    }

    @Override
    protected void addTabs()
    {
        addTab(plasticValueTab);
        addTab(sidePairsTab);
        //sidePairsTab.init();
    }

}
