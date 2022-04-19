package by.dak.report.jasper.store;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FinderException;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.*;
import by.dak.report.ReportType;
import by.dak.report.jasper.DefaultReportCreatorFactory;
import by.dak.report.jasper.JReportData;
import by.dak.report.jasper.TAbstractReportTester;
import by.dak.report.jasper.milling.TMilllingReportTester;

import java.sql.Date;

/**
 * @author Denis Koyro
 * @version 0.1 13.01.2009
 * @since 1.0.0
 */
public class TStoreReportTester extends TAbstractReportTester {
    public TStoreReportTester() {
        REPORT_TYPE = ReportType.store;
    }

    public static void main(String[] args) {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        MainFacade mainFacade = springConfiguration.getMainFacade();
        new TStoreReportTester();
        try {
            if (args.length > 0 && args[0].equals("compile")) {
                String jasperPath = args.length == 2 ? args[1] : null;
                compile(TStoreReportTester.class.getResource("StoreReport.jrxml").getFile(), jasperPath);
            } else {
                compile(TStoreReportTester.class.getResource("StoreReport.jrxml").getFile(), null);
                process(TMilllingReportTester.createReportData(mainFacade));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
