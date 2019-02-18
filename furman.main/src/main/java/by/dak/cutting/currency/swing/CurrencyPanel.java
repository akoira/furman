package by.dak.cutting.currency.swing;

import by.dak.cutting.currency.facade.CurrencyFacade;
import by.dak.cutting.currency.persistence.entity.Currency;
import by.dak.cutting.swing.BaseTabPanel;
import by.dak.persistence.entities.Dailysheet;
import org.jdesktop.swingx.JXComboBox;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.combobox.ListComboBoxModel;
import org.jdesktop.swingx.renderer.DefaultListRenderer;
import org.jdesktop.swingx.renderer.StringValue;

import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import static javax.swing.GroupLayout.PREFERRED_SIZE;

/**
 * User: akoiro
 * Date: 2019-02-16
 */
public class CurrencyPanel extends BaseTabPanel<Dailysheet> {

	private CurrencyListTab currencyListTab;
	private JXLabel label = new JXLabel("По умолчанию");
	private JXComboBox comboBox = new JXComboBox();

	private CurrencyFacade currencyFacade;

	private ItemListener listener = new ItemListener() {
		@Override
		public void itemStateChanged(ItemEvent e) {
			currencyFacade.setSelected((Currency) comboBox.getSelectedItem());
		}
	};

	public CurrencyPanel(CurrencyFacade currencyFacade) {
		this.currencyFacade = currencyFacade;
		this.currencyListTab = new CurrencyListTab(currencyFacade);

		comboBox.setRenderer(new DefaultListRenderer(new StringValue() {
			@Override
			public String getString(Object value) {
				return ((Currency) value).getType().name();
			}
		}));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup()
						.addGap(15)
						.addComponent(label, PREFERRED_SIZE, 100, PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED)
						.addGap(5)
						.addComponent(comboBox, PREFERRED_SIZE, PREFERRED_SIZE, PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED)
						.addGap(15)
				)
						.addComponent(currencyListTab, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup()
						.addGap(15)
						.addGroup(layout.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(label)
								.addComponent(comboBox, PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, PREFERRED_SIZE)
						).addPreferredGap(ComponentPlacement.RELATED).addComponent(
								currencyListTab, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)));
	}

	@Override
	protected void valueChanged() {
		currencyListTab.setValue(getValue());
		comboBox.setModel(new ListComboBoxModel<>(currencyFacade.allBy(getValue())));
		comboBox.setSelectedItem(currencyFacade.getSelected(getValue()));
		comboBox.addItemListener(listener);
	}

	@Override
	public String getTitle() {
		return getResourceMap().getString("panel.title", getValue().getDate());
	}

	public static CurrencyPanel valueOf(Dailysheet dailysheet, CurrencyFacade currencyFacade) {
		CurrencyPanel currencyPanel = new CurrencyPanel(currencyFacade);
		currencyPanel.setValue(dailysheet);
		return currencyPanel;
	}
}
