package by.dak.cutting.swing.order;

import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.cutting.swing.order.popup.CommonSideMenu;
import by.dak.cutting.swing.order.popup.PopUpDrillingMenu;
import org.jdesktop.application.ApplicationContext;

import javax.swing.table.TableCellEditor;


public class PopupDrillingPanel extends AbstractPopupPanel<OrderDetailsDTO, OrderDetailsDTO>
{
    private ApplicationContext context;
    private PopUpDrillingMenu drillingMenu;

    public PopupDrillingPanel(TableCellEditor tableCellEditor, ApplicationContext context)
    {
        setTableCellEditor(tableCellEditor);
        this.context = context;
        initComponents();
    }

    @Override
    public CommonSideMenu getSideMenu()
    {
        if (drillingMenu == null)
        {
            drillingMenu = new PopUpDrillingMenu(getTableCellEditor());
        }
        return drillingMenu;
    }

    public void setData(OrderDetailsDTO data)
    {
        getSideMenu().setData(data);
    }

    public OrderDetailsDTO getData()
    {
        return getSideMenu().getData();
    }

    public OrderDetailsDTO getSelectedItem()
    {
        return getSideMenu().getData();
    }

    public void setContext(ApplicationContext context)
    {
        this.context = context;
    }
}
