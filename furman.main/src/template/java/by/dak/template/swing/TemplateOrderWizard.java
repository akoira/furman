package by.dak.template.swing;

import by.dak.order.swing.AOrderWizard;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Order;
import by.dak.swing.wizard.Step;
import by.dak.template.TemplateOrder;
import org.netbeans.spi.wizard.Wizard;

import java.util.Map;

/**
 * User: akoyro
 * Date: 16.03.11
 * Time: 19:03
 */
public class TemplateOrderWizard extends AOrderWizard<TemplateOrderInfoPanel, TemplateOrder> {
	private TemplateOrderInfoPanel infoPanel;

	private OrderInfoStep orderInfoStep;
	private Order sourceOrder;

	public TemplateOrderWizard(String orderName) {
		super(orderName, TemplateOrderWizard.class);
		infoPanel = new TemplateOrderInfoPanel();
		infoPanel.init();
		orderInfoStep = new OrderInfoStep(infoPanel);
	}

	@Override
	protected Step<TemplateOrderInfoPanel> getInfoStep() {
		return orderInfoStep;
	}

	public Order getSourceOrder() {
		return sourceOrder;
	}

	public void setSourceOrder(Order sourceOrder) {
		this.sourceOrder = sourceOrder;
	}

	private class OrderInfoStep extends Step<TemplateOrderInfoPanel> {
		public OrderInfoStep(TemplateOrderInfoPanel panel) {
			super(panel);
		}

		@Override
		protected boolean validate(Map map, Wizard wizard) {
			return getView().validateGUI();
		}

		@Override
		protected void proceedNext(Map map, Wizard wizard) {
			if (getSourceOrder() != null) {
				FacadeContext.getTemplateOrderFacade().copy(getSourceOrder(), getOrder(), null);
			} else {
				FacadeContext.getTemplateOrderFacade().save(getOrder());
			}
			getItemsStep().setOrder(getView().getOrder());
		}

		@Override
		protected void remainOnPage(Map map, Wizard wizard) {
			getView().setFocusToFirstComponent();
		}
	}

}
