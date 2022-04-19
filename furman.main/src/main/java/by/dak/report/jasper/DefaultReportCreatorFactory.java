package by.dak.report.jasper;

import by.dak.cutting.agt.report.AGTFacadeReportDataCreator;
import by.dak.cutting.zfacade.report.ZFacadeReportDataCreator;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.report.ReportType;
import by.dak.report.jasper.common.CommonReportDataCreator;
import by.dak.report.jasper.common.DialerCommonReportDataCreator;
import by.dak.report.jasper.common.data.CommonReportData;
import by.dak.report.jasper.common.data.CommonReportDataImpl;
import by.dak.report.jasper.cutoff.CutoffReportDataCreator;
import by.dak.report.jasper.cutting.CuttingReportDataCreator;
import by.dak.report.jasper.cutting.data.CuttedReportData;
import by.dak.report.jasper.glueing.GlueingReportDataCreator;
import by.dak.report.jasper.milling.MillingReportDataCreator;
import by.dak.report.jasper.store.StoreReportDataCreator;

/**
 * @author Denis Koyro
 * @version 0.1 15.03.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public final class DefaultReportCreatorFactory implements ReportCreatorFactory
{
    private static DefaultReportCreatorFactory factoryInstance;
    private final MainFacade mainFacade;

    public DefaultReportCreatorFactory(MainFacade mainFacade)
    {
        this.mainFacade = mainFacade;
    }

    public ReportDataCreator createReportDataCreator(Object reportObject, ReportType reportType)
    {
        switch (reportType)
        {
            case glueing:
                return new GlueingReportDataCreator((AOrder) reportObject);
            case common:
                CommonReportDataImpl commonReportData = (CommonReportDataImpl) reportObject;
                return new DialerCommonReportDataCreator(
                        commonReportData);
            case production_common:
                return new CommonReportDataCreator((CommonReportData) reportObject);
            case cutting:
            case cutting_dsp_plastic:
                return new CuttingReportDataCreator((CuttedReportData) reportObject);
            case cutoff:
                return new CutoffReportDataCreator((AOrder) reportObject);
            case milling:
                return new MillingReportDataCreator((AOrder) reportObject);
            case store:
                return reportObject instanceof AOrder ? new StoreReportDataCreator((AOrder) reportObject, mainFacade) :
                        new StoreReportDataCreator((CommonReportData) reportObject, mainFacade);
            case zfacade:
                return new ZFacadeReportDataCreator((AOrder) reportObject);
            case agtfacade:
                return new AGTFacadeReportDataCreator((AOrder) reportObject);
            default:
                assert false : "Unknown report type is specified!";
                return ReportDataCreator.UNKNOWN;
        }
    }
}

