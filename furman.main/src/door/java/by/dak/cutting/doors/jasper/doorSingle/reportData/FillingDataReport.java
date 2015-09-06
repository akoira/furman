package by.dak.cutting.doors.jasper.doorSingle.reportData;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 05.10.2009
 * Time: 12:16:36
 * To change this template use File | Settings | File Templates.
 */
public class FillingDataReport
{
    private String name;
    private String material;
    private String colorTexture;
    private Long count;

    public FillingDataReport()
    {
    }

    public FillingDataReport(String name, String material, String colorTexture, Long count)
    {
        this.name = name;
        this.material = material;
        this.colorTexture = colorTexture;
        this.count = count;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMaterial()
    {
        return material;
    }

    public void setMaterial(String material)
    {
        this.material = material;
    }

    public String getColorTexture()
    {
        return colorTexture;
    }

    public void setColorTexture(String colorTexture)
    {
        this.colorTexture = colorTexture;
    }

    public Long getCount()
    {
        return count;
    }

    public void setCount(Long count)
    {
        this.count = count;
    }

    public java.util.List<FillingDataReport> getMainAggregatedData()
    {
        ArrayList<FillingDataReport> datas = new ArrayList<FillingDataReport>();
        datas.add(this);
        return datas;
    }
}
