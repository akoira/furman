package by.dak.cutting.doors.jasper.doorSingle.reportData;

import by.dak.persistence.entities.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 05.10.2009
 * Time: 13:52:12
 * To change this template use File | Settings | File Templates.
 */
public class DoorSingleDataReport
{
    private Order order;
    private List<DoorDataReport> doorDataReports;

    public DoorSingleDataReport()
    {
    }

    public DoorSingleDataReport(Order order)
    {
        this.order = order;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }

    public List<DoorDataReport> getDoorDataReports()
    {
        return doorDataReports;
    }

    public void setDoorDataReports(List<DoorDataReport> doorDataReports)
    {
        this.doorDataReports = doorDataReports;
    }

    public java.util.List<DoorDataReport> getMainAggregatedData()
    {
        ArrayList<DoorDataReport> datas = new ArrayList<DoorDataReport>();
        for (DoorDataReport doorDataReport : doorDataReports)
        {
            datas.add(doorDataReport);
        }
        return datas;
    }
}
