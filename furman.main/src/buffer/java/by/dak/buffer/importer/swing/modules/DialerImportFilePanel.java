package by.dak.buffer.importer.swing.modules;

import by.dak.buffer.importer.DilerImportFile;
import by.dak.buffer.importer.OrderImporter;
import by.dak.buffer.importer.swing.tab.DilerImportFileTab;
import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.DModPanel;
import by.dak.cutting.swing.ValueSave;
import by.dak.persistence.FacadeContext;

import javax.swing.*;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 23.11.2010
 * Time: 18:18:01
 * To change this template use File | Settings | File Templates.
 */
public class DialerImportFilePanel extends DModPanel<DilerImportFile> {
	private DilerImportFileTab dialerImportFileTab = null;

	public DialerImportFilePanel() {
		init();
		addTabs();
	}


	private void init() {
		setValueSave(new DialerImporter());
		setShowOkCancel(true);
		dialerImportFileTab = new DilerImportFileTab();
	}

	@Override
	protected void addTabs() {
		addTab(getTitle(), dialerImportFileTab);
	}

	private class DialerImporter implements ValueSave<DilerImportFile> {
		@Override
		public void save(DilerImportFile value) {
			try {
				SwingWorker swingWorker = new SwingWorker() {

					@Override
					protected Object doInBackground() throws Exception {
						Connection connection = FacadeContext.getJDBCConnection();

						OrderImporter orderImporter = new OrderImporter();
						orderImporter.setZipFile(getValue().getSelectedFile());
						orderImporter.setConnection(connection);
						orderImporter.execute();
						orderImporter.getDilerOrder().setCustomer(getValue().getCustomer());
						orderImporter.getDilerOrder().setCreatedDailySheet(FacadeContext.getDailysheetFacade().loadCurrentDailysheet());
						orderImporter.saveContext();

						return null;
					}
				};
				DialogShowers.showWaitDialog(swingWorker, DialerImportFilePanel.this);
			} catch (Exception e) {
				FacadeContext.getExceptionHandler().handle(e);
			}
		}
	}
}
