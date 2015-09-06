package by.dak.cutting.swing.order;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
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
		UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		SpringConfiguration springConfiguration = new SpringConfiguration();
		TOrderDetailsTab.launch(TOrderDetailsTab.class, args);
	}

	@Override
	public JComponent getMainComponent() {
		OrderItem item = FacadeContext.getOrderItemFacade().loadAll().get(0);

		FurnitureLinkPanel furnitureLinkPanel = new FurnitureLinkPanel();
		furnitureLinkPanel.setEditable(true);
		furnitureLinkPanel.setValue(item);
		return furnitureLinkPanel;
	}
}
