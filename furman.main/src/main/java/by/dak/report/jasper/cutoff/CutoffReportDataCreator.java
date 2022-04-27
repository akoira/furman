package by.dak.report.jasper.cutoff;

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
public class CutoffReportDataCreator extends OrderHeaderReportDataCreator
{
    private static final String JASPER_REPORT_PATH = "CutoffReport.jasper";

    private static final String REPORT_BUNDLES_PATH = "by/dak/report/jasper/cutoff/cutoffReport";

    public CutoffReportDataCreator(AOrder order, MainFacade mainFacade)
    {
        super(order, mainFacade);
    }

    @Override
    public Collection createCollection()
    {
        List<OrderFurniture> furnitures = FacadeContext.getOrderFurnitureFacade().findWithCutoffBy(getOrder());
        List<CutoffReportData> list = new ArrayList<CutoffReportData>(furnitures.size());

        for (int i = 0; i < furnitures.size(); i++)
        {
            OrderFurniture furniture1 = furnitures.get(i);
            OrderFurniture furniture2 = null;
            i++;
            if (i < furnitures.size())
            {
                furniture2 = furnitures.get(i);
            }

            CutoffReportData cutoffReportData = new CutoffReportData(furniture1, furniture2);
            list.add(cutoffReportData);
        }
        return list;
    }

    @Override
    public URL getJasperReportPath()
    {
        return CutoffReportDataCreator.class.getResource(JASPER_REPORT_PATH);
    }

    @Override
    public String getReportBundlesPath()
    {
        return REPORT_BUNDLES_PATH;
    }
}