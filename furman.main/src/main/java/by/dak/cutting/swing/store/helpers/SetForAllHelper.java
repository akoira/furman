package by.dak.cutting.swing.store.helpers;

import by.dak.cutting.CuttingApp;
import by.dak.persistence.entities.PriceEntity;
import by.dak.swing.table.ListNaviTable;
import by.dak.swing.table.PopupMenuHelper;
import by.dak.utils.BindingUtils;
import org.jdesktop.application.Application;
import org.jdesktop.swingx.table.TableColumnExt;

import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 10.02.2010
 * Time: 20:41:19
 * To change this template use File | Settings | File Templates.
 */
public class SetForAllHelper extends PopupMenuHelper {
	private ListNaviTable listNaviTable;

	public SetForAllHelper(final ListNaviTable listNaviTable) {
		super(listNaviTable.getTable());
		this.listNaviTable = listNaviTable;


		setActionMap(Application.getInstance().getContext().getActionMap(SetForAllHelper.class, this));
		setPopupMenuTrigger(new PopupMenuTrigger() {
			@Override
			public boolean showPopupMenu(MouseEvent e) {
				int col = listNaviTable.getTable().getSelectedColumn();
				if (col > -1) {
					TableColumnExt tableColumnExt = listNaviTable.getTable().getColumnExt(col);
					return tableColumnExt.getIdentifier().equals(listNaviTable.getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + PriceEntity.PROPERTY_price)) ||
							tableColumnExt.getIdentifier().equals(listNaviTable.getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + PriceEntity.PROPERTY_priceFaktor)) ||
							tableColumnExt.getIdentifier().equals(listNaviTable.getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + PriceEntity.PROPERTY_priceDealer)) ||
							tableColumnExt.getIdentifier().equals(listNaviTable.getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + PriceEntity.PROPERTY_priceSale)) ||
							tableColumnExt.getIdentifier().equals(listNaviTable.getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + PriceEntity.PROPERTY_priceSaleFaktor))
							;
				} else {
					return false;
				}
			}
		});
	}

	private void setValueForAll(int startRow, int column, Object value) {
		int count = listNaviTable.getTable().getModel().getRowCount();
		try {
			for (int i = startRow; i < count; i++) {
				listNaviTable.getTable().getModel().setValueAt(value, i, column);
			}
		} catch (Exception e) {
			CuttingApp.getApplication().getExceptionHandler().handle(e);
		}
	}

	@org.jdesktop.application.Action
	public void setValueForAll() {
		int col = listNaviTable.getTable().getSelectedColumn();
		int row = listNaviTable.getTable().getSelectedRow();

		TableColumnExt tableColumnExt = listNaviTable.getTable().getColumnExt(col);
		String property = null;
		if (tableColumnExt.getIdentifier().equals(listNaviTable.getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + PriceEntity.PROPERTY_price))) {
			property = PriceEntity.PROPERTY_price;
		} else if (tableColumnExt.getIdentifier().equals(listNaviTable.getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + PriceEntity.PROPERTY_priceFaktor))) {
			property = PriceEntity.PROPERTY_priceFaktor;
		} else if (tableColumnExt.getIdentifier().equals(listNaviTable.getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + PriceEntity.PROPERTY_priceDealer))) {
			property = PriceEntity.PROPERTY_priceDealer;
		} else if (tableColumnExt.getIdentifier().equals(listNaviTable.getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + PriceEntity.PROPERTY_priceSale))) {
			property = PriceEntity.PROPERTY_priceSale;
		} else if (tableColumnExt.getIdentifier().equals(listNaviTable.getResourceMap().getString(BindingUtils.PREFIX_TABLE_COLUMN_KEY + PriceEntity.PROPERTY_priceSaleFaktor))) {
			property = PriceEntity.PROPERTY_priceSaleFaktor;
		}
		if (property != null) {
			int colM = listNaviTable.getTable().convertColumnIndexToModel(col);
			Object value = listNaviTable.getTable().getModel().getValueAt(row, colM);
			setValueForAll(row, colM, value);
		}
	}
}
