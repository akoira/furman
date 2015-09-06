package by.dak.report.jasper;

import by.dak.cutting.agt.report.AGTFacadeReportDataCreator;
import by.dak.cutting.zfacade.report.ZFacadeReportDataCreator;
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
public class DefaultReportCreatorFactory<O> implements ReportCreatorFactory<O>
{
    private static DefaultReportCreatorFactory factoryInstance;

    public synchronized static <P> DefaultReportCreatorFactory<P> getInstance()
    {
        if (factoryInstance == null)
        {
            factoryInstance = new DefaultReportCreatorFactory<P>();
        }
        return factoryInstance;
    }

    private DefaultReportCreatorFactory()
    {
    }

    public ReportDataCreator createReportDataCreator(O reportObject, ReportType reportType)
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
                return new CuttingReportDataCreator((CuttedReportData) reportObject);
            case cutoff:
                return new CutoffReportDataCreator((AOrder) reportObject);
            case milling:
                return new MillingReportDataCreator((AOrder) reportObject);
            case store:
                return reportObject instanceof AOrder ? new StoreReportDataCreator((AOrder) reportObject) : new StoreReportDataCreator((CommonReportData) reportObject);
            case zfacade:
                return new ZFacadeReportDataCreator((AOrder) reportObject);
            case agtfacade:
                return new AGTFacadeReportDataCreator((AOrder) reportObject);
            case cutting_dsp_plastic:
                return new CuttingReportDataCreator((CuttedReportData) reportObject);
            default:
                assert false : "Unknown report type is specified!";
                return ReportDataCreator.UNKNOWN;
        }
    }
}

