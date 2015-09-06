package by.dak.cutting.swing.order.cellcomponents.editors;

import by.dak.autocad.AutocadDelegator;
import by.dak.autocad.com.Application;
import by.dak.cutting.DialogShowers;
import by.dak.cutting.swing.order.controls.OrderDetailsControl;
import by.dak.cutting.swing.order.data.Milling;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.cutting.swing.order.models.OrderDetailsTableModel;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.persistence.FacadeContext;
import by.dak.swing.ButtonEditor;
import by.dak.swing.WindowCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

/**
 * User: akoyro
 * Date: 12.10.2010
 * Time: 21:45:50
 */
public class MillingCellEditor extends ButtonEditor {
	private ExecutorService executorService = Executors.newSingleThreadExecutor();

	private OrderDetailsDTO orderDetails;
	private OrderDetailsControl orderDetailsControl;
	private JTable jTable;

	public MillingCellEditor() {
		setActionListener(new AutocadAction());
	}

	@Override
	public Object getCellEditorValue() {
		return orderDetails.getMilling();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		jTable = table;
		orderDetails = ((OrderDetailsTableModel) table.getModel()).getRowBy(row);
		return super.getTableCellEditorComponent(table, value, isSelected, row, column);
	}

	public void setOrderDetailsControl(OrderDetailsControl orderDetailsControl) {
		this.orderDetailsControl = orderDetailsControl;
	}


	public class AutocadAction implements ActionListener {
		@org.jdesktop.application.Action
		public void saveMilling() {

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			final AutocadRunnable autocadRunnable = new AutocadRunnable();
			AutocadSwingWorker autocadSwingWorker = new AutocadSwingWorker(autocadRunnable);

			AtomicReference<AutocadDelegator> autocadDelegator = new AtomicReference<AutocadDelegator>(new AutocadDelegator() {
				@Override
				public void applicationStopped(Application application) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							Milling milling = (Milling) XstreamHelper.getInstance().fromXML(orderDetails.getMilling());
							if (milling != null && (milling.getCurveGluingLength() > 0 || milling.getDirectGluingLength() > 0)) {
								WindowCallback windowCallback = new WindowCallback();
								MillingBorderTextureTab millingBorderTextureTab = new MillingBorderTextureTab(orderDetails, milling, windowCallback);
								millingBorderTextureTab.init();
								DialogShowers.showBy(millingBorderTextureTab, jTable, windowCallback, true, true);
							}

							//we need this code to be sure that  milling will be save at once after editing.
							orderDetailsControl.saveOrderFurniture(orderDetails);
							stopCellEditing();
						}
					});
					autocadRunnable.setApplicationAlive(false);
				}

				@Override
				public void applicationStarting() {
					autocadRunnable.setApplicationAlive(true);
				}
			});
			FacadeContext.getAutocadFacade().setAutocadDelegator(autocadDelegator.get());
			DialogShowers.showWaitDialog(autocadSwingWorker, jTable);
		}
	}


	public class AutocadSwingWorker extends SwingWorker {
		private AutocadRunnable autocadRunnable;

		public AutocadSwingWorker(AutocadRunnable autocadRunnable) {
			this.autocadRunnable = autocadRunnable;
		}

		@Override
		protected Object doInBackground() throws Exception {
			try {
				Future future = executorService.submit(autocadRunnable);
				while (!future.isDone()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;
					}
				}
				return null;
			} catch (Throwable e) {
				throw new IllegalArgumentException(e);
			}
		}
	}


	public class AutocadRunnable implements Runnable {
		private boolean applicationAlive = false;

		@Override
		public void run() {
			try {
				FacadeContext.getAutocadFacade().startApplication(orderDetails);
				if (orderDetails.getMilling() == null) {
					FacadeContext.getAutocadFacade().newMilling(orderDetails);
				} else {
					FacadeContext.getAutocadFacade().openMilling(orderDetails);
				}

				while (isApplicationAlive()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;
					}
				}
			} catch (Throwable e) {
				FacadeContext.getExceptionHandler().handle(e);
			}
		}

		public synchronized boolean isApplicationAlive() {
			return applicationAlive;
		}

		public synchronized void setApplicationAlive(boolean applicationAlive) {
			this.applicationAlive = applicationAlive;
		}
	}
}
