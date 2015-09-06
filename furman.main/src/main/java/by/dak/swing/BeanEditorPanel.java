package by.dak.swing;

import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * User: akoyro
 * Date: 13.07.2009
 * Time: 10:53:21
 */
public class BeanEditorPanel<B> extends JPanel
{

    private B bean;
    protected JTable table;
    protected PropertyTableData model = new PropertyTableData();
    private ResourceMap resourceMap;

    private List<String> hidePropertyNames = new ArrayList<String>();

    public BeanEditorPanel()
    {
        setLayout(new BorderLayout());
        table = new JTable()
        {
            @Override
            public TableCellEditor getCellEditor(int row, int column)
            {
                if (column == 0)
                {
                    return super.getCellEditor(row, column);
                }
                else
                {
                    return getDefaultEditor(((PropertyTableData) getModel()).getCellClass(row, column));
                }
            }

            @Override
            public TableCellRenderer getCellRenderer(int row, int column)
            {
                if (column == 0)
                {
                    return super.getCellRenderer(row, column);
                }
                else
                {
                    return getDefaultRenderer(((PropertyTableData) getModel()).getCellClass(row, column));
                }
            }
        };
        JScrollPane ps = new JScrollPane();
        ps.getViewport().add(table);
        add(ps, BorderLayout.CENTER);
        table.setModel(model);
        model.fireTableDataChanged();
    }

    public void addHidePropertyName(String propertyName)
    {
        hidePropertyNames.add(propertyName);
    }

    public B getBean()
    {
        return bean;
    }

    public void setBean(B bean)
    {
        this.bean = bean;
        if (bean != null)
        {
            model.setBean(bean);
        }
    }

    public ResourceMap getResourceMap()
    {
        return resourceMap;
    }

    public void setResourceMap(ResourceMap resourceMap)
    {
        this.resourceMap = resourceMap;
    }


    class PropertyTableData extends AbstractTableModel
    {
        protected List<PropertyDescriptor> data = new ArrayList<PropertyDescriptor>();

        public void setBean(B bean)
        {
            data.clear();
            try
            {
                BeanInfo info = Introspector.getBeanInfo(

                        bean.getClass());
                PropertyDescriptor[] props = info.getPropertyDescriptors();
                for (int i = 0; i < props.length; i++)
                {
                    PropertyDescriptor propertyDescriptor = props[i];
                    if (propertyDescriptor.getReadMethod() != null &&
                            propertyDescriptor.getWriteMethod() != null &&
                            !hidePropertyNames.contains(propertyDescriptor.getDisplayName()))
                    {
                        data.add(propertyDescriptor);
                    }
                }
                fireTableDataChanged();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(BeanEditorPanel.this, "Error: " + ex.toString(),
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }

        public int getRowCount()
        {
            return data.size();
        }

        public int getColumnCount()
        {
            return 2;
        }

        public String getColumnName(int nCol)
        {
            return nCol == 0 ? "Property" : "Value";
        }

        public boolean isCellEditable(int nRow, int nCol)
        {
            return (nCol == 1);
        }

        public Class getColumnClass(int columnIndex)
        {
            if (columnIndex == 0)
            {
                return String.class;
            }
            else
            {
                return Object.class;
            }
        }

        public Class<?> getCellClass(int rowIndex, int columnIndex)
        {
            PropertyDescriptor descriptor = data.get(rowIndex);
            if (columnIndex == 0)
            {
                return String.class;
            }
            else
            {
                return descriptor.getPropertyType();
            }
        }


        public Object getValueAt(int nRow, int nCol)
        {

            if (nRow < 0 || nRow >= getRowCount())
                return "";

            PropertyDescriptor prop =
                    data.get(nRow);
            switch (nCol)
            {
                case 0:
                    return getResourceMap() != null ? getResourceMap().getString(prop.getDisplayName()) : prop.getDisplayName();
                case 1:
                    Method mRead = prop.getReadMethod();
                    if (mRead != null &&
                            mRead.getParameterTypes().length == 0)
                    {
                        try
                        {
                            return mRead.invoke(bean, (Object[]) null);
                        }
                        catch (Exception e)
                        {
                            return "error";
                        }
                    }
            }
            return "";
        }

        public void setValueAt(Object value, int nRow, int nCol)
        {
            if (nRow < 0 || nRow >= getRowCount())
                return;

            PropertyDescriptor prop = data.get(nRow);

            Method mWrite = prop.getWriteMethod();
            if (mWrite == null || mWrite.getParameterTypes().length != 1)
            {
                return;
            }
            try
            {
                mWrite.invoke(bean, new Object[]{value});
                fireTableCellUpdated(nRow, nCol);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        BeanEditorPanel.this, "Error: " + ex.toString(),
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }

        public String objToString(Object value)
        {
            if (value == null)
                return "null";
            if (value instanceof Dimension)
            {
                Dimension dim = (Dimension) value;
                return "" + dim.width + "," + dim.height;
            }
            else if (value instanceof Insets)
            {
                Insets ins = (Insets) value;
                return "" + ins.left + "," + ins.top + "," + ins.right + "," + ins.bottom;
            }
            else if (value instanceof Rectangle)
            {
                Rectangle rc = (Rectangle) value;
                return "" + rc.x + "," + rc.y + "," + rc.width + "," + rc.height;
            }
            else if (value instanceof Color)
            {
                Color col = (Color) value;
                return "" + col.getRed() + "," + col.getGreen() + "," + col.getBlue();
            }
            return value.toString();
        }

        public Object stringToObj(String str, Class cls)
        {

            try
            {

                if (str == null)

                    return null;

                String name = cls.getName();

                if (name.equals("java.lang.String"))

                    return str;

                else if (name.equals("int"))

                    return new Integer(str);

                else if (name.equals("long"))

                    return new Long(str);

                else if (name.equals("float"))

                    return new Float(str);

                else if (name.equals("double"))

                    return new Double(str);

                else if (name.equals("boolean"))

                    return new Boolean(str);

                else if (name.equals("java.awt.Dimension"))
                {

                    int[] i = strToInts(str);

                    return new Dimension(i[0], i[1]);

                }

                else if (name.equals("java.awt.Insets"))
                {

                    int[] i = strToInts(str);

                    return new Insets(i[0], i[1], i[2], i[3]);

                }

                else if (name.equals("java.awt.Rectangle"))
                {

                    int[] i = strToInts(str);

                    return new Rectangle(i[0], i[1], i[2], i[3]);

                }

                else if (name.equals("java.awt.Color"))
                {

                    int[] i = strToInts(str);

                    return new Color(i[0], i[1], i[2]);

                }

                return null;    // not supported

            }

            catch (Exception ex)
            {
                return null;
            }

        }

        public int[] strToInts(String str) throws Exception
        {
            int[] i = new int[4];
            StringTokenizer tokenizer = new StringTokenizer(str, ",");
            for (int k = 0; k < i.length &&
                    tokenizer.hasMoreTokens(); k++)
                i[k] = Integer.parseInt(tokenizer.nextToken());
            return i;
        }

    }

    public JTable getTable()
    {
        return table;
    }

}

