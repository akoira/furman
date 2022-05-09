package by.dak.cutting.doors.jasper.doorSingle;

import by.dak.cutting.doors.jasper.doorSingle.reportData.DoorDataReport;
import by.dak.cutting.doors.jasper.doorSingle.reportData.DoorSingleDataReport;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.Order;
import by.dak.report.jasper.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 05.10.2009
 * Time: 12:24:08
 * To change this template use File | Settings | File Templates.
 */
public class ReportDoorDataCreator extends ReportDataCreatorDecorator
{
    static enum SubreportKey
    {
        Profile,
        Filling;

        private static final String PREFIX = "SubReport";
        private static final String SUFFIX = ".jasper";
        private static final String SUBREPORT = "subreport";
        private static final String DATASOURCE = "Datasource";

        public String getPath()
        {
            return PREFIX + name() + SUFFIX;
        }

        public String getSubreportKey()
        {
            return SUBREPORT + name();
        }

        public String getSubreportDatasourceKey()
        {
            return SUBREPORT + DATASOURCE + name();
        }
    }

    private static final Logger LOGGER = Logger.getLogger(ReportDoorDataCreator.class.getName());

    private static final String JASPER_REPORT_PATH = "ReportDoor.jasper";
    private static final String REPORT_BUNDLES_PATH = "by/dak/cutting/doors/jasper/doorSingle/commonReport";
    private DoorSingleDataReport doorSingleData;

    public ReportDoorDataCreator(DoorSingleDataReport doorSingleData)
    {
        super(new DefaultReportDataCreator(REPORT_BUNDLES_PATH), new MainFacade());
        this.doorSingleData = doorSingleData;
    }

    @Override
    public JReportData create()
    {
        try
        {
            Order order = doorSingleData.getOrder();

            ReportTitle reportTitle = new ReportTitle(order);
            HashMap<String, Object> parameters = reportTitle.getParameters();
            parameters.put(JRParameter.REPORT_LOCALE, getLocale());
            parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, getResourceBundle());
            parameters.put(JRParameter.REPORT_CONTEXT, Constants.DUMMY_REPORT_CONTEXT);

            for (DoorDataReport doorDataReport : doorSingleData.getDoorDataReports())
            {
                fillSubreport(doorDataReport);
            }

            URL definitionPath = ReportDoorDataCreator.class.getResource(JASPER_REPORT_PATH);
            return new JReportDataImpl(new JRBeanCollectionDataSource(doorSingleData.getMainAggregatedData()), parameters, definitionPath, getResourceBundle(), getLocale());
        }
        catch (JRException e)
        {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
            return JReportData.UNKNOWN;
        }
    }

    private void fillSubreport(DoorDataReport doorDataReport) throws JRException
    {
        Map<String, Object> subreportMap = new HashMap<String, Object>();
        doorDataReport.setSubreportProfileMap(subreportMap);
        doorDataReport.setSubreportProfile((JasperReport) JRLoader.loadObject(ReportDoorDataCreator.class.getResource(SubreportKey.Profile.getPath())));
        doorDataReport.setSubreportDatasourceProfile(new JRBeanCollectionDataSource(doorDataReport.getProfileDataReports()));

        subreportMap.put(JRParameter.REPORT_LOCALE, getLocale());
        subreportMap.put(JRParameter.REPORT_RESOURCE_BUNDLE, getResourceBundle());
        subreportMap.put(JRParameter.REPORT_CONTEXT, Constants.DUMMY_REPORT_CONTEXT);

        subreportMap.put(SubreportKey.Filling.getSubreportKey(), JRLoader.loadObject(ReportDoorDataCreator.class.getResource(SubreportKey.Filling.getPath())));
        subreportMap.put(SubreportKey.Filling.getSubreportDatasourceKey(), new JRBeanCollectionDataSource(doorDataReport.getFillingDataReports()));
    }
}
