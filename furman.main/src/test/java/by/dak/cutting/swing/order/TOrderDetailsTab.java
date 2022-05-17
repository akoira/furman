package by.dak.cutting.swing.order;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.OrderItem;
import by.dak.test.ATestApplication;

import javax.swing.*;
import java.util.EventObject;

/**
 * User: akoyro
 * Date: 06.08.2009
 * Time: 22:26:42
 */
public final class TOrderDetailsTab extends ATestApplication {
	private OrderItem item;
	private OrderDetailsPanel panel;
	public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
		TOrderDetailsTab.launch(TOrderDetailsTab.class, args);
	}

	@Override
	public JComponent getMainComponent() {
		OrderItem item = mainFacade.getOrderItemFacade().loadAll().get(0);

		panel = new OrderDetailsPanel(getContext());
		panel.setEnabled(true);
		panel.setValue(item);
		return panel;
	}

	@Override
	public void exit(EventObject event) {
	}
}
