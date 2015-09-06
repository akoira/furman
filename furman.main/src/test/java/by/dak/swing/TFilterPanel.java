package by.dak.swing;

import by.dak.cutting.swing.DComboBox;
import by.dak.cutting.swing.DPanel;
import by.dak.test.TestUtils;
import org.jdesktop.swingx.JXLabel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.beans.PropertyDescriptor;
import java.util.HashMap;

/**
 * User: akoyro
 * Date: 23.08.2009
 * Time: 15:02:36
 */
public class TFilterPanel extends DPanel
{
    private FilterPropertyComponentFactory factory;

    private HashMap<String, FilterPropertyComponent> components = new HashMap<String, FilterPropertyComponent>();

    public TFilterPanel(FilterPropertyComponentFactory factory, String[] properties)
    {
        this.factory = factory;
        setLayout(new GridBagLayout());

        for (String property : properties)
        {
            FilterPropertyComponent component = this.factory.getFilterPropertyComponent(property);
            addFilterPropertyComponent(component);
        }
//        try
//        {
//            BeanInfo info = Introspector.getBeanInfo(
//                    Order.class);
//            PropertyDescriptor[] propertyDescriptors = info.getPropertyDescriptors();
//            for (PropertyDescriptor propertyDescriptor : propertyDescriptors)
//            {
//
//                componentController.labelComponent = createLabelBy(propertyDescriptor);
//                this.addLabelComponent(componentController.labelComponent);
//
//                componentController.criterionComponent = createCriterionComponent();
//                this.addCriterionComponent(componentController.criterionComponent);
//
//                componentController.editorComponent = createEditorComponentBy(propertyDescriptor);
//                addEditorComponent(componentController.editorComponent);
//
//                PropertyFilter filterProperty = new PropertyFilter();
//                this.components.put(filterProperty, componentController);
//
//            }
//        }
//        catch (IntrospectionException e)
//        {
//            Logger.getLogger(TFilterPanel.class.getName()).log(Level.SEVERE, null, e);
//        }
    }

    private void addFilterPropertyComponent(FilterPropertyComponent component)
    {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = components.size();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.ipady = 1;
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        add(component, constraints);
        components.put(component.getFilterProperty().getProperty(), component);
    }


    private JComponent createCriterionComponent()
    {
        return new DComboBox();
    }


    private JComponent createEditorComponentBy(PropertyDescriptor propertyDescriptor)
    {
        return new JTextField(propertyDescriptor.getDisplayName());
    }


    private JComponent createLabelBy(PropertyDescriptor propertyDescriptor)
    {
        JXLabel label = new JXLabel(propertyDescriptor.getDisplayName());
        label.setBorder(new LineBorder(Color.BLACK));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    public static void main(String[] args)
    {
        TestUtils.showFrame(new TFilterPanel(new FilterPropertyComponentFactory(),
                new String[]{"p1",
                        "p2",
                        "p3"}), "");
    }
}
