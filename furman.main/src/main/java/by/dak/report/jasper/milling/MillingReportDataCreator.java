package by.dak.report.jasper.milling;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.report.jasper.OrderHeaderReportDataCreator;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 13.02.2009
 * Time: 11:07:43
 */
public class MillingReportDataCreator extends OrderHeaderReportDataCreator
{

    private static final String JASPER_REPORT_PATH = "milling.report.jasper";

    private static final String REPORT_BUNDLES_PATH = "by/dak/report/jasper/milling/millingReport";


    public MillingReportDataCreator(AOrder order, MainFacade mainFacade)
    {
        super(order, mainFacade);
    }

    @Override
    public Collection createCollection()
    {
        List<OrderFurniture> furnitures = FacadeContext.getOrderFurnitureFacade().findWithMillingBy(getOrder());
        List<MillingReportData> list = new ArrayList<MillingReportData>(furnitures.size());

        for (OrderFurniture furniture1 : furnitures)
        {
            MillingReportData millingReportData = new MillingReportData(furniture1, mainFacade);
            list.add(millingReportData);
        }
        return list;
    }

    @Override
    public URL getJasperReportPath()
    {
        return MillingReportDataCreator.class.getResource(JASPER_REPORT_PATH);
    }

    @Override
    public String getReportBundlesPath()
    {
        return REPORT_BUNDLES_PATH;
    }
}
