package by.dak.cutting.doors.jasper.doorSingle.reportData;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 05.10.2009
 * Time: 12:16:19
 * To change this template use File | Settings | File Templates.
 */
public class ProfileDataReport
{
    private String type;
    private Long count;

    public ProfileDataReport()
    {
    }

    public ProfileDataReport(String type, Long count)
    {
        this.type = type;
        this.count = count;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Long getCount()
    {
        return count;
    }

    public void setCount(Long count)
    {
        this.count = count;
    }

    public java.util.List<ProfileDataReport> getMainAggregatedData()
    {
        ArrayList<ProfileDataReport> datas = new ArrayList<ProfileDataReport>();
        datas.add(this);
        return datas;
    }
}
