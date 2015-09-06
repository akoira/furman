package by.dak.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.JTableBinding;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;

/**
 * User: akoyro
 * Date: 15.06.2009
 * Time: 15:28:50
 */
public class BindingUtils
{

    private static final Log LOGGER = LogFactory.getLog(BindingUtils.class.getName());

    public final static String PREFIX_TABLE_COLUMN_KEY = "table.column.";

    public static void setSourceObject(BindingGroup bindingGroup, Object source)
    {
        bindingGroup.unbind();
        List<Binding> bindings = bindingGroup.getBindings();
        for (Binding binding : bindings)
        {
            binding.setSourceObject(source);
        }
        bindingGroup.bind();
    }

    public static JTableBinding.ColumnBinding createColumnBinding(JTableBinding jTableBinding, String propertyName, Class beanClass, ResourceMap resourceMap)
    {
        return createColumnBinding(jTableBinding, propertyName, beanClass, resourceMap, false);
    }

    public static JTableBinding.ColumnBinding createColumnBinding(JTableBinding jTableBinding, String propertyName, Class beanClass, ResourceMap resourceMap, boolean editable)
    {
        JTableBinding.ColumnBinding columnBinding = null;
        try
        {
            columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${" + propertyName + "}"), propertyName);
            columnBinding.setColumnName(resourceMap != null ? resourceMap.getString(PREFIX_TABLE_COLUMN_KEY + propertyName) : propertyName);
            BeanInfo beanInfo = Introspector
                    .getBeanInfo(beanClass);
            beanInfo.getPropertyDescriptors();
            
            columnBinding.setColumnClass(getPropertyClassBy(beanInfo, propertyName));

            columnBinding.setEditable(editable);
        }
        catch (IntrospectionException e)
        {
            LOGGER.error("", e);
        }
        return columnBinding;
    }

    public static Class getPropertyClassBy(BeanInfo beanInfo, String propertyName)
    {
        for (int i = 0; i < beanInfo.getPropertyDescriptors().length; i++)
        {
            PropertyDescriptor propertyDescriptor = beanInfo.getPropertyDescriptors()[i];
            if (propertyDescriptor.getName().equals(propertyName))
            {
                return propertyDescriptor.getPropertyType();
            }
        }
       return null;
    }

}
