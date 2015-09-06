package by.dak.report.jasper.common;

import by.dak.report.jasper.common.data.CommonData;
import net.sf.jasperreports.engine.JasperReport;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 28.09.2009
 * Time: 13:15:08
 * To change this template use File | Settings | File Templates.
 */
public class SubreportValue
{
    private JasperReport JasperReport;
    private List<CommonData> data;

    public JasperReport getJasperReport()
    {
        return JasperReport;
    }

    public void setJasperReport(JasperReport jasperReport)
    {
        JasperReport = jasperReport;
    }

    public List<CommonData> getData()
    {
        return data;
    }

    public void setData(List<CommonData> data)
    {
        this.data = data;
    }
}
