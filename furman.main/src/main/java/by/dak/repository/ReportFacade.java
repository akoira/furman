package by.dak.repository;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.AOrder;
import by.dak.report.ReportType;
import by.dak.utils.CollectionUtils;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author dkoyro
 * @version 0.1 12.03.2009 11:08:15
 */
public class ReportFacade implements IReportFacade<AOrder, JasperPrint>
{
    private final static Log LOGGER = LogFactory.getLog(ReportFacade.class);

    private ExecutorService service = Executors.newCachedThreadPool();
    private IRepositoryService repositoryService;


    public void saveReport(final AOrder order, final ReportType reportType, final JasperPrint report)
    {
        String uuid = createUUID(order, reportType);
        getRepositoryService().delete(uuid);

        File reportFile = createTmpFile(report, uuid);
        getRepositoryService().store(reportFile, uuid);
    }

    private File createTmpFile(JasperPrint report, String uuid)
    {
        try
        {
            File file = File.createTempFile(uuid, uuid);
            JRSaver.saveObject(report, file);
            return file;
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException();
        }
    }

    public JasperPrint loadReport(final AOrder order, final ReportType reportType)
    {
        try
        {
            String uuid = createUUID(order, reportType);
            if (getRepositoryService().exist(uuid))
            {
                InputStream inputStream = getRepositoryService().read(uuid);
                return (JasperPrint) JRLoader.loadObject(inputStream);
            }
            else
            {
                return null;
            }
        }
        catch (Throwable e)
        {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public void removeReport(final AOrder order, final ReportType reportType)
    {
        String uuid = createUUID(order, reportType);
        repositoryService.delete(uuid);
    }

    private String createUUID(AOrder order, ReportType reportType)
    {
        return String.format("%s_%s", order.getId(), reportType.name());
    }

    @Override
    public void removeAll(final AOrder order)
    {
        ReportType[] reportTypes = ReportType.values();
        ArrayList<Future> futures = new ArrayList<Future>();
        for (final ReportType reportType : reportTypes)
        {
            Runnable runnable = new Runnable()
            {
                @Override
                public void run()
                {
                    removeReport(order, reportType);
                }
            };
            futures.add(service.submit(runnable));
        }
        CollectionUtils.waitWhileAllDone(futures);
        FacadeContext.getCommonDataFacade().deleteAll(order);
    }

    public IRepositoryService getRepositoryService()
    {
        return repositoryService;
    }

    public void setRepositoryService(IRepositoryService repositoryService)
    {
        this.repositoryService = repositoryService;
    }
}
