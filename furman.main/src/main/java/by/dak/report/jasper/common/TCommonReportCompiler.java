package by.dak.report.jasper.common;

import by.dak.report.jasper.TAbstractReportTester;

/**
 * User: akoyro
 * Date: 05.11.2009
 * Time: 10:23:44
 */
public class TCommonReportCompiler extends TAbstractReportTester
{
    public static void main(String[] args)
    {
        try
        {
            compile(TCommonReportTester.class.getResource("CommonReport.jrxml").getFile(), null);
            compile(TCommonReportTester.class.getResource("CommonReportMaterialSubreport.jrxml").getFile(), null);
            compile(TCommonReportTester.class.getResource("CommonSubreportRailing.jrxml").getFile(), null);
            compile(TCommonReportTester.class.getResource("CommonSubreportSellotape.jrxml").getFile(), null);
            compile(TCommonReportTester.class.getResource("CommonSubreportSheet.jrxml").getFile(), null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
