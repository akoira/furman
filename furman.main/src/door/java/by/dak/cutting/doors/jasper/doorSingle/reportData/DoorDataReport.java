package by.dak.cutting.doors.jasper.doorSingle.reportData;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 05.10.2009
 * Time: 12:15:57
 * To change this template use File | Settings | File Templates.
 */
public class DoorDataReport
{
    private Long number;
    private Long width;
    private Long height;
    private String profile;
    private String color;
    private JasperReport subreportProfile;
    private JRDataSource subreportDatasourceProfile;
    private Map subreportProfileMap;
    private List<ProfileDataReport> profileDataReports;
    private List<FillingDataReport> fillingDataReports;
    private Image image;

    public DoorDataReport()
    {
    }

    public DoorDataReport(Long number, Long width, Long height, String profile, String color, Image image)
    {
        this.number = number;
        this.width = width;
        this.height = height;
        this.profile = profile;
        this.color = color;
        this.image = image;
    }

    public Image getImage()
    {
        return image;
    }

    public void setImage(Image image)
    {
        this.image = image;
    }

    public Long getNumber()
    {
        return number;
    }

    public void setNumber(Long number)
    {
        this.number = number;
    }

    public Long getWidth()
    {
        return width;
    }

    public void setWidth(Long width)
    {
        this.width = width;
    }

    public Long getHeight()
    {
        return height;
    }

    public void setHeight(Long height)
    {
        this.height = height;
    }

    public String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        this.profile = profile;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public List<ProfileDataReport> getProfileDataReports()
    {
        return profileDataReports;
    }

    public void setProfileDataReports(List<ProfileDataReport> profileDataReports)
    {
        this.profileDataReports = profileDataReports;
    }

    public List<FillingDataReport> getFillingDataReports()
    {
        return fillingDataReports;
    }

    public void setFillingDataReports(List<FillingDataReport> fillingDataReports)
    {
        this.fillingDataReports = fillingDataReports;
    }

    public Map getSubreportProfileMap()
    {
        return subreportProfileMap;
    }

    public JasperReport getSubreportProfile()
    {
        return subreportProfile;
    }

    public void setSubreportProfile(JasperReport subreportProfile)
    {
        this.subreportProfile = subreportProfile;
    }

    public JRDataSource getSubreportDatasourceProfile()
    {
        return subreportDatasourceProfile;
    }

    public void setSubreportDatasourceProfile(JRDataSource subreportDatasourceProfile)
    {
        this.subreportDatasourceProfile = subreportDatasourceProfile;
    }

    public void setSubreportProfileMap(Map subreportProfileMap)
    {
        this.subreportProfileMap = subreportProfileMap;
    }
}
