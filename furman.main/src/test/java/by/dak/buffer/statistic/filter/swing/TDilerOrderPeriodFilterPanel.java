package by.dak.buffer.statistic.filter.swing;

import by.dak.buffer.statistic.filter.DilerOrderFilter;
import by.dak.cutting.SpringConfiguration;
import by.dak.test.TestUtils;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 25.11.2010
 * Time: 18:42:31
 * To change this template use File | Settings | File Templates.
 */
public class TDilerOrderPeriodFilterPanel
{
    public static void main(String[] args)
    {
        new SpringConfiguration();
        DilerOrderPeriodFilterPanel dilerOrderPeriodFilterPanel = new DilerOrderPeriodFilterPanel();
        dilerOrderPeriodFilterPanel.init();
        dilerOrderPeriodFilterPanel.setValue(new DilerOrderFilter());
        TestUtils.showFrame(dilerOrderPeriodFilterPanel, "test");
    }


}
