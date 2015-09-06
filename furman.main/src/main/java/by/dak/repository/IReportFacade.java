package by.dak.repository;

import by.dak.report.ReportType;

/**
 * @author dkoyro
 * @version 0.1 12.03.2009 11:10:30
 */
public interface IReportFacade<O, R>
{
    /**
     * Save report object to repository.
     * If report object already exists by the sorreponding path, then it will be removed and created new.
     *
     * @param order      is an order object, which report was generated for
     * @param reportType is the type of report
     * @param report     is a report object
     */
    void saveReport(O order, ReportType reportType, R report);

    /**
     * Load report object from repository
     *
     * @param order      is an order object, which report should be loaded for
     * @param reportType is the type of report
     * @return the required report object
     */
    R loadReport(O order, ReportType reportType);

    /**
     * Remote report object from repository
     *
     * @param order      is an order object, which report should be removed for
     * @param reportType is the type of report
     */
    void removeReport(O order, ReportType reportType);


    void removeAll(O order);
}
