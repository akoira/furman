package by.dak.buffer.statistic.filter;

import by.dak.buffer.statistic.swing.DilerOrderStatisticsPanel;
import by.dak.cutting.SpringConfiguration;
import by.dak.test.TestUtils;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 27.11.2010
 * Time: 23:04:46
 * To change this template use File | Settings | File Templates.
 */
public class TDilerOrderStatisticsPanel
{
    public static void main(String[] args)
    {
        new SpringConfiguration();
        DilerOrderStatisticsPanel dilerOrderStatisticsPanel = new DilerOrderStatisticsPanel();
        TestUtils.showFrame(dilerOrderStatisticsPanel, "test");
    }
}
