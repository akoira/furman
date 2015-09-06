package by.dak.cutting.doors.jasper.doorSingle;

import net.sf.jasperreports.engine.JasperReport;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 05.10.2009
 * Time: 12:39:59
 * To change this template use File | Settings | File Templates.
 */
public class SubreportValueDoor
{
    private JasperReport JasperReport;
    private List data;

    public SubreportValueDoor()
    {
    }

    public JasperReport getJasperReport()
    {
        return JasperReport;
    }

    public void setJasperReport(JasperReport jasperReport)
    {
        JasperReport = jasperReport;
    }

    public List getData()
    {
        return data;
    }

    public void setData(List data)
    {
        this.data = data;
    }
}
