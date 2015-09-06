package by.dak.template.swing.tree;

import by.dak.category.Category;
import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.ItemSelector;
import by.dak.report.PriceReportType;
import by.dak.report.ReportProperties;
import by.dak.report.swing.ReportPropertiesTab;
import by.dak.swing.WindowCallback;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.ListUpdater;
import by.dak.swing.table.ListUpdaterProvider;
import by.dak.swing.tree.ATreeNode;
import by.dak.template.TemplateOrder;
import by.dak.template.swing.TemplateOrdersUpdater;
import org.jdesktop.application.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 * User: akoyro
 * Date: 25.03.11
 * Time: 13:10
 */
public abstract class ACategoryNode extends ATreeNode implements ListUpdaterProvider<TemplateOrder> {
	public static final String PROPERTY_leafChildrenIcon = "leafChildrenIcon";
	public static final String PROPERTY_openChildrenIcon = "openChildrenIcon";
	public static final String PROPERTY_closedChildrenIcon = "closedChildrenIcon";
	private AListUpdater<TemplateOrder> listUpdater;

	public void init() {
		super.init();
		listUpdater = new TemplateOrdersUpdater(getCategory());
	}

	public void updateIcons() {
		if (getChildCount() > 0) {
			setLeafIcon(getResourceMap().getIcon(PROPERTY_leafChildrenIcon));
			setOpenIcon(getResourceMap().getIcon(PROPERTY_openChildrenIcon));
			setClosedIcon(getResourceMap().getIcon(PROPERTY_closedChildrenIcon));
		}
	}

	public Category getCategory() {
		return (Category) getUserObject();
	}

	public void setCategory(Category category) {
		setUserObject(category);
	}

	@Action
	public void print() {
//        AValueTab<ReportProperties> valueTab = new AValueTab<ReportProperties>();
//        valueTab.setValueClass(ReportProperties.class);
//        valueTab.init();
		ReportPropertiesTab valueTab = new ReportPropertiesTab();
		valueTab.init();
		valueTab.setValue(new ReportProperties());
		final ItemSelector itemSelector = (ItemSelector) valueTab.getEditors().get("reportType");
		final WindowCallback windowCallback = new WindowCallback();
		itemSelector.getComboBoxItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				windowCallback.dispose();
				String reportPath = "by/dak/template/report/template.report." +
						((PriceReportType) itemSelector.getComboBoxItem().getSelectedItem()).name() +
						".jrxml";
				HashMap map = new HashMap();
				map.put("START_CATEGORY", new BigDecimal(getCategory() != null ? getCategory().getId() : 0));
				map.put("END_CATEGORY", new BigDecimal(getCategory() != null ? getCategory().getId() : Long.MAX_VALUE));
				DialogShowers.showJasperViewer(getTree(), "template.report", "template.report", getResourceMap(), reportPath, map);

			}
		});
		DialogShowers.showBy(valueTab, getTree(), windowCallback, true, true);
	}

	@Override
	public ListUpdater<TemplateOrder> getListUpdater() {
		return listUpdater;
	}
}
