package by.dak.cutting.swing.dictionaries;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.currency.persistence.entity.CurrencyType;
import by.dak.cutting.swing.BaseTabPanel;
import by.dak.cutting.swing.BaseTable;
import by.dak.cutting.swing.DComboBox;
import by.dak.persistence.entities.PriceEntity;
import by.dak.utils.BindingAdapter;
import by.dak.utils.MathUtils;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;
import org.jdesktop.swingx.table.TableColumnExt;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public abstract class AbstractPricesTab<V> extends BaseTabPanel<V>
{

    private javax.swing.JScrollPane jScrollPane1;

    private by.dak.cutting.swing.BaseTable tablePrices;
    private JPopupMenu popupMenu = new JPopupMenu();


    private PriceListSupporter priceListSupporter = new PriceListSupporter(this)
    {
        @Override
        protected PriceEntity createEmptyPrice()
        {
            return AbstractPricesTab.this.createEmptyPrice();
        }
    };

    public AbstractPricesTab()
    {
        super(false);
        setName(this.getClass().getSimpleName());
        initComponents();
        if (!Beans.isDesignTime())
        {
            initEnvironment();
        }
    }

    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablePrices = new by.dak.cutting.swing.BaseTable();


        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tablePrices.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        tablePrices.setName("tablePrices"); // NOI18N
        jScrollPane1.setViewportView(tablePrices);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                                .addContainerGap())
        );

        popupMenu.add(actionMap.get("setValueForAll"));
    }

    private void initEnvironment()
    {
        tablePrices.setSortable(false);
        tablePrices.setRowSelectionAllowed(false);
        tablePrices.setColumnSelectionAllowed(false);
        tablePrices.setCellSelectionEnabled(true);
        tablePrices.getTableHeader().setReorderingAllowed(false);

        SelectionController controller = new SelectionController();
        tablePrices.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePrices.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePrices.getSelectionModel().addListSelectionListener(controller);
        tablePrices.getColumnModel().getSelectionModel().addListSelectionListener(controller);
        tablePrices.setEditable(true);

        tablePrices.addMouseListener(new PopupListener(popupMenu));

        tablePrices.setDefaultEditor(CurrencyType.class, new ComboBoxCellEditor(new DComboBox<CurrencyType>(CurrencyType.values())));

        initTableGui();

        initBinding();
    }

    private void initBinding()
    {
        BindingAdapter priceFaktorBindingAdapter = new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                if (!binding.getSourceValueForTarget().failed())
                {
                    PriceEntity priceEntity = (PriceEntity) binding.getSourceObject();
                    //с редактора может прейти null 
                    if (priceEntity.getPrice() != null && priceEntity.getPriceFaktor() != null)
                    {
                        priceEntity.setPriceDealer(MathUtils.round(priceEntity.getPrice() * priceEntity.getPriceFaktor(), 4));
                    }
                }
            }
        };

        BindingAdapter priceSaleFaktorBindingAdapter = new BindingAdapter()
        {
            @Override
            public void synced(Binding binding)
            {
                if (!binding.getSourceValueForTarget().failed())
                {
                    PriceEntity priceEntity = (PriceEntity) binding.getSourceObject();
                    //с редактора может прейти null
                    if (priceEntity.getPrice() != null
                            && priceEntity.getPriceSaleFaktor() != null)
                    {
                        priceEntity.setPriceSale(MathUtils.round(priceEntity.getPrice() * priceEntity.getPriceSaleFaktor(), 4));
                    }
                }
            }
        };

        JTableBinding jTableBinding = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ_WRITE,
                priceListSupporter.getPrices(), tablePrices);
        initColumnBindings(jTableBinding);


        JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${price}"), "price");
        columnBinding.setColumnName(getResourceMap().getString("table.column.price"));
        columnBinding.setColumnClass(Double.class);
        columnBinding.setEditable(true);
        columnBinding.addBindingListener(priceFaktorBindingAdapter);
        columnBinding.addBindingListener(priceSaleFaktorBindingAdapter);


        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${currencyType}"), "currencyType");
        columnBinding.setColumnName(getResourceMap().getString("table.column.currencyType"));
        columnBinding.setColumnClass(CurrencyType.class);
        columnBinding.setEditable(true);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priceFaktor}"), "priceFaktor");
        columnBinding.setColumnName(getResourceMap().getString("table.column.priceFaktor"));
        columnBinding.setColumnClass(Double.class);
        columnBinding.setEditable(true);
        columnBinding.addBindingListener(priceFaktorBindingAdapter);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priceDealer}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.priceDealer"));
        columnBinding.setColumnClass(Double.class);
        columnBinding.setEditable(true);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priceSaleFaktor}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.priceSaleFaktor"));
        columnBinding.setColumnClass(Double.class);
        columnBinding.setEditable(true);
        columnBinding.addBindingListener(priceSaleFaktorBindingAdapter);

        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${priceSale}"));
        columnBinding.setColumnName(getResourceMap().getString("table.column.priceSale"));
        columnBinding.setColumnClass(Double.class);
        columnBinding.setEditable(true);

        getBindingGroup().addBinding(jTableBinding);
        getBindingGroup().bind();
        jTableBinding.addBindingListener(priceListSupporter.getPriceChangedController());
        hideColumns(getHidenColumns());
        addPropertyChangeListener("value", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                initColumnEditors();
            }
        });
    }

    protected void initColumnEditors()
    {

    }

    protected String[] getHidenColumns()
    {
        return new String[]{};
    }

    private void hideColumns(String... columns)
    {
        for (String column : columns)
        {
            TableColumnExt tableColumnExt = getColumnExtBy(column);
            if (tableColumnExt != null)
            {
                tableColumnExt.setVisible(false);
            }

        }
    }

    public TableColumnExt getColumnExtBy(String columnKey)
    {
        TableColumnExt tableColumnExt = tablePrices.getColumnExt(getResourceMap().getString(columnKey));
        return tableColumnExt;
    }


    public List<PriceEntity> getPrices()
    {
        return priceListSupporter.getPrices();
    }

    public void setPrices(List<PriceEntity> list)
    {
        priceListSupporter.setPrices(list);
    }

    public BaseTable getTablePrices()
    {
        return tablePrices;
    }


    protected abstract void initColumnBindings(JTableBinding jTableBinding);

    protected abstract void initTableGui();

    protected abstract PriceEntity createEmptyPrice();

    //todo refactoring for SerForAllHelper
    public class SelectionController implements ListSelectionListener
    {
        @Override
        public void valueChanged(ListSelectionEvent e)
        {
            int column = getTablePrices().getSelectedColumn();
            int row = getTablePrices().getSelectedRow();
            if (row > -1 && row < getPrices().size() - 1)
            {
                if (column < getFirstPriceColumn())
                {
                    getTablePrices().getColumnModel().getSelectionModel().setSelectionInterval(getFirstPriceColumn(), getFirstPriceColumn());
                }
            }
        }
    }

    protected abstract int getFirstPriceColumn();


    private void setValueForAll(int startRow, int column, Object value)
    {

        int count = tablePrices.getModel().getRowCount();
        try
        {
            for (int i = startRow; i < count; i++)
            {
                tablePrices.getModel().setValueAt(value, i, column);
            }
        }
        catch (Exception e)
        {
            CuttingApp.getApplication().getExceptionHandler().handle(e);
        }
    }

    @org.jdesktop.application.Action
    public void setValueForAll()
    {
        int col = tablePrices.getSelectedColumn();
        int row = tablePrices.getSelectedRow();

        TableColumnExt tableColumnExt = tablePrices.getColumnExt(col);
        String property = null;
        if (tableColumnExt.getIdentifier().equals(getResourceMap().getString("table.column.price")))
        {
            property = PriceEntity.PROPERTY_price;
        }
        else if (tableColumnExt.getIdentifier().equals(getResourceMap().getString("table.column.priceFaktor")))
        {
            property = PriceEntity.PROPERTY_priceFaktor;
        }
        else if (tableColumnExt.getIdentifier().equals(getResourceMap().getString("table.column.priceDealer")))
        {
            property = PriceEntity.PROPERTY_priceDealer;
        }
        else if (tableColumnExt.getIdentifier().equals(getResourceMap().getString("table.column.priceSale")))
        {
            property = PriceEntity.PROPERTY_priceSale;
        }
        else if (tableColumnExt.getIdentifier().equals(getResourceMap().getString("table.column.priceSaleFaktor")))
        {
            property = PriceEntity.PROPERTY_priceSaleFaktor;
        }
        if (property != null)
        {
            Object value = tablePrices.getModel().getValueAt(row, tablePrices.convertColumnIndexToModel(col));
            setValueForAll(row, tablePrices.convertColumnIndexToModel(col), value);
        }
    }

    //todo refactoring for SerForAllHelper
    class PopupListener extends MouseAdapter
    {
        private JPopupMenu popupMenu;

        public PopupListener(JPopupMenu popupMenu)
        {
            this.popupMenu = popupMenu;
        }

        public void mousePressed(MouseEvent e)
        {
            showPopup(e);
        }

        public void mouseReleased(MouseEvent e)
        {
            showPopup(e);
        }

        private void showPopup(MouseEvent e)
        {
            if (e.isPopupTrigger())
            {
                int col = tablePrices.getSelectedColumn();
                int row = tablePrices.getSelectedRow();

                if (col > -1)
                {
                    TableColumnExt tableColumnExt = tablePrices.getColumnExt(col);
                    if (tableColumnExt.getIdentifier().equals(getResourceMap().getString("table.column.price")) ||
                            tableColumnExt.getIdentifier().equals(getResourceMap().getString("table.column.priceFaktor")) ||
                            tableColumnExt.getIdentifier().equals(getResourceMap().getString("table.column.priceDealer")) ||
                            tableColumnExt.getIdentifier().equals(getResourceMap().getString("table.column.priceSaleFaktor")) ||
                            tableColumnExt.getIdentifier().equals(getResourceMap().getString("table.column.priceSale"))
                            )
                    {
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        }
    }

}
