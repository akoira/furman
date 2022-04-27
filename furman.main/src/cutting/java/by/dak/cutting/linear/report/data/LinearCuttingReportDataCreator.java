package by.dak.cutting.linear.report.data;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.linear.LinearElementDimensionItem;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.MainFacade;
import by.dak.report.jasper.*;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.net.URL;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 28.04.11
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
 */
public class LinearCuttingReportDataCreator extends ReportDataCreatorDecorator
{
    private static final String JASPER_REPORT_PATH = "LinearCuttingReport.jasper";
    private static final String REPORT_BUNDLES_PATH = "by/dak/cutting/linear/report/data/linearCuttingReport";
    private static final String CUTTING_MAP_IMAGE = "cuttingMap";
    private static final String FURNITURE_CODE = "furnitureCode";
    private static final String FURNITURE_TYPE = "furnitureType";
    private static final String TOTAL_SHEET_COUNT = "totalSheetCount";
    private static final String CARD_NUMBER = "cardNumber";
    private static final String ORDERS = "orders";
    private static final String PRODUCTION_DATE = "productionDate";
    private static final String SUBREPORT_PATH = "DetailsSubReport.jasper";
    private static final String DETAILS_REPORT = "detailsTableReport";
    private static final String SUB_REPORT_DATASOURCE = "subReportDatasource";

    private LinearCuttedDataReport cuttedReportData;

    public LinearCuttingReportDataCreator(LinearCuttedDataReport cuttedReportData)
    {
        super(new DefaultReportDataCreator(REPORT_BUNDLES_PATH), new MainFacade());
        this.cuttedReportData = cuttedReportData;
    }

    public JReportData create()
    {
        try
        {
            JasperReport subReport = (JasperReport) JRLoader.loadObject(LinearCuttingReportDataCreator.class.getResource(SUBREPORT_PATH));

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put(DETAILS_REPORT, subReport);
            parameters.put(JRParameter.REPORT_LOCALE, new Locale("ru"));
            parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, getResourceBundle());
            parameters.put(JRParameter.REPORT_CONTEXT, Constants.DUMMY_REPORT_CONTEXT);

            URL definitionPath = LinearCuttingReportDataCreator.class.getResource(JASPER_REPORT_PATH);
            return new JReportDataImpl(new JRMapCollectionDataSource(createMasterDataMap()), parameters, definitionPath, getResourceBundle(), getLocale());
        }
        catch (Exception e)
        {
            return JReportData.UNKNOWN;
        }
    }

    private Collection<Map<String, ?>> createMasterDataMap()
    {
        List<Map<String, ?>> masterDataMap = new ArrayList<Map<String, ?>>();
        List<LinearMaterialCuttedData> materialCuttedDataList = cuttedReportData.getMaterialCuttedData();

        for (int cardNumber = 0; cardNumber < materialCuttedDataList.size(); cardNumber++)
        {
            LinearMaterialCuttedData materialCuttedData = materialCuttedDataList.get(cardNumber);
            List<LinearCuttedSheetData> sheetDataList = materialCuttedData.getCuttedSheetData();
            int totalSheetCount = sheetDataList.size();
            for (LinearCuttedSheetData cuttedSheetData : sheetDataList)
            {
                masterDataMap.add(createSheetDataMap(cuttedSheetData, materialCuttedData, totalSheetCount, cardNumber));
            }
        }
        return masterDataMap;
    }

    private Map createSheetDataMap(LinearCuttedSheetData sheetData, LinearMaterialCuttedData materialCuttedData, int totalSheets, int cardNumber)
    {
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(CUTTING_MAP_IMAGE, sheetData.getCuttingMap());
        parameters.put(FURNITURE_TYPE, materialCuttedData.getPair().getFurnitureType().getName());
        parameters.put(FURNITURE_CODE, ReportUtils.formatFurnitureCode(materialCuttedData.getPair().getFurnitureCode()));
        parameters.put(TOTAL_SHEET_COUNT, totalSheets);
        parameters.put(CARD_NUMBER, cardNumber + 1);
        parameters.put(ORDERS, FacadeContext.getOrderFacade().getOrdersStringBy(cuttedReportData.getOrderGroup()));
        parameters.put(PRODUCTION_DATE, cuttedReportData.getOrderGroup().getDailysheet().getDate());
        parameters.put(SUB_REPORT_DATASOURCE, new JRBeanCollectionDataSource(createDetails(sheetData)));

        return parameters;
    }

    private Collection createDetails(LinearCuttedSheetData sheetData)
    {
        List<ElementDetailsData> elementDetailsDataList = new ArrayList<ElementDetailsData>();
        List<Element> elements = sheetData.getElements();
        Collections.sort(elements, new Comparator<Element>()
        {
            @Override
            public int compare(Element o1, Element o2)
            {
                if (((LinearElementDimensionItem) o1.getDimensionItem()).getNumber() <
                        ((LinearElementDimensionItem) o2.getDimensionItem()).getNumber())
                {
                    return -1;
                }
                else if (((LinearElementDimensionItem) o1.getDimensionItem()).getNumber() ==
                        ((LinearElementDimensionItem) o2.getDimensionItem()).getNumber())
                {
                    return 0;
                }
                return 1;
            }
        });

        List<LinearElementDimensionItem> dimensionItems = new ArrayList<LinearElementDimensionItem>();

        for (Element element : elements)
        {
            if (!dimensionItems.contains((LinearElementDimensionItem) element.getDimensionItem()))
            {
                dimensionItems.add((LinearElementDimensionItem) element.getDimensionItem());
            }
        }

        for (LinearElementDimensionItem dimensionItem : dimensionItems)
        {
            ElementDetailsData elementDetailsData = new ElementDetailsData();
            elementDetailsData.setElementNumber(dimensionItem.getNumber());
            elementDetailsData.setElementCount(dimensionItem.getCount());
            elementDetailsData.setSize(dimensionItem.getFurnitureLink().getSize());
            elementDetailsDataList.add(elementDetailsData);
        }

        return elementDetailsDataList;
    }
}
