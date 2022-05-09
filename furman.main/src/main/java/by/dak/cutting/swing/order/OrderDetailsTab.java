package by.dak.cutting.swing.order;

import by.dak.additional.swing.AdditionalsTab;
import by.dak.cutting.swing.DModPanel;
import by.dak.cutting.swing.order.controls.OrderDetailsControl;
import by.dak.cutting.swing.order.wizard.ClearNextStepObserver;
import by.dak.design.swing.DesignerTab;
import by.dak.persistence.entities.OrderItem;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import org.jdesktop.application.Application;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class OrderDetailsTab extends DModPanel<OrderItem> implements ClearNextStepObserver
{

    private OrderDetailsControl orderDetailsControl;
    private OrderDetailsPanel orderDetailsPanel;
    private FurnitureLinkPanel furnitureLinkPanel;
    private AdditionalsTab additionalsTab;
    private DesignerTab designerTab;

    private TableModelListener clearListener = e -> firePropertyChange(ClearNextStepObserver.PROPERTY_clearNextStep, null, Boolean.TRUE);


    public OrderDetailsTab()
    {
        setShowOkCancel(false);
        addPropertyChangeListener("value", evt -> {
            removeClearNextStep();
            orderDetailsPanel.setValue(getValue());
            furnitureLinkPanel.setValue(getValue());
            additionalsTab.setValue(getValue());
            additionalsTab.init();
            designerTab.setValue(getValue());
            addClearNextStep();
        });
        addTabs();
        orderDetailsControl = new OrderDetailsControl(this);
    }


    private void removeClearNextStep()
    {
        orderDetailsControl.remoteTableModelListener(clearListener);
        furnitureLinkPanel.getTable().getModel().removeTableModelListener(clearListener);
        additionalsTab.getListNaviTable().getTable().getModel().removeTableModelListener(clearListener);

    }

    private void addClearNextStep()
    {
        Runnable runnable = () -> {
            orderDetailsControl.addTableModelListener(clearListener);
            furnitureLinkPanel.getTable().getModel().addTableModelListener(clearListener);
            additionalsTab.getListNaviTable().getTable().getModel().addTableModelListener(clearListener);
        };
        SwingUtilities.invokeLater(runnable);

    }

    @Override
    protected void addTabs()
    {
        orderDetailsPanel = new OrderDetailsPanel(Application.getInstance().getContext());
        addTab(orderDetailsPanel);
        orderDetailsPanel.setWarningList(getWarningList());

        furnitureLinkPanel = new FurnitureLinkPanel();
        getFurnitureOrderPanel().addPropertyChangeListener("editable", evt -> getFurnitureOrderPanel().getTable().setEditable(isEditable()));
        addTab(furnitureLinkPanel);
        furnitureLinkPanel.setWarningList(getWarningList());

        additionalsTab = new AdditionalsTab();
        addTab(additionalsTab);
        additionalsTab.setWarningList(getWarningList());
        additionalsTab.addPropertyChangeListener("editable", evt -> additionalsTab.getListNaviTable().getTable().setEditable(isEditable()));
        designerTab = new DesignerTab();
        designerTab.addPropertyChangeListener("editable", evt -> designerTab.setEditable(isEditable()));
        addTab(designerTab);
    }

    public DesignerTab getDesignerTab()
    {
        return designerTab;
    }

    public OrderDetailsPanel getOrderDetailsPanel()
    {
        return orderDetailsPanel;
    }


    public Boolean validateGUI()
    {
        return validateData(true);
    }

    public boolean validateData(boolean isWizard)
    {
        getOrderDetailsPanel().getOrderTable().stopEditing();
        return new OrderDetailsValidator(isWizard).validate();
    }

    public boolean validateRow(int row)
    {
        getOrderDetailsPanel().getOrderTable().stopEditing();
        return new OrderDetailsValidator().validateRow(row);
    }

    public void setEditable(boolean editable)
    {
        getOrderDetailsPanel().setEditable(editable);
        super.setEditable(editable);
    }


    public FurnitureLinkPanel getFurnitureOrderPanel()
    {
        return furnitureLinkPanel;
    }

    public void setFurnitureOrderPanel(FurnitureLinkPanel furnitureLinkPanel)
    {
        this.furnitureLinkPanel = furnitureLinkPanel;
    }

    public OrderDetailsControl getOrderDetailsControl()
    {
        return orderDetailsControl;
    }


    private class OrderDetailsValidator
    {
        private boolean isWizard;

        private OrderDetailsValidator(boolean wizard)
        {
            isWizard = wizard;
        }

        private OrderDetailsValidator()
        {
            this(true);
        }

        public boolean validate()
        {
            ValidationResult validationResult = new ValidationResult();
            ValidationResultModel validationResultModel = getWarningList().getModel();
            orderDetailsPanel.validateGUI(validationResult, isWizard);

            validationResultModel.setResult(validationResult);
            getWarningList().setModel(validationResultModel);

            return !validationResultModel.hasErrors();
        }

        public boolean validateRow(int row)
        {
            ValidationResult validationResult = new ValidationResult();
            ValidationResultModel validationResultModel = getWarningList().getModel();
            orderDetailsPanel.validateRow(validationResult, row);

            validationResultModel.setResult(validationResult);
            getWarningList().setModel(validationResultModel);

            return !validationResultModel.hasErrors();
        }
    }

    public void cleanWarnList()
    {
        ValidationResultModel validationResultModel = getWarningList().getModel();
        validationResultModel.setResult(new ValidationResult());
        getWarningList().setModel(validationResultModel);
    }

    @Override
    @Deprecated
    public void save()
    {

    }


    @Override
    public void addClearNextStepListener(PropertyChangeListener listener)
    {
        addPropertyChangeListener(ClearNextStepObserver.PROPERTY_clearNextStep, listener);
    }

    @Override
    public void removeClearNextStepListener(PropertyChangeListener listener)
    {
        removePropertyChangeListener(ClearNextStepObserver.PROPERTY_clearNextStep, listener);
    }
}
