package by.dak.report.jasper.common;

import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.report.jasper.*;
import by.dak.report.jasper.common.data.CommonData;
import by.dak.report.jasper.common.data.CommonDataType;
import by.dak.report.jasper.common.data.CommonReportData;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static by.dak.report.jasper.common.SimpleCommonReportDataCreator.SubreportKey.*;

/**
 * @author Denis Koyro
 * @version 0.1 22.03.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class SimpleCommonReportDataCreator extends ReportDataCreatorDecorator
{

    static enum SubreportKey
    {
        Service,
        AdditionalService,
        Additional;

        private static final String PREFIX = "SimpleCommonSubreport";
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

    private static final Logger LOGGER = Logger.getLogger(SimpleCommonReportDataCreator.class.getName());

    private static final String JASPER_REPORT_PATH = "SimpleCommonReport.jasper";
    private static final String REPORT_BUNDLES_PATH = "by/dak/report/jasper/common/commonReport";

    private CommonReportData commonReportData;

    public SimpleCommonReportDataCreator(CommonReportData commonReportData, MainFacade mainFacade)
    {
        super(new DefaultReportDataCreator(REPORT_BUNDLES_PATH), mainFacade);
        this.commonReportData = commonReportData;
    }

    @Override
    public JReportData create()
    {
        try
        {
            AOrder order = commonReportData.getOrder();

            ReportTitle reportTitle = new ReportTitle(order);
            HashMap<String, Object> parameters = reportTitle.getParameters();
            parameters.put(JRParameter.REPORT_LOCALE, getLocale());
            parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, getResourceBundle());
            parameters.put(JRParameter.REPORT_CONTEXT, Constants.DUMMY_REPORT_CONTEXT);

            fillSubreport(Service, parameters);
            fillSubreport(AdditionalService, parameters);
            fillSubreport(Additional, parameters);

            URL definitionPath = SimpleCommonReportDataCreator.class.getResource(JASPER_REPORT_PATH);
            return new JReportDataImpl(new JREmptyDataSource(), parameters, definitionPath, getResourceBundle(), getLocale());
        }
        catch (JRException e)
        {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
            return JReportData.UNKNOWN;
        }
    }

    private SubreportValue getSubreportValue(SubreportKey subreportKey) throws JRException
    {
        SubreportValue subreportValue = new SubreportValue();

        switch (subreportKey)
        {
            case Service:
                subreportValue.setData(commonReportData.getServicesData());
                break;
            case AdditionalService:
                subreportValue.setData(commonReportData.getAdditionalServicesData());
                break;
            case Additional:
                subreportValue.setData(commonReportData.getCommonDatas(CommonDataType.additional));
                break;
            default:
                throw new IllegalArgumentException();
        }


        subreportValue.setJasperReport((JasperReport) JRLoader.loadObject(SimpleCommonReportDataCreator.class.getResource(subreportKey.getPath())));
        return subreportValue;
    }


    private void fillSubreport(SubreportKey subreportKey, Map<String, Object> parameters) throws JRException
    {
        SubreportValue subreportValue = getSubreportValue(subreportKey);
        parameters.put(subreportKey.getSubreportKey(), subreportValue.getJasperReport());
        List<CommonData> datas = subreportValue.getData();
        datas = addEmptyCommonData(datas);
        parameters.put(subreportKey.getSubreportDatasourceKey(), new JRBeanCollectionDataSource(datas));
    }

    private List<CommonData> addEmptyCommonData(List<CommonData> datas)
    {
        if (datas == null || datas.size() < 1)//необходима для того чтобы работала цепочка подразделов
        {
            CommonData data = new CommonData();
            data.setName("");
            data.setService("");
            data.markAsLast();
            datas = Collections.singletonList(data);
        }
        return datas;
    }
}
