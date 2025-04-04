package by.dak.cutting.swing.order;

import by.dak.cutting.facade.ServicePlugTypeFacade;
import by.dak.cutting.swing.BaseTabPanel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.ServiceLink;
import by.dak.persistence.entities.OrderItem;
import by.dak.persistence.entities.ServicePlugType;
import by.dak.persistence.entities.predefined.ServiceType;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.utils.BindingAdapter;
import by.dak.utils.validator.ValidationUtils;
import by.dak.utils.validator.ValidatorAnnotationProcessor;
import com.jgoodies.validation.ValidationResult;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingListener;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.swingx.JXComboBox;

import javax.accessibility.Accessible;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.ComboPopup;
import java.util.*;

public class ServiceLinkSupporter
{
    private List<ServiceLink> linkList;
    private Controller controller = new Controller();

    private BindingAdapter clearServiceCodeListener;
    private BaseTabPanel<OrderItem> panel;

    private static final List<String> SERVICE_SQUARE_METER_TYPES = Collections.singletonList(ServiceType.dspMirrorGluing.name());
    private static final List<String> SERVICE_LINEAR_METER_TYPES = Arrays.asList(
            ServiceType.euroCutting.name(),
            ServiceType.compactEdgeProcessing.name(),
            ServiceType.planeThicknessSelection.name()
    );

    public ServiceLinkSupporter(BaseTabPanel<OrderItem> panel)
    {
        this.panel = panel;
        linkList = ObservableCollections.observableList(new ArrayList<ServiceLink>());
        clearServiceCodeListener = new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                ServiceLink serviceLink = (ServiceLink) binding.getSourceObject();

                if (serviceLink != null) {
                    final String serviceName = serviceLink.getPriced().getName();
                    final ServicePlugTypeFacade facade = FacadeContext.getServicePlugTypeFacade();

                    final ServicePlugType priceAware =
                            SERVICE_LINEAR_METER_TYPES.contains(serviceName) ? facade.findByUnit(Unit.linearMetre) :
                                    SERVICE_SQUARE_METER_TYPES.contains(serviceName) ? facade.findByUnit(Unit.squareMetre) :
                                            null;
                    serviceLink.setPriceAware(priceAware);
                }
            }
        };
    }

    public List<ServiceLink> getLinkList()
    {
        return linkList;
    }

    public void setLinkList(List<ServiceLink> linkList)
    {
        this.linkList.clear();
        this.linkList.addAll(linkList);
        ServiceLink serviceLink = createEmptyServiceLink();
        this.linkList.add(serviceLink);
    }

    public BindingListener getServiceLinkChangedController()
    {
        return controller;
    }

    protected ServiceLink createEmptyServiceLink()
    {
        return new ServiceLink();
    }

    public BindingAdapter getClearServiceCodeListener()
    {
        return clearServiceCodeListener;
    }


    /**
     * This method adjust autocomplete behavior for comboBox table editor in the following way:
     * when user is selecting item by pressing keys UP or DOWN this selected item is set
     * as current value for correspondent field.
     */
    public void adjustComboBoxEditor(final JXComboBox comboBox)
    {
        final Accessible a = comboBox.getUI().getAccessibleChild(comboBox, 0);
        if (a instanceof ComboPopup)
        {
            ((ComboPopup) a).getList().addListSelectionListener(new ListSelectionListener()
            {
                @Override
                public void valueChanged(ListSelectionEvent e)
                {
                    Runnable runnable = new Runnable()
                    {
                        public void run()
                        {
                            Object value = ((ComboPopup) a).getList().getSelectedValue();
                            if (value != null)
                                comboBox.getEditor().setItem(value);
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                }
            });
        }
    }

    private class Controller extends BindingAdapter
    {
        @Override
        public void synced(Binding binding)
        {

            ServiceLink serviceLink = (ServiceLink) binding.getSourceObject();

            ValidationResult result = ValidatorAnnotationProcessor.getProcessor().validate(serviceLink);
            if (panel.getWarningList() != null)
            {
                panel.getWarningList().getModel().setResult(result);
            }
            if (!ValidationUtils.isErrors(result))
            {
                if (serviceLink.getId() == null || serviceLink.getId() == 0)
                {
                    linkList.add(createEmptyServiceLink());
                }

                if (panel.getValue() != null)
                {
                    serviceLink.setOrderItem(panel.getValue());
                    FacadeContext.getServiceLinkFacade().save(serviceLink);
                }
            }
        }
    }

}
