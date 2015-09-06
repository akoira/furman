package by.dak.cutting.swing.order.cellcomponents.editors;

import by.dak.cutting.swing.BaseTable;
import by.dak.cutting.swing.order.cellcomponents.renderers.CutoffCheckBoxProvider;
import by.dak.cutting.swing.order.data.Cutoff;
import by.dak.cutting.swing.table.PopupEditor;
import com.toedter.calendar.JDateChooserCellEditor;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

/**
 * User: akoyro
 * Date: 25.04.2009
 * Time: 14:08:37
 */
public class CutoffCellEditorTest extends JPanel
{
    private BaseTable baseTable = new BaseTable();
    private OrderFurnitureTableModel tableModel = new OrderFurnitureTableModel();


    public CutoffCellEditorTest()
    {
        super(new BorderLayout());
        baseTable.setModel(tableModel);
        baseTable.setDefaultRenderer(Cutoff.class, new DefaultTableRenderer(new CutoffCheckBoxProvider()));


        baseTable.getColumn(1).setCellEditor(new PopupEditor(new CutoffComponentProvider()
        {
            @Override
            public void setCellContext(by.dak.cutting.swing.table.CellContext cellContext)
            {
                Cutoff cutoff = (Cutoff) cellContext.getValue();
                if (cutoff == null)
                {
                    cutoff = new Cutoff();
                }

                OrderFurnitureTableModel model = (OrderFurnitureTableModel) ((BaseTable) cellContext.getTable()).getModel();
                OrderFurniture dto = model.getRowBy(cellContext.getRow());
                getPanel().setElement(new Dimension(dto.getLength(),
                        dto.getWidth()));
                getPanel().setCutoff(cutoff);

            }
        }));
        JScrollPane scrollPane = new JScrollPane(baseTable);
        baseTable.setDefaultEditor(Date.class, new JDateChooserCellEditor());

        add(scrollPane, BorderLayout.CENTER);
    }


    public class OrderFurnitureTableModel extends AbstractTableModel
    {
        private OrderFurniture orderFurniture = new OrderFurniture();
        private OrderFurniture orderFurniture1 = new OrderFurniture();

        public OrderFurnitureTableModel()
        {
            orderFurniture.setLength(700);
            orderFurniture.setWidth(400);
        }

        @Override
        public int getRowCount()
        {
            return 2;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public int getColumnCount()
        {
            return 2;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Class<?> getColumnClass(int columnIndex)
        {
            switch (columnIndex)
            {
                case 0:
                    return Date.class;
                case 1:
                    return Cutoff.class;
                default:
                    return null;
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            OrderFurniture orderFurniture = getRowBy(rowIndex);
            switch (columnIndex)
            {
                case 0:
                    return orderFurniture.getDate();
                case 1:
                    return orderFurniture.getCutoff();
                default:
                    return null;
            }
        }

        private OrderFurniture getRowBy(int rowIndex)
        {
            OrderFurniture orderFurniture = rowIndex == 0 ? this.orderFurniture : this.orderFurniture1;
            return orderFurniture;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex)
        {
            OrderFurniture orderFurniture = getRowBy(rowIndex);
            switch (columnIndex)
            {
                case 0:
                    orderFurniture.setDate((Date) aValue);
                    break;
                case 1:
                    orderFurniture.setCutoff((Cutoff) aValue);
                    break;
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return true;
        }
    }


    public class OrderFurniture
    {
        private Cutoff cutoff;
        private Date date;
        private int length = 1000;
        private int width = 1000;


        public Cutoff getCutoff()
        {
            return cutoff;
        }

        public void setCutoff(Cutoff cutoff)
        {
            this.cutoff = cutoff;
        }

        public Date getDate()
        {
            return date;
        }

        public void setDate(Date date)
        {
            this.date = date;
        }

        public int getLength()
        {
            return length;
        }

        public void setLength(int length)
        {
            this.length = length;
        }

        public int getWidth()
        {
            return width;
        }

        public void setWidth(int width)
        {
            this.width = width;
        }
    }

    public static void main(String[] args)
    {
        JFrame jFrame = new JFrame();
        jFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        CutoffCellEditorTest cutoffCellEditorTest = new CutoffCellEditorTest();
        jFrame.setContentPane(cutoffCellEditorTest);
        jFrame.pack();

        jFrame.setVisible(true);
    }


}
