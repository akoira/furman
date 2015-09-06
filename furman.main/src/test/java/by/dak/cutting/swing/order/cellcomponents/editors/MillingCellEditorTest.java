package by.dak.cutting.swing.order.cellcomponents.editors;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.swing.BaseTable;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.MillingEditorDialog;
import by.dak.cutting.swing.order.data.Milling;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.cutting.swing.order.models.OrderDetailsTableModel;
import by.dak.cutting.swing.table.CellContext;
import by.dak.persistence.entities.TextureEntity;
import by.dak.swing.ButtonEditor;
import com.toedter.calendar.JDateChooserCellEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Locale;

/**
 * User: akoyro
 * Date: 25.04.2009
 * Time: 14:08:37
 */
public class MillingCellEditorTest extends JPanel
{
    private BaseTable baseTable = new BaseTable();
    private OrderFurnitureTableModel tableModel = new OrderFurnitureTableModel();
    private OrderDetailsDTO detailsDTO;

    public MillingCellEditorTest()
    {
        super(new BorderLayout());
        baseTable.setModel(tableModel);
        final MillingEditorDialog millingEditorDialog = new MillingEditorDialog(null, false);
        baseTable.getColumnModel().getColumn(1).setCellEditor(new ButtonEditor(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                DialogShowers.getMillingEditorDialogShower(millingEditorDialog).show();
            }
        }, new ButtonEditor.ReturnValue()
        {
            @Override
            public Object getValue()
            {
                return millingEditorDialog.getMilling();
            }

            @Override
            public void setCommitAction(Action action)
            {
                millingEditorDialog.getButtonOk().setAction(action);
            }

            @Override
            public void setCellContext(CellContext cellContext)
            {
                long l = ((OrderFurnitureTableModel) cellContext.getTable().getModel()).getRowBy(cellContext.getRow()).getLength();
                long w = ((OrderFurnitureTableModel) cellContext.getTable().getModel()).getRowBy(cellContext.getRow()).getWidth();

                OrderDetailsDTO dto = ((OrderDetailsTableModel) cellContext.getTable().getModel()).getRowBy(cellContext.getRow());

                MillingCellEditorTest.this.setDetailsDTO(dto, millingEditorDialog);

                millingEditorDialog.setElement(new Dimension((int) l, (int) w));
                millingEditorDialog.setMilling((String) cellContext.getValue());
            }

            @Override
            public void hidePopup()
            {
                millingEditorDialog.dispose();
            }

            @Override
            public void setCancelAction(final Action action)
            {
                millingEditorDialog.addWindowListener(new WindowAdapter()
                {
                    @Override
                    public void windowClosing(WindowEvent e)
                    {
                        action.actionPerformed(null);
                    }
                });
            }
        }
        ));

        JScrollPane scrollPane = new JScrollPane(baseTable);
        baseTable.setDefaultEditor(Date.class, new JDateChooserCellEditor());

        add(scrollPane, BorderLayout.CENTER);
    }

    private void setDetailsDTO(OrderDetailsDTO detailsDTO, MillingEditorDialog millingEditorDialog)
    {
        this.detailsDTO = detailsDTO;
        millingEditorDialog.setDetailsDTO(getDetailsDTO());
    }

    public OrderDetailsDTO getDetailsDTO()
    {
        return detailsDTO;
    }

    public class OrderFurnitureTableModel extends OrderDetailsTableModel
    {
        private OrderDetailsDTO orderFurniture = new OrderDetailsDTO();
        private OrderDetailsDTO orderFurniture1 = new OrderDetailsDTO();

        public OrderFurnitureTableModel()
        {
            orderFurniture.setLength(700l);
            orderFurniture.setWidth(400l);
            orderFurniture1.setLength(100l);
            orderFurniture1.setWidth(100l);
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
                    return Milling.class;
                default:
                    return null;
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            OrderDetailsDTO orderFurniture = getRowBy(rowIndex);
            switch (columnIndex)
            {
                case 0:
                    return orderFurniture.getTexture();
                case 1:
                    return orderFurniture.getMilling();
                default:
                    return null;
            }
        }

        public OrderDetailsDTO getRowBy(int rowIndex)
        {
            OrderDetailsDTO orderFurniture = rowIndex == 0 ? this.orderFurniture : this.orderFurniture1;
            return orderFurniture;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex)
        {
            OrderDetailsDTO orderFurniture = getRowBy(rowIndex);
            switch (columnIndex)
            {
                case 0:
                    orderFurniture.setTexture((TextureEntity) aValue);
                    break;
                case 1:
                    orderFurniture.setMilling((String) aValue);
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
        private String milling;
        private Date date;
        private int length = 1000;
        private int width = 1000;


        public String getMilling()
        {
            return milling;
        }

        public void setMilling(String milling)
        {
            this.milling = milling;
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
        Locale.setDefault(new Locale("ru", "RU", "utf8"));
        new SpringConfiguration();
        JFrame jFrame = new JFrame();
        jFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        MillingCellEditorTest millingCellEditorTest = new MillingCellEditorTest();
        jFrame.setContentPane(millingCellEditorTest);
        jFrame.pack();

        jFrame.setVisible(true);
    }


}