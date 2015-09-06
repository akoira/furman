package by.dak.cutting.doors.jasper.common.reportData;

import by.dak.persistence.entities.Order;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 29.09.2009
 * Time: 17:22:53
 * To change this template use File | Settings | File Templates.
 */
public class DoorsDataReport
{
    private Order order;
    private String profile;
    private String name;
    private String color;
    private Long rail;
    private Long count;
    private Image image;

    public DoorsDataReport()
    {
    }

    public DoorsDataReport(Order order, String profile, String name, String color, Long rail, Long count, Image image)
    {
        this.order = order;
        this.profile = profile;
        this.name = name;
        this.color = color;
        this.rail = rail;
        this.count = count;
        this.image = image;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }

    public String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        this.profile = profile;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public Long getRail()
    {
        return rail;
    }

    public void setRail(Long rail)
    {
        this.rail = rail;
    }

    public Long getCount()
    {
        return count;
    }

    public void setCount(Long count)
    {
        this.count = count;
    }

    public Image getImage()
    {
        return image;
    }

    public void setImage(Image image)
    {
        this.image = image;
    }

    public java.util.List<DoorsDataReport> getMainAggregatedData()
    {
        ArrayList<DoorsDataReport> datas = new ArrayList<DoorsDataReport>();
        datas.add(this);
        return datas;
    }
}
