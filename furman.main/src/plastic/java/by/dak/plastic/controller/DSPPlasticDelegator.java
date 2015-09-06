package by.dak.plastic.controller;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.SearchFilter;
import by.dak.cutting.swing.CutterPanel;
import by.dak.cutting.swing.CuttersPanel;
import by.dak.cutting.swing.MainCuttingPanel;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.AOrderBoardDetail;
import by.dak.persistence.entities.OrderItem;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.plastic.process.CuttingModel2DSPPlasticDetailsProcess;
import by.dak.plastic.swing.DSPPlasticValue;
import by.dak.plastic.swing.PlasticTextureBoardDefTab;
import by.dak.swing.TabIterator;
import by.dak.swing.WindowCallback;
import org.jhotdraw.draw.event.FigureSelectionEvent;
import org.jhotdraw.draw.event.FigureSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 25.09.11
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class DSPPlasticDelegator
{
    private CommonCuttingFinishedSuccessfully commonCuttingFinishedSuccessfully = new CommonCuttingFinishedSuccessfully();
    private CommonCuttingStarted commonCuttingStarted = new CommonCuttingStarted();

    private PlasticCuttingFinishedSuccessfully plasticCuttingFinishedSuccessfully = new PlasticCuttingFinishedSuccessfully();
    private PlasticCuttingStarted plasticCuttingStarted = new PlasticCuttingStarted();

    private CuttingModelChangedListener cuttingModelChangedListener = new CuttingModelChangedListener();
    private MainCuttingPanel mainCuttingPanel;

    private DSPPlasticValue plasticValue;

    public static final String PLASTIC_ORDER_ITEM_NAME = "plasticOrderItem";
    private CuttingDrawingViewHandler cuttingDrawingViewHandler = new CuttingDrawingViewHandler();


    public boolean hasPlasticDetails()
    {
        return FacadeContext.getDSPPlasticDetailFacade().hasPlasticDetails(getCommonCuttingModel().getOrder());
    }

    private boolean validateCutting()
    {
        return mainCuttingPanel.getDspPlasticCuttersPanel() != null && mainCuttingPanel.getDspPlasticCuttersPanel().validateModel();
    }

    private boolean isCuttingLoaded()
    {
        return mainCuttingPanel.getDspPlasticCuttersPanel() != null && mainCuttingPanel.getDspPlasticCuttersPanel().getCuttingModel().isStripsLoaded() && mainCuttingPanel.getDspPlasticCuttersPanel().getCuttingModel().getPairs().size() > 0;
    }

    public boolean validate()
    {
        return hasPlasticDetails() ? validateCutting() : true;
    }

    public CuttingModel getCommonCuttingModel()
    {
        return mainCuttingPanel.getCommonCuttersPanel().getCuttingModel();
    }

    public void generate()
    {
        AOrder order = getCommonCuttingModel().getOrder();
        createPlasticDatails(order);

        addCuttingDrawingViewHandler();

        CuttersPanel dspPlasticCuttersPanel = createDspPlasticPanel();
        CuttingModel dspPlasticCuttingModel = loadDSPPlasticCuttingModel();
        dspPlasticCuttersPanel.setCuttingModel(dspPlasticCuttingModel);
        dspPlasticCuttersPanel.setOrderBoardDetailFacade(FacadeContext.getDSPPlasticDetailFacade());
        dspPlasticCuttersPanel.setStripsFacade(FacadeContext.getDSPPlasticStripsFacade());

        mainCuttingPanel.setDspPlasticCuttersPanel(dspPlasticCuttersPanel);
        dspPlasticCuttersPanel.generate();
        mainCuttingPanel.setSelectedComponent(dspPlasticCuttersPanel);
        mainCuttingPanel.getCommonCuttersPanel().setEditable(false);
    }

    private CuttersPanel createDspPlasticPanel()
    {
        CuttersPanel dspPlasticCuttersPanel = new CuttersPanel();

        dspPlasticCuttersPanel.addPropertyChangeListener(CuttersPanel.PROPERTY_cuttingFinishedSuccessfully, plasticCuttingFinishedSuccessfully);
        dspPlasticCuttersPanel.addPropertyChangeListener(CuttersPanel.PROPERTY_cuttingStarted, plasticCuttingStarted);

        dspPlasticCuttersPanel.setEditable(mainCuttingPanel.isEditable());
        return dspPlasticCuttersPanel;
    }

    private CuttingModel loadDSPPlasticCuttingModel()
    {
        SwingWorker<CuttingModel, CuttingModel> swingWorker = new SwingWorker<CuttingModel, CuttingModel>()
        {
            @Override
            protected CuttingModel doInBackground() throws Exception
            {
                CuttingModel cuttingModel = FacadeContext.getDSPPlasticStripsFacade().loadCuttingModel(mainCuttingPanel.
                        getCommonCuttersPanel().getCuttingModel().getOrder()).load();
                return cuttingModel;
            }
        };

        DialogShowers.showWaitDialog(swingWorker, getMainCuttingPanel());
        CuttingModel dspPlasticCuttingModel;
        try
        {
            dspPlasticCuttingModel = swingWorker.get();
        }
        catch (Throwable e)
        {
            throw new IllegalArgumentException(e);
        }

        return dspPlasticCuttingModel;
    }

    private List<? extends AOrderBoardDetail> createPlasticDatails(AOrder order)
    {
        OrderItem plasticOrderItem = createPlasticOrderItem(order);

        CuttingModel2DSPPlasticDetailsProcess processor = new CuttingModel2DSPPlasticDetailsProcess();
        processor.setOrderItem(plasticOrderItem);
        processor.setPlasticValue(plasticValue);
        processor.setCuttingModel(getCommonCuttingModel());
        processor.process();
        return processor.getResult();
    }

    //todo надо чтобы закончился раскрой. Наверно лучше сделать не editable commonPanel, пока краится ДСП пластика

    /**
     * если процесс раскроя начался а в общем раскрое меняется что-то, сначала надо остановить раскрой
     */
    private void stopDspPlasticCuttingProcess()
    {
        if (mainCuttingPanel.getDspPlasticCuttersPanel() != null)
        {
            if (mainCuttingPanel.getDspPlasticCuttersPanel().getState() != CuttersPanel.State.STOPED)
            {
                mainCuttingPanel.getDspPlasticCuttersPanel().stop();
            }
        }
    }

    private OrderItem createPlasticOrderItem(AOrder order)
    {
        OrderItem plasticOrderItem = new OrderItem();
        plasticOrderItem.setType(OrderItemType.common);
        plasticOrderItem.setName(PLASTIC_ORDER_ITEM_NAME);
        plasticOrderItem.setType(OrderItemType.plastic);
        plasticOrderItem.setOrder(order);
        FacadeContext.getOrderItemFacade().save(plasticOrderItem);

        return plasticOrderItem;
    }

    public void removeAll()
    {
        removeDetails();
        removePlasticDspPanel();
    }

    /**
     * удаление orderItem и деталей
     */
    public void removeDetails()
    {
        SearchFilter searchFilter = SearchFilter.instanceSingle();
        searchFilter.eq(OrderItem.PROPERTY_order, getCommonCuttingModel().getOrder());
        searchFilter.eq(OrderItem.PROPERTY_type, OrderItemType.plastic);
        List<OrderItem> orderItems = FacadeContext.getOrderItemFacade().loadAll(searchFilter);
        for (OrderItem orderItem : orderItems)
        {
            FacadeContext.getDSPPlasticDetailFacade().deleteAllBy(orderItem);
            FacadeContext.getOrderItemFacade().delete(orderItem);
        }
        FacadeContext.getDSPPlasticStripsFacade().deleteAll(getCommonCuttingModel().getOrder());
    }


    public void removePlasticDspPanel()
    {
        getMainCuttingPanel().setDspPlasticCuttersPanel(null);
    }

    public MainCuttingPanel getMainCuttingPanel()
    {
        return mainCuttingPanel;
    }

    public void setMainCuttingPanel(MainCuttingPanel mainCuttingPanel)
    {
        this.mainCuttingPanel = mainCuttingPanel;

        mainCuttingPanel.getCommonCuttersPanel().removePropertyChangeListener(CuttersPanel.PROPERTY_cuttingModel,
                getCuttingModelChangedListener());
        mainCuttingPanel.getCommonCuttersPanel().addPropertyChangeListener(CuttersPanel.PROPERTY_cuttingModel,
                getCuttingModelChangedListener());

        mainCuttingPanel.getCommonCuttersPanel().removePropertyChangeListener(CuttersPanel.PROPERTY_cuttingFinishedSuccessfully, commonCuttingFinishedSuccessfully);
        mainCuttingPanel.getCommonCuttersPanel().addPropertyChangeListener(CuttersPanel.PROPERTY_cuttingFinishedSuccessfully, commonCuttingFinishedSuccessfully);


        mainCuttingPanel.getCommonCuttersPanel().removePropertyChangeListener(CuttersPanel.PROPERTY_cuttingStarted, commonCuttingStarted);
        mainCuttingPanel.getCommonCuttersPanel().addPropertyChangeListener(CuttersPanel.PROPERTY_cuttingStarted, commonCuttingStarted);
    }

    public CuttingModelChangedListener getCuttingModelChangedListener()
    {
        return cuttingModelChangedListener;
    }

    private void addCuttingDrawingViewHandler()
    {
        TabIterator tabIterator = new TabIterator(mainCuttingPanel.getCommonCuttersPanel().getTabbedPaneCutting())
        {
            @Override
            protected Object iterate(Component tab)
            {
                ((CutterPanel) tab).removeCuttingDrawingViewHandler(cuttingDrawingViewHandler);
                ((CutterPanel) tab).addCuttingDrawingViewHandler(cuttingDrawingViewHandler);
                return null;
            }
        };
        tabIterator.iterate();
    }

    public DSPPlasticValue getPlasticValue()
    {
        return plasticValue;
    }

    public void setPlasticValue(DSPPlasticValue plasticValue)
    {
        this.plasticValue = plasticValue;
    }

    /**
     * слушает события на тягание деталей в раскрое
     */
    private class CuttingDrawingViewHandler implements FigureSelectionListener
    {
        @Override
        public void selectionChanged(FigureSelectionEvent evt)
        {
            stopDspPlasticCuttingProcess();
            removeAll();
        }


    }

    /**
     * cuttingModel устанавливается в cuttersPanel, инцилизируются все табы (cutterPanel)
     * и к табам добавляется слушатель на тягание элементов
     */
    private class CuttingModelChangedListener implements PropertyChangeListener
    {

        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            Runnable runnable = new Runnable()
            {
                @Override
                public void run()
                {
                    if (hasPlasticDetails())
                    {
                        addCuttingDrawingViewHandler();
                        //если у commonCuttingModel strips is loaded, то загружаем dspModel
                        if (getMainCuttingPanel().getCommonCuttersPanel().getCuttingModel().isStripsLoaded())
                        {
                            //если вообще есть пары, то создаём панель и устанавливаем модель
                            CuttingModel dspCuttingModel = loadDSPPlasticCuttingModel();
                            if (dspCuttingModel.getPairs().size() > 0)
                            {
                                CuttersPanel dspCuttersPanel = createDspPlasticPanel();
                                dspCuttersPanel.setCuttingModel(dspCuttingModel);
                                dspCuttersPanel.setOrderBoardDetailFacade(FacadeContext.getDSPPlasticDetailFacade());
                                dspCuttersPanel.setStripsFacade(FacadeContext.getDSPPlasticStripsFacade());
                                getMainCuttingPanel().setDspPlasticCuttersPanel(dspCuttersPanel);
                                //и если strips не loaded
                                if (!dspCuttingModel.isStripsLoaded())
                                {
                                    dspCuttersPanel.generate();
                                }
                            }
                        }
                        else
                        {
                            removeAll();
                        }
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
    }

    /**
     * слушает изменеие модели (нажатие generate в commonCuttersPanel меняет модель)
     */
    private class CommonCuttingStarted implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            stopDspPlasticCuttingProcess();
            removeAll();
        }
    }


    private class CommonCuttingFinishedSuccessfully implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            if (hasPlasticDetails() && !isCuttingLoaded())
            {
                WindowCallback windowCallback = new WindowCallback();
                PlasticTextureBoardDefTab plasticTab = new PlasticTextureBoardDefTab();
                plasticTab.setWindowCallback(windowCallback);
                plasticTab.setDSPPlasticDelegator(DSPPlasticDelegator.this);
                plasticTab.init();
                DialogShowers.showBy(plasticTab, mainCuttingPanel, windowCallback, true, false);
            }
        }
    }

    private class PlasticCuttingStarted implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            mainCuttingPanel.getCommonCuttersPanel().setEditable(false);
        }
    }

    private class PlasticCuttingFinishedSuccessfully implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            mainCuttingPanel.getCommonCuttersPanel().setEditable(mainCuttingPanel.getDspPlasticCuttersPanel().getCuttingModel().getOrder().isEditable());
        }
    }

}
