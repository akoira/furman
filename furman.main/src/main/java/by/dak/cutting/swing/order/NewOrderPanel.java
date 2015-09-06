/*
 * NewOrderPanel.java
 *
 * Created on 06.12.2008, 12:07:43
 */
package by.dak.cutting.swing.order;

import by.dak.cutting.swing.CleanModPanel;
import by.dak.cutting.swing.store.tabs.OrderInfoTab;
import by.dak.order.swing.IOrderStepDelegator;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Order;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.*;

import java.awt.*;
import java.beans.Beans;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author admin
 */
public class NewOrderPanel extends CleanModPanel implements IOrderStepDelegator<Order>
{

    private ResourceMap rsMap = Application.getInstance().getContext().getResourceMap(NewOrderPanel.class);
    private OrderInfoTab oiTab;

    public NewOrderPanel()
    {
        initEnvironment();
    }

    @Override
    protected void runWork()
    {
        oiTab = new OrderInfoTab();
        tabbedPane.addTab(rsMap.getString("newOrderPanelTab.title"), oiTab);
    }

    public void initEnvironment()
    {

        //   startTabs();
        runWork();
        if (!Beans.isDesignTime())
        {
            oiTab.getCustomerValue().setItems(FacadeContext.getCustomerFacade().loadAllSortedBy("name"));
            oiTab.getDesignerValue().setItems(FacadeContext.getDesignerFacade().loadAllSortedBy("name"));
        }
    }

    private Order order;
    private BindingGroup bindingGroup;

    /**
     * @return the orderEntity
     */
    public Order getOrder()
    {
        return order;
    }

    /**
     * @param order the orderEntity to set
     */
    public void setOrder(Order order)
    {
        this.order = order;
        initBindingGroup();
    }

    private void initBindingGroup()
    {
        bindingGroup = new BindingGroup();

        Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, order,
                BeanProperty.create("orderNumber"),
                oiTab.getNumberValue().getNumber(),
                BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, order,
                BeanProperty.create("name"),
                oiTab.getNameValue(),
                BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, order,
                BeanProperty.create("dialerCost"),
                oiTab.getCostValue(),
                BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        Converter<Double, String> converterLong = new Converter<Double, String>()
        {
            @Override
            public String convertForward(Double aDouble)
            {
                return aDouble.toString().replace('.', ',');
            }

            @Override
            public Double convertReverse(String s)
            {
                return new Double(s.replace(',', '.'));
            }
        };
        binding.setConverter(converterLong);
        bindingGroup.addBinding(binding);

        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, order,
                BeanProperty.create("readyDate"),
                oiTab.getReadyValue(),
                BeanProperty.create("date"));
        binding.setSourceUnreadableValue(null);
        Converter<java.sql.Date, Date> converterDate = new Converter<java.sql.Date, Date>()
        {
            @Override
            public Date convertForward(java.sql.Date date)
            {
                return new Date(date.getTime());
            }

            @Override
            public java.sql.Date convertReverse(Date date)
            {
                return new java.sql.Date(date.getTime());
            }
        };
        binding.setConverter(converterDate);
        bindingGroup.addBinding(binding);

        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, order,
                BeanProperty.create("customer"),
                oiTab.getCustomerValue().getComboBoxItem(),
                BeanProperty.create("selectedItem"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);


        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, order,
                BeanProperty.create("designer"),
                oiTab.getDesignerValue().getComboBoxItem(),
                BeanProperty.create("selectedItem"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, order,
                BeanProperty.create("miscalculation"),
                oiTab.getOrderMiscalculation(),
                BeanProperty.create("selected"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);


        bindingGroup.bind();

        if (!Beans.isDesignTime())
        {
//            Calendar calendar = Calendar.getInstance();
//            if ( getOrderItem().getReadyDate() != null
//                    && order.getCreatedDailySheet().getDate().before(getOrderItem().getReadyDate() ))
//            {
//                calendar.setTime(order.getReadyDate());
//            }
//            else
//            {
//                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
//            }
//            oiTab.getReadyValue().setCalendar(calendar);
            oiTab.getDateValue().setText(new SimpleDateFormat("dd.MM.yyyy").format(order.getCreatedDailySheet().getDate()));
            oiTab.getNumberValue().fill(order.getCreatedDailySheet().getDate(), order.getOrderNumber());
//            oiTab.getCostValue().setText(rsMap.getString("costValue.text"));
            oiTab.getLastOrderNumberInfoPanel().refresh();
        }

    }

    public boolean validateData()
    {
        OrderInfoValidator orderInfoValidator = new OrderInfoValidator();
        return orderInfoValidator.validate();
    }

    public void requestFocus(String name)
    {
        for (int i = 0; i < oiTab.getComponents().length; i++)
        {
            Component component = oiTab.getComponents()[i];
            if (component.getName() != null && component.getName().equals(name))
            {
                component.requestFocus();
            }
        }
    }

    public void setFocusToFirstComponent()
    {
        requestFocus("numberValue");
    }

    private class OrderInfoValidator
    {

        public boolean validate()
        {
            ValidationResult validationResult = new ValidationResult();
            ValidationResultModel validationResultModel = lbWarningList.getModel();

            if (order.getReadyDate() != null && order.getReadyDate().before(order.getCreatedDailySheet().getDate()))
            {
                validationResult.addError(rsMap.getString("validator.readyDate"));
            }

            if (ValidationUtils.isEmpty(oiTab.getNumberValue().getNumber().getText()))
            {
                validationResult.addError(rsMap.getString("validator.number"));
            }

            if (ValidationUtils.isEmpty(oiTab.getNameValue().getText()))
            {
                validationResult.addError(rsMap.getString("validator.name"));
            }
            if (oiTab.getCustomerValue().getComboBoxItem().getSelectedIndex() == -1)
            {
                validationResult.addError(rsMap.getString("validator.customer"));
            }

            validationResultModel.setResult(validationResult);
            lbWarningList.setModel(validationResultModel);

            if (validationResultModel.hasErrors())
            {
                return false;
            }
            return true;
        }
    }

    public void setEditable(boolean editable)
    {
        oiTab.setEditable(editable);
        super.setEditable(editable);
    }
}
