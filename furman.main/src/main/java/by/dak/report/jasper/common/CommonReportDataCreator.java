package by.dak.report.jasper.common;

import by.dak.persistence.entities.AOrder;
import by.dak.report.jasper.*;
import by.dak.report.jasper.common.data.CommonData;
import by.dak.report.jasper.common.data.CommonDataType;
import by.dak.report.jasper.common.data.CommonReportData;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static by.dak.report.jasper.common.CommonReportDataCreator.SubreportKey.*;

/**
 * @author Denis Koyro
 * @version 0.1 22.03.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class CommonReportDataCreator extends ReportDataCreatorDecorator
{

    static enum SubreportKey
    {
        Service,
        Sheet,
        Railing,
        Sellotape,
        Additional;

        private static final String PREFIX = "CommonSubreport";
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

    private static final Logger LOGGER = Logger.getLogger(CommonReportDataCreator.class.getName());

    private static final String JASPER_REPORT_PATH = "CommonReport.jasper";
    private static final String REPORT_BUNDLES_PATH = "by/dak/report/jasper/common/commonReport";

    private static final String PARAMETER_commonCost = "commonCost";

    private CommonReportData commonReportData;

    public CommonReportDataCreator(CommonReportData commonReportData)
    {
        super(new DefaultReportDataCreator(REPORT_BUNDLES_PATH));
        this.commonReportData = commonReportData;
    }

    protected Double getCommonCost()
    {
        return commonReportData.getCommonCost();
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

            parameters.put(PARAMETER_commonCost, getCommonCost());

            fillSubreport(Service, parameters);
            fillSubreport(Sheet, parameters);
            fillSubreport(Railing, parameters);
            fillSubreport(Sellotape, parameters);
            fillSubreport(Additional, parameters);

            URL definitionPath = CommonReportDataCreator.class.getResource(JASPER_REPORT_PATH);
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
            case Railing:
                subreportValue.setData(commonReportData.getCommonDatas(CommonDataType.border));
                break;
            case Sellotape:
                subreportValue.setData(commonReportData.getFurnitureData());
                break;
            case Sheet:
                subreportValue.setData(commonReportData.getCommonDatas(CommonDataType.board));
                break;
            case Additional:
                subreportValue.setData(commonReportData.getCommonDatas(CommonDataType.additional));
                break;
            default:
                throw new IllegalArgumentException();
        }


        subreportValue.setJasperReport((JasperReport) JRLoader.loadObject(CommonReportDataCreator.class.getResource(subreportKey.getPath())));
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
