package by.dak.cutting.swing.archive;

import by.dak.cutting.statistics.swing.PeriodFilterPanel;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.Order;
import by.dak.swing.AStatisticPanel;
import by.dak.swing.ActionsPanel;
import by.dak.swing.table.AListUpdater;
import org.jdesktop.application.Application;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * User: akoyro
 * Date: 05.04.11
 * Time: 16:32
 */
public class ArchiveOrdersPanel extends AStatisticPanel<Order> {
	public ArchiveOrdersPanel(MainFacade mainFacade)
	{
		super(mainFacade);
	}
	@Override
	protected void init() {

		getPanelFilter().setCollapsed(true);
		final OrderExplorer orderExplorer = new OrderExplorer();
		setPanelResult(orderExplorer);

		final OrderFilterPanel orderFilterPanel = new OrderFilterPanel();
		orderFilterPanel.init();
		orderFilterPanel.setValue(new OrderFilter());


		ActionsPanel<JPanel> actionsPanel = new ActionsPanel<JPanel>(orderFilterPanel,
				Application.getInstance().getContext().getActionMap(orderFilterPanel),
				PeriodFilterPanel.ACTION_applyFilter,
				PeriodFilterPanel.ACTION_resetFilter);
		actionsPanel.init();

		setPanelFilterControl(actionsPanel);


		orderFilterPanel.addPropertyChangeListener(PeriodFilterPanel.ACTION_resetFilter, new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				AListUpdater listUpdater = (AListUpdater) orderExplorer.getListNaviTable().getListUpdater();
				if (listUpdater != null) {
					listUpdater.getSearchFilter().reset();
					listUpdater.adjustFilter();
					orderExplorer.getListNaviTable().reload();
				}
			}
		});

		orderFilterPanel.addPropertyChangeListener(PeriodFilterPanel.ACTION_applyFilter, new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				AListUpdater listUpdater = (AListUpdater) orderExplorer.getListNaviTable().getListUpdater();
				if (listUpdater != null) {
					listUpdater.getSearchFilter().reset();
					listUpdater.adjustFilter();
					listUpdater.getSearchFilter().addAllCriterion(orderFilterPanel.getCriterions());
					orderExplorer.getListNaviTable().reload();
				}
			}
		});

	}
}
