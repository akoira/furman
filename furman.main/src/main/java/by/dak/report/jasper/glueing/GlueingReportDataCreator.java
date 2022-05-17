package by.dak.report.jasper.glueing;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.report.jasper.OrderHeaderReportDataCreator;
import by.dak.utils.convert.ListConverter;
import by.dak.utils.convert.Validator;

import java.net.URL;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 13.02.2009
 * Time: 11:07:43
 */
public class GlueingReportDataCreator extends OrderHeaderReportDataCreator
{
    private static final String JASPER_REPORT_PATH = "GlueingReport.jasper";
    private static final String REPORT_BUNDLES_PATH = "by/dak/report/jasper/glueing/glueingReport";


    public GlueingReportDataCreator(AOrder order, MainFacade mainFacade)
    {
        super(order, mainFacade);
    }

    @Override
    public URL getJasperReportPath()
    {
        return GlueingReportDataCreator.class.getResource(JASPER_REPORT_PATH);
    }

    @Override
    public String getReportBundlesPath()
    {
        return REPORT_BUNDLES_PATH;
    }

    @Override
    public Collection createCollection()
    {
        return createGlueingData();
    }

    private List<OrderDetailsData> createGlueingData()
    {
        ListConverter<OrderFurniture, OrderDetailsData> converter = new ListConverter<OrderFurniture, OrderDetailsData>(
                new GluingReportConverter(),
                source -> (!source.isComplex() || source.isPrimary())
                        && (source.getDrilling() != null || source.getGlueing() != null || source.getMilling() != null));

        List<OrderFurniture> orderFurnitures = FacadeContext.getOrderFurnitureFacade().loadAllBy(getOrder());
        return converter.convert(orderFurnitures);
    }
}