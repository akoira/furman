package by.dak.template.swing.tree;

import by.dak.category.Category;
import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.persistence.FacadeContext;
import by.dak.report.model.impl.ReportModelCreator;
import by.dak.report.model.impl.ReportsModelImpl;
import by.dak.template.TemplateOrder;
import org.jdesktop.application.Action;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * User: akoyro
 * Date: 21.03.11
 * Time: 19:15
 */
public class RootNode extends ACategoryNode
{
    private static final String[] ACTIONS = new String[]{"create", "recalculate", "print", "deleteAll"};

    @Override
    public void init()
    {
        setAllowsChildren(true);
        super.init();
        setUserObject(getResourceMap().getString("node.name"));
    }

    @Override
    protected void initChildren()
    {
    }

    @Override
    public String[] getActions()
    {
        return ACTIONS;
    }

    @Action
    public void create()
    {
        Category category = new Category();
        category.setName(getResourceMap().getString("category.default.name"));
        FacadeContext.getCategoryFacade().save(category);

        if (getTree() != null)
        {
            add(CategoryNode.valueOf(category, getContext()));
            ((DefaultTreeModel) getTree().getModel()).reload(this);
        }
    }

    @Action
    public void deleteAll()
    {
        SwingWorker worker = new SwingWorker()
        {
            @Override
            protected Object doInBackground() throws Exception
            {
                deleteCategories();

                List<TemplateOrder> orders = FacadeContext.getTemplateOrderFacade().loadAll();
                for (TemplateOrder templateOrder : orders)
                {
                    try
                    {
                        FacadeContext.getTemplateOrderFacade().delete(templateOrder);
                    }
                    catch (Throwable e)
                    {
                        FacadeContext.getExceptionHandler().handle(e);
                    }
                }

                return null;
            }

            private void deleteCategories()
            {
                try
                {
                    List<Category> categories = FacadeContext.getCategoryFacade().loadAll();
                    for (Category category : categories)
                    {
                        FacadeContext.getTemplateOrderFacade().removeFrom(category);
                        FacadeContext.getCategoryFacade().delete(category);
                    }
                }
                catch (Throwable e)
                {
                    FacadeContext.getExceptionHandler().handle(e);
                }
            }
        };
        DialogShowers.showWaitDialog(worker, getTree());
        ((DefaultTreeModel) getTree().getModel()).reload(this);
    }


    @Action
    public void recalculate()
    {
        SwingWorker swingWorker = new SwingWorker()
        {
            @Override
            protected Object doInBackground() throws Exception
            {
                List<TemplateOrder> orders = FacadeContext.getTemplateOrderFacade().loadAll();

                ExecutorService executorService = Executors.newFixedThreadPool(10);

                for (final TemplateOrder templateOrder : orders)
                {
                    Callable<ReportsModelImpl> callable = new Callable<ReportsModelImpl>()
                    {
                        public ReportsModelImpl call() throws Exception
                        {
                            CuttingModel cuttingModel = FacadeContext.getStripsFacade().loadCuttingModel(templateOrder).load();
                            ReportModelCreator reportModelCreator = new ReportModelCreator(cuttingModel.getOrder(), cuttingModel);
                            return reportModelCreator.create();
                        }
                    };
                    executorService.submit(callable);
                }

                executorService.shutdown();

                try
                {
                    while (!executorService.awaitTermination(10, TimeUnit.SECONDS)) ;
                }
                catch (InterruptedException e)
                {
                }
                return null;
            }
        };
        DialogShowers.showWaitDialog(swingWorker, getTree());
        ((DefaultTreeModel) getTree().getModel()).reload(this);
    }

}

