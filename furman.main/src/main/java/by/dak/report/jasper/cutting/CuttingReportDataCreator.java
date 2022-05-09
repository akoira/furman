package by.dak.report.jasper.cutting;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.AOrderBoardDetail;
import by.dak.report.jasper.*;
import by.dak.report.jasper.cutting.data.CuttedReportData;
import by.dak.report.jasper.cutting.data.CuttedSheetData;
import by.dak.report.jasper.cutting.data.MaterialCuttedData;
import by.dak.utils.MathUtils;
import by.dak.utils.convert.ListConverter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.net.URL;
import java.text.MessageFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Denis Koyro
 * @version 0.1 18.03.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class CuttingReportDataCreator<D extends AOrderBoardDetail> extends ReportDataCreatorDecorator
{
    private static final Logger LOGGER = Logger.getLogger(CuttingReportDataCreator.class.getName());

    private static final String JASPER_REPORT_PATH = "CuttingReport.jasper";
    private static final String JASPER_SUBREPORT_PATH = "CuttingReportDetailsSubreport.jasper";
    private static final String REPORT_BUNDLES_PATH = "by/dak/report/jasper/cutting/cuttingReport";
    private static final String LIST_FORMAT_PATTERN = "{0}x{1}";

    private static final String ORDER_NUMBER = "orderNumber";
    private static final String CUSTOMER = "customer";
    private static final String DEADLINE = "deadline";
    private static final String METERAGE = "meterage";
    private static final String MATERIAL = "material";
    private static final String TEXTURE = "texture";
    private static final String CARD_NUMBER = "cardNumber";
    private static final String LIST_TOTAL_COUNT = "listTotalCount";
    private static final String LIST_FORMAT = "listFormat";
    private static final String CUTTING_MAP_IMAGE = "cuttingMapImage";
    private static final String SUB_REPORT_DATASOURCE = "subReportDatasource";

    private CuttedReportData cuttedReportData;

    public CuttingReportDataCreator(CuttedReportData cuttedReportData, MainFacade mainFacade)
    {
        super(new DefaultReportDataCreator(REPORT_BUNDLES_PATH), mainFacade);
        this.cuttedReportData = cuttedReportData;
    }

    public JReportData create()
    {
        try
        {
            JasperReport subReport = (JasperReport) JRLoader.loadObject(CuttingReportDataCreator.class.getResource(JASPER_SUBREPORT_PATH));

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("detailsTableReport", subReport);
            parameters.put(JRParameter.REPORT_LOCALE, new Locale("ru"));
            parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, getResourceBundle());
            parameters.put(JRParameter.REPORT_CONTEXT, Constants.DUMMY_REPORT_CONTEXT);


            URL definitionPath = CuttingReportDataCreator.class.getResource(JASPER_REPORT_PATH);
            return new JReportDataImpl(new JRMapCollectionDataSource(createMasterDataMap(cuttedReportData)), parameters, definitionPath, getResourceBundle(), getLocale());
        }
        catch (JRException e)
        {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
            return JReportData.UNKNOWN;
        }
    }

    private Collection<Map<String, ?>> createMasterDataMap(CuttedReportData cuttedReportData)
    {
        List<Map<String, ?>> masterDataList = new ArrayList<Map<String, ?>>();
        List<MaterialCuttedData> materials = cuttedReportData.getMaterials();

        for (int cardNumber = 0; cardNumber < materials.size(); cardNumber++)
        {
            MaterialCuttedData materialCuttedData = materials.get(cardNumber);
            List<CuttedSheetData> cuttedSheets = materialCuttedData.getCuttedSheets();
            int sheetsTotal = cuttedSheets.size();
            for (CuttedSheetData cuttedSheet : cuttedSheets)
            {
                Map sheetData = createSheetDataMap(cuttedReportData.getOrder(), materialCuttedData, cuttedSheet, cardNumber + 1, sheetsTotal);
                masterDataList.add(sheetData);
            }
        }
        return masterDataList;
    }

    private Map createSheetDataMap(AOrder order, MaterialCuttedData materialCuttedData, CuttedSheetData sheetData, int cardNumber, int listNumber)
    {
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(ORDER_NUMBER, FacadeContext.getOrderFacadeBy(order.getClass()).parseOrderNumber(order));
        parameters.put(CUSTOMER, order.getCustomer().getName());
        parameters.put(DEADLINE, ReportUtils.getReadyDateForReport(order.getReadyDate()));
        parameters.put(METERAGE, calcMeterage(sheetData));
        parameters.put(MATERIAL, materialCuttedData.getTextureFurniturePair().getBoardDef().getName());
        parameters.put(TEXTURE, ReportUtils.formatTexture(materialCuttedData.getTextureFurniturePair().getTexture()));
        parameters.put(CARD_NUMBER, cardNumber);
        parameters.put(LIST_TOTAL_COUNT, listNumber);
        parameters.put(LIST_FORMAT, formatListFormat(sheetData.getSheetSegment()));
        parameters.put(CUTTING_MAP_IMAGE, sheetData.getCuttedImage());
        parameters.put(SUB_REPORT_DATASOURCE, new JRBeanCollectionDataSource(createDetailsMapData(sheetData.getFurnitures())));
        return parameters;
    }

    private List<CardDetailsData> createDetailsMapData(List<AOrderBoardDetail> details)
    {
        ListConverter<AOrderBoardDetail, CardDetailsData> converter = new ListConverter<AOrderBoardDetail, CardDetailsData>(new CuttingReportConverter(getResourceBundle()));
        List<CardDetailsData> cardDetailsDatas = converter.convert(Collections.unmodifiableList(details));
        return cardDetailsDatas;
    }

    private String calcMeterage(CuttedSheetData sheetData)
    {
        double value = GetSawLength.valueOf(sheetData).get();
        value = ReportUtils.calcLinear(value);
        value = MathUtils.round(value, 2);
        return Double.toString(value);
    }

    private String formatListFormat(Segment segment)
    {
        return MessageFormat.format(LIST_FORMAT_PATTERN, segment.getMaterialLength().toString(), segment.getMaterialWidth().toString());
    }
}