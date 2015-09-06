package by.dak.cutting.swing.order;

import by.dak.cutting.swing.BaseTabPanel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.OrderItem;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 12.03.2010
 * Time: 15:10:49
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureLinkSupporter
{
    private List<FurnitureLink> linkList;
    private Controller controller = new Controller();

    private BindingAdapter clearFurnitureCodeListener;
    private BaseTabPanel<OrderItem> panel;

    public FurnitureLinkSupporter(BaseTabPanel<OrderItem> panel)
    {
        this.panel = panel;
        linkList = ObservableCollections.observableList(new ArrayList<FurnitureLink>());
        clearFurnitureCodeListener = new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                FurnitureLink furnitureLink = (FurnitureLink) binding.getSourceObject();
                if (furnitureLink != null)
                    furnitureLink.setFurnitureCode(null);
            }
        };
    }

    public List<FurnitureLink> getLinkList()
    {
        return linkList;
    }

    public void setLinkList(List<FurnitureLink> linkList)
    {
        this.linkList.clear();
        this.linkList.addAll(linkList);
        FurnitureLink furnitureLink = createEmptyFurnitureLink();
        this.linkList.add(furnitureLink);
    }

    public BindingListener getFurnitureLinkChangedController()
    {
        return controller;
    }

    protected FurnitureLink createEmptyFurnitureLink()
    {
        return new FurnitureLink();
    }

    public BindingAdapter getClearFurnitureCodeListener()
    {
        return clearFurnitureCodeListener;
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

            FurnitureLink furnitureLink = (FurnitureLink) binding.getSourceObject();

            ValidationResult result = ValidatorAnnotationProcessor.getProcessor().validate(furnitureLink);
            if (panel.getWarningList() != null)
            {
                panel.getWarningList().getModel().setResult(result);
            }
            if (!ValidationUtils.isErrors(result))
            {
                if (furnitureLink.getId() == null || furnitureLink.getId() == 0)
                {
                    linkList.add(createEmptyFurnitureLink());
                }

                if (panel.getValue() != null)
                {
                    furnitureLink.setOrderItem(panel.getValue());
                    FacadeContext.getFurnitureLinkFacade().save(furnitureLink);
                }
            }
        }
    }

}
