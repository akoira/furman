package by.dak.cutting.swing.order;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.OrderItem;
import by.dak.test.ATestApplication;

import javax.swing.*;

/**
 * User: akoyro
 * Date: 06.08.2009
 * Time: 22:26:42
 */
public class TOrderDetailsTab extends ATestApplication {
	public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
		TOrderDetailsTab.launch(TOrderDetailsTab.class, args);
	}

	@Override
	public JComponent getMainComponent() {
		OrderItem item = mainFacade.getOrderItemFacade().loadAll().get(0);

		OrderDetailsPanel panel = new OrderDetailsPanel(this.getContext());
		panel.setEnabled(true);
		panel.setValue(item);
		return panel;
	}
}
