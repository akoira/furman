/*
 * To change this template, choose Tools | Templates and open the template in the draw.
 */
package by.dak.cutting;

import by.dak.cutting.def.swing.tree.TypesRootNode;
import by.dak.cutting.doors.profile.def.swing.ProfileDefDialog;
import by.dak.cutting.swing.DictionaryEditType;
import by.dak.cutting.swing.ItemSelector;
import by.dak.cutting.swing.dictionaries.delivery.DeliveryController;
import by.dak.cutting.swing.dictionaries.delivery.DeliveryModel;
import by.dak.cutting.swing.dictionaries.material.MaterialController;
import by.dak.cutting.swing.dictionaries.service.ServiceWizardController;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.MillingEditorDialog;
import by.dak.cutting.swing.order.tree.OrderRootNode;
import by.dak.cutting.swing.order.wizard.OrderWizard;
import by.dak.cutting.swing.store.modules.*;
import by.dak.cutting.swing.store.tree.StoreRootNode;
import by.dak.ordergroup.OrderGroup;
import by.dak.ordergroup.swing.wizard.OrderGroupWizardController;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.report.PriceReportType;
import by.dak.report.ReportProperties;
import by.dak.report.swing.ReportPropertiesTab;
import by.dak.swing.IDefaultButtonProvider;
import by.dak.swing.IWindowAware;
import by.dak.swing.Titled;
import by.dak.swing.WindowCallback;
import by.dak.swing.explorer.ExplorerPanel;
import by.dak.swing.tree.ATreeNode;
import by.dak.swing.wizard.DWizardController;
import by.dak.swing.wizard.WizardDisplayerHelper;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.base.JRBaseParameter;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import nl.jj.swingx.gui.modal.JModalFrame;
import org.apache.commons.io.IOUtils;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.netbeans.spi.wizard.WizardObserver;
import org.netbeans.spi.wizard.WizardPanelProvider;

import javax.swing.FocusManager;
import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static by.dak.report.jasper.Constants.MAIN_FACADE;


/**
 * @author admin
 */
public class DialogShowers
{
    public static DialogShower<MillingEditorDialog> getMillingEditorDialogShower(final MillingEditorDialog millingEditorDialog)
    {
        DialogShower<MillingEditorDialog> dialogShower = new DialogShower<MillingEditorDialog>(CuttingApp.getApplication().getMainFrame())
        {
            @Override
            protected MillingEditorDialog createDialog()
            {
                return millingEditorDialog;
            }

            @Override
            protected void postCreatingSetting()
            {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        return dialogShower;
    }


    public static void showWizard(DWizardController controller)
    {
        WizardDisplayerHelper helper = new WizardDisplayerHelper(controller);
        helper.showWizard();
    }

    public static Object showWizard(WizardPanelProvider wizardPanelProvider, WizardObserver wizardObserver)
    {
        WizardDisplayerHelper helper = new WizardDisplayerHelper(wizardPanelProvider, wizardObserver);
        return helper.showWizard();
    }

    public static <E> AEntityEditorPanel getPanelBy(E entity)
    {
        AEntityEditorPanel panel;

        if (entity instanceof Furniture)
        {
            panel = new FurniturePanel();
        }
        else if (entity instanceof Border)
        {
            panel = new BorderPanel();
        }
        else if (entity instanceof Board)
        {
            panel = new BoardPanel();
        }
        else if (entity instanceof DepartmentEntity)
        {
            panel = new MDepartmentPanel();
        }
        else
        {
            throw new IllegalArgumentException();
        }
        panel.setValue(entity);
        return panel;
    }


    public static DialogShower<ProfileDefDialog> getProfileDefDialogShower()
    {
        DialogShower<ProfileDefDialog> dialogShower = new DialogShower<ProfileDefDialog>(CuttingApp.getApplication().getMainFrame())
        {

            @Override
            protected ProfileDefDialog createDialog()
            {
                return new ProfileDefDialog(DictionaryEditType.FULL);
            }

            @Override
            protected void postCreatingSetting()
            {
                getDialog().setList(FacadeContext.getProfileDefFacade().loadAll());
            }
        };
        return dialogShower;
    }


    public static DWizardController getWizardControllerBy(Object entity)
    {
        if (entity instanceof PriceAware)
        {
            return new MaterialController((PriceAware) entity);
        }
        else if (entity instanceof Service)
        {
            return new ServiceWizardController((Service) entity);
        }
        else if (entity instanceof Delivery)
        {
            return new DeliveryController();
        }
        else if (entity instanceof OrderGroup)
        {
            OrderGroupWizardController controller = new OrderGroupWizardController();
            controller.setModel((OrderGroup) entity);
            return controller;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public static DWizardController getWizardControllerBy(Delivery entity, boolean editable, StoreElementStatus status)
    {
        DeliveryModel model = new DeliveryModel();
        model.setDelivery(entity);
        model.setEditable(editable);
        model.setStatus(status);

        DeliveryController controller = new DeliveryController();
        controller.setModel(model);
        return controller;
    }

    public static void showOrderExplorer()
    {
        showExplorerFrame(new OrderRootNode());
    }

    public static void showStoreExplorer()
    {
        showExplorerFrame(new StoreRootNode());
    }

    private static void showExplorerFrame(ATreeNode aTreeNode)
    {
        String name = aTreeNode.getClass().getSimpleName();
        ExplorerPanel panel = new ExplorerPanel();
        DefaultTreeModel treeModel = new DefaultTreeModel(aTreeNode);
        panel.getTreePanel().getTree().setModel(treeModel);
        JModalFrame modalFrame = new JModalFrame(CuttingApp.getApplication().getMainFrame(), false);
        modalFrame.setContentPane(panel);
        modalFrame.setName(name);
        modalFrame.setTitle(aTreeNode.getResourceMap().getString("title"));
        modalFrame.pack();
        CuttingApp.getApplication().show(modalFrame);
    }

    public static void showTypesExplorer()
    {
        showExplorerFrame(new TypesRootNode());
    }

    public static void showBy(JPanel panel, boolean modal)
    {
        showBy(panel, (Component) null, modal);
    }

    public static void showBy(JPanel panel, WindowCallback callback, boolean modal)
    {
        showBy(panel, null, callback, modal);
    }

    public static void showBy(JPanel panel, Component relatedComponent, boolean modal, String title)
    {
        showBy(panel, relatedComponent, null, modal, title);
    }

    public static void showBy(JPanel panel, Component relatedComponent, boolean modal)
    {
        showBy(panel, relatedComponent, null, modal);
    }

    public static void showBy(JPanel panel, Component relatedComponent, WindowCallback callback, boolean modal)
    {
        showBy(panel, relatedComponent, callback, modal, null);
    }

    public static void showBy(JPanel panel, Component relatedComponent, WindowCallback callback, boolean modal, boolean undecorated)
    {
        showBy(panel, relatedComponent, callback, modal, null, undecorated);
    }

    public static void showBy(JPanel panel, Component relatedComponent, WindowCallback callback, boolean modal, String title, boolean undecorated)
    {
        Window owner = null;
        if (relatedComponent != null)
        {
            owner = SwingUtilities.getWindowAncestor(relatedComponent);
        }
        showBy(panel, owner, callback, modal, title, undecorated);

    }

    public static void showBy(JPanel panel, Component relatedComponent, WindowCallback callback, boolean modal, String title)
    {
        showBy(panel, relatedComponent, callback, modal, title, false);
    }

    public static JFrame getMainFrame()
    {
        return Application.getInstance() instanceof SingleFrameApplication ? ((SingleFrameApplication) Application.getInstance()).getMainFrame() : null;
    }


    public static void showBy(JPanel panel, Window owner, WindowCallback callback, boolean modal, String title, boolean undecorated)
    {
        final JModalFrame modalFrame = new JModalFrame(owner == null ? getMainFrame() : owner, modal);
        if (callback != null)
        {
            callback.setWindow(modalFrame);
        }
        modalFrame.setContentPane(panel);
        modalFrame.setUndecorated(undecorated);
        modalFrame.setName(panel.getName() == null ? panel.getClass().getSimpleName() : panel.getName());
        if (panel instanceof Titled)
        {
            modalFrame.setTitle(((Titled) panel).getTitle());
            ImageIcon imageIcon = ((Titled) panel).getIcon();
            modalFrame.setIconImage(imageIcon != null ? imageIcon.getImage() : null);
        }
        if (title != null)
        {
            modalFrame.setTitle(title);
        }
        modalFrame.pack();
        if (panel instanceof IDefaultButtonProvider)
        {
            modalFrame.getRootPane().setDefaultButton(((IDefaultButtonProvider) panel).getDefaultButton());
        }
        if (panel instanceof IWindowAware)
        {
            ((IWindowAware) panel).setWindow(modalFrame);
        }


        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                ((SingleFrameApplication) Application.getInstance()).show(modalFrame);
                modalFrame.toFront();
            }
        };
        SwingUtilities.invokeLater(runnable);
    }


    public static void showJasperViewer(String name, String reportKey,
                                        ResourceMap resourceMap, String reportPath)
    {
        showJasperViewer(name, reportKey,
                resourceMap, reportPath, null);
    }


    public static void showJasperViewer(final String name, final String reportKey,
                                        final ResourceMap resourceMap, final String reportPath,
                                        final Map<String, Object> parameters)
    {
        showJasperViewer(null, name, reportKey, resourceMap, reportPath, parameters);
    }


    public static void showJasperViewer(final Component relatedComponent, final String name, final String reportKey,
                                        final ResourceMap resourceMap, final String reportPath,
                                        final Map<String, Object> parameters)
    {
        //"by/dak/cutting/def/report/owner.price.jrxml"
        final InputStream[] stream = {null};
        final JasperPrint[] jasperPrint = {null};
        SwingWorker swingWorker = new SwingWorker()
        {
            @Override
            protected Object doInBackground() throws Exception
            {
                AtomicReference<Runnable> runnable = new AtomicReference<Runnable>(() -> {
                    try
                    {
                        stream[0] = DialogShowers.class.getClassLoader().getResourceAsStream(reportPath);
                        JasperDesign jasperDesign = JRXmlLoader.load(stream[0]);
                        JRDesignParameter mainFacade = new JRDesignParameter();
                        mainFacade.setName(MAIN_FACADE);
                        mainFacade.setValueClass(MainFacade.class);
                        jasperDesign.addParameter(mainFacade);

                        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                        Connection jdbcConnection = ((MainFacade) parameters.get(MAIN_FACADE)).getJDBCConnection();
                        jasperPrint[0] = JasperFillManager.fillReport(jasperReport,parameters, jdbcConnection);
                    }
                    catch (Exception e)
                    {
                        CuttingApp.getApplication().getExceptionHandler().handle(e);
                    }
                    finally
                    {
                        if (stream[0] != null)
                            IOUtils.closeQuietly(stream[0]);
                    }

                    if (jasperPrint[0] != null)
                    {
                        JRViewer jrViewer = new JRViewer(jasperPrint[0]);
                        jrViewer.setName(name);
                        showBy(jrViewer, relatedComponent, false, resourceMap.getString(reportKey + ".title"));
                    }
                });
                SwingUtilities.invokeLater(runnable.get());
                return null;
            }
        };
        showWaitDialog(swingWorker, relatedComponent != null ? relatedComponent : CuttingApp.getApplication().getMainFrame());
    }

    public static void showStatisticsJasperViewer(by.dak.persistence.entities.predefined.MaterialType type, Date start, Date end)
    {
        HashMap map = new HashMap();
        map.put("START", start);
        map.put("END", end);
        ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(by.dak.cutting.def.report.Constants.class);
        String reportPath = "by/dak/cutting/statistics/report/" + "statistics." + type.name() + by.dak.cutting.store.report.Constants.REPORT_FILE_SUFFIX;
        showJasperViewer("StatisticsJasperViewer", type.name(), resourceMap, reportPath, map);
    }


    public static void showPriceJasperViewer(final String type, MainFacade mainFacade)
    {
        final ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(by.dak.cutting.def.report.Constants.class);

        ReportPropertiesTab valueTab = new ReportPropertiesTab();
        valueTab.init();
        valueTab.setValue(new ReportProperties());
        final ItemSelector itemSelector = (ItemSelector) valueTab.getEditors().get(ReportProperties.PROPERTY_priceReportType);
        final WindowCallback windowCallback = new WindowCallback();
        itemSelector.getComboBoxItem().addActionListener(e -> {
            windowCallback.dispose();
            Map<String, Object> properties = new HashMap<>();
            properties.put(MAIN_FACADE, mainFacade);

            String priceType = ((PriceReportType) itemSelector.getComboBoxItem().getSelectedItem()).name();
            String reportPath = "by/dak/cutting/def/report/" + type + "/" +
                    ((PriceReportType) itemSelector.getComboBoxItem().getSelectedItem()).name() + by.dak.cutting.def.report.Constants.REPORT_PRICE_FILE_SUFFIX;
            showJasperViewer(type + "." + priceType + ".price", type + "." + priceType + ".price", resourceMap, reportPath, properties);
        });
        DialogShowers.showBy(valueTab, null, windowCallback, true);
    }

    public static void showPriceService()
    {
        String reportKey = "service";
        ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(by.dak.cutting.def.report.Constants.class);
        String reportPath = "by/dak/cutting/def/report/" + reportKey + by.dak.cutting.def.report.Constants.REPORT_PRICE_FILE_SUFFIX;
        showJasperViewer("PriceJasperViewer", reportKey, resourceMap, reportPath, null);
    }

    public static void showOrderJasperViewer(by.dak.persistence.entities.predefined.MaterialType type)
    {
        ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(by.dak.cutting.store.report.Constants.class);
        String reportPath = "by/dak/cutting/store/report/" + by.dak.cutting.store.report.Constants.REPORT_FILE_PREFIX + type.name() + by.dak.cutting.store.report.Constants.REPORT_FILE_SUFFIX;
        showJasperViewer("OrderJasperViewer", type.name(), resourceMap, reportPath);
    }

    public static void showWaitDialog(SwingWorker swingWorker, Component relatedComponent)
    {
        FocusManager.getCurrentManager().getActiveWindow();
        Window window = SwingUtilities.getWindowAncestor(relatedComponent);
        if (window == null)
            window = FocusManager.getCurrentManager().getActiveWindow();
        final JModalFrame modal = new JModalFrame(window, "", true);
        modal.setUndecorated(true);
        modal.getContentPane().setLayout(new BorderLayout());
        modal.setLocationRelativeTo(window);

        JProgressBar jProgressBar = new JProgressBar();
        jProgressBar.setMinimum(0);
        jProgressBar.setMaximum(100);
        jProgressBar.setIndeterminate(true);
        modal.getContentPane().add(jProgressBar, BorderLayout.CENTER);
        modal.pack();
        swingWorker.addPropertyChangeListener(new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                if (evt.getPropertyName().equals("state") && evt.getNewValue() == SwingWorker.StateValue.DONE)
                {
                    modal.dispose();
                }
            }
        });
        swingWorker.execute();
        modal.setVisible(true);
        modal.waitForClose();
    }

    public static void showOrderWizardBy(Long id)
    {
        Order order = FacadeContext.getOrderFacade().findBy(id);
        final OrderWizard orderWizard = new OrderWizard(order.getNumber().getStringValue());
        orderWizard.setOrder(order);
        DialogShowers.showWizard(orderWizard, orderWizard.getWizardObserver());
    }

}
