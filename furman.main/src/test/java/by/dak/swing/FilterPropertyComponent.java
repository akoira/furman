package by.dak.swing;

import by.dak.cutting.swing.DComboBox;
import by.dak.cutting.swing.DPanel;

import javax.swing.*;
import java.awt.*;

/**
 * User: akoyro
 * Date: 23.08.2009
 * Time: 19:46:50
 */
public abstract class FilterPropertyComponent<L extends JComponent, C extends JComponent, E extends JComponent>
        extends DPanel
{
    private PropertyFilter propertyFilter;

    private L labelComponent;
    private C criterionComponent;
    private E editorComponent;

    public void init()
    {
        addLabelComponent(getLabelComponent());
        addCriterionComponent(getCriterionComponent());
        addEditorComponent(getEditorComponent());
    }

    public abstract void bind();

    public FilterPropertyComponent()
    {
        super(new GridBagLayout());
    }

    public PropertyFilter getFilterProperty()
    {
        return propertyFilter;
    }

    public void setFilterProperty(PropertyFilter propertyFilter)
    {
        this.propertyFilter = propertyFilter;
    }

    public L getLabelComponent()
    {
        if (labelComponent == null)
        {
            labelComponent = (L) new JLabel(propertyFilter.getProperty());
        }
        return labelComponent;
    }

    public void setLabelComponent(L labelComponent)
    {
        this.labelComponent = labelComponent;
    }

    public C getCriterionComponent()
    {
        if (criterionComponent == null)
        {
            criterionComponent = (C) new DComboBox();
        }
        return criterionComponent;
    }

    public void setCriterionComponent(C criterionComponent)
    {
        this.criterionComponent = criterionComponent;
    }

    public E getEditorComponent()
    {
        if (editorComponent == null)
        {
            editorComponent = (E) new JTextField();
        }
        return editorComponent;
    }

    public void setEditorComponent(E editorComponent)
    {
        this.editorComponent = editorComponent;
    }

    private void addLabelComponent(L component)
    {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 5, 0, 0);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.EAST;

        add(component, constraints);
    }

    private void addCriterionComponent(C component)
    {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 5, 0, 0);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.EAST;

        add(component, constraints);
    }


    private void addEditorComponent(E component)
    {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 5, 0, 0);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.ipady = 1;
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        add(component, constraints);
    }

}
