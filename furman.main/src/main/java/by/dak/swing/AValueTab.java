package by.dak.swing;

import by.dak.cutting.swing.BaseTabPanel;
import by.dak.utils.BindingUtils;
import by.dak.utils.GenericUtils;
import by.dak.utils.PropertyDescriptors;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.beansbinding.BindingListener;
import org.jdesktop.swingx.JXLabel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: akoyro
 * Date: 20.11.2009
 * Time: 15:13:30
 */
public class AValueTab<V> extends BaseTabPanel<V> implements FocusToFirstComponent
{
    private V value;
    private Class<V> valueClass;

    private String[] visibleProperties;

    private Map<Class, APropertyEditorCreator> cacheEditorCreator;
    private java.util.List<Component> labels = new ArrayList<Component>();
    private Map<String, Component> editors = new LinkedHashMap<String, Component>();
    private PropertyDescriptors cacheProperties;

    public PropertyDescriptors getCacheProperties()
    {
        if (cacheProperties == null)
        {
            cacheProperties = new PropertyDescriptors(getValueClass());
        }
        return cacheProperties;
    }

    public Map<String, Component> getEditors()
    {
        return editors;
    }

    public Class getValueClass()
    {
        if (valueClass == null)
        {
            valueClass = GenericUtils.getParameterClass(getClass(), 0);
        }
        return valueClass;
    }

    public void setValueClass(Class<V> valueClass)
    {
        this.valueClass = valueClass;
    }


    private JComponent createPropertyLabelComponent(String property)
    {
        JXLabel label = new JXLabel();
        if (getResourceMap() != null)
        {
            String bProperty = getResourceMap().getString("label." + property + ".text");
            if (bProperty != null)
            {
                property = bProperty;
            }
        }
        label.setText(property);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setVerticalAlignment(SwingConstants.TOP);
        return label;
    }

    private Component createPropertyEditorComponent(String property)
    {

        String[] path = StringUtils.split(property, '.');
        PropertyDescriptor descriptor = getCacheProperties().getPropertyDescriptor(path[0]);
        if (path.length > 1)
        {
            for (int i = 1; i < path.length; i++)
            {
                descriptor = new PropertyDescriptors(descriptor.getPropertyType()).getPropertyDescriptor(path[i]);
            }
        }
        return getEditorCreator(descriptor).createEditor(getValue(), property, descriptor, getBindingGroup());
    }

    protected APropertyEditorCreator getEditorCreator(PropertyDescriptor descriptor)
    {
        return cacheEditorCreator.get(descriptor.getPropertyType());
    }


    public void init()
    {
        getBindingGroup().unbind();
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        GroupLayout.ParallelGroup labelsGroup = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false);
        GroupLayout.ParallelGroup editorsGroup = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, true);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(labelsGroup)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(editorsGroup)
                                .addContainerGap())
        );

        String[] properties = getVisibleProperties();
        for (String property : properties)
        {
            Component label = createPropertyLabelComponent(property);
            labelsGroup.addComponent(label, GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
            labels.add(label);

            final Component editor = createPropertyEditorComponent(property);

            addPropertyChangeListener("editable", new PropertyChangeListener()
            {
                @Override
                public void propertyChange(PropertyChangeEvent evt)
                {
                    editor.setEnabled(isEditable());
                }
            });

            editorsGroup.addComponent(editor, javax.swing.GroupLayout.Alignment.TRAILING, 0, 300, Short.MAX_VALUE);
            editors.put(property, editor);
        }
        getBindingGroup().bind();


        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, labels.toArray(new Component[]{}));


        GroupLayout.SequentialGroup sequentialGroup = layout.createSequentialGroup();

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(sequentialGroup));
        int i = 0;
        for (Map.Entry<String, Component> entry : editors.entrySet())
        {
            Component label = labels.get(i++);
            Component editor = entry.getValue();
            if (i == 0)
            {
                sequentialGroup.addContainerGap();
            }
            else
            {
                sequentialGroup.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            }
            sequentialGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(label, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                    .addComponent(editor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));
        }
        sequentialGroup.addContainerGap();

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, labels.toArray(new Component[]{}));
    }


    public String[] getVisibleProperties()
    {
        return visibleProperties;
    }

    public void setVisibleProperties(String... visibleProperties)
    {
        this.visibleProperties = visibleProperties;
    }

    public V getValue()
    {
        return value;
    }

    public void setValue(V value)
    {
        V old = this.value;
        this.value = value;
        getBindingGroup().unbind();
        BindingListener[] bindingListeners = getBindingGroup().getValueBindingListeners();
        for (BindingListener bindingListener : bindingListeners)
        {
            getBindingGroup().removeBindingListener(bindingListener);
        }
        BindingUtils.setSourceObject(getBindingGroup(), this.value);
        firePropertyChange("value", old, value);
        for (BindingListener bindingListener : bindingListeners)
        {
            getBindingGroup().addBindingListener(bindingListener);
        }
    }

    public Map<Class, APropertyEditorCreator> getCacheEditorCreator()
    {
        return cacheEditorCreator;
    }

    public void setCacheEditorCreator(Map<Class, APropertyEditorCreator> cacheEditorCreator)
    {
        this.cacheEditorCreator = cacheEditorCreator;
    }

    @Override
    public void setFocusToFirstComponent()
    {
        if (getVisibleProperties() != null && getVisibleProperties().length > 0)
        {
            editors.get(getVisibleProperties()[0]).requestFocus();
        }
    }
}
