/*
 * CuttingView.java
 */
package by.dak.cutting;

import by.dak.buffer.importer.DilerImportFile;
import by.dak.buffer.importer.swing.modules.DialerImportFilePanel;
import by.dak.buffer.statistic.swing.DilerOrderStatisticsPanel;
import by.dak.buffer.statistic.swing.RootOrderTableReloader;
import by.dak.cutting.currency.swing.CurrencyListTab;
import by.dak.cutting.statistics.swing.StatisticsPanel;
import by.dak.cutting.swing.archive.OrderExplorer;
import by.dak.cutting.swing.archive.tree.RootNode;
import by.dak.cutting.swing.struct.StructureExplorer;
import by.dak.delivery.swing.DeliveryExplorer;
import by.dak.order.swing.IOrderWizardDelegator;
import by.dak.ordergroup.swing.OrderGroupsTab;
import by.dak.persistence.Exporter;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.Importer;
import by.dak.persistence.entities.Order;
import by.dak.persistence.entities.OrderItem;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.swing.ActionsPanel;
import by.dak.template.swing.action.ShowTemplatesExplorerAction;
import by.dak.utils.convert.StringValueAnnotationProcessor;
import nl.jj.swingx.gui.modal.JModalFrame;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.application.Action;
import org.jdesktop.application.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The application's main frame.
 */
public class CuttingView extends FrameView {
	private ResourceMap resourceMap = Application.getInstance(CuttingApp.class).getContext().getResourceMap(
			CuttingView.class);
	ActionMap actionMap = Application.getInstance(CuttingApp.class).getContext().getActionMap(CuttingView.class,
			this);

	private static final String[] EXPORT_TABLES = StringUtils.split("MANUFACTURER,PROVIDER,FURNITURE_CODE,FURNITURE_TYPE,FURNITURE_TYPE_LINK,FURNITURE_CODE_LINK,PRICE", ',');

	public CuttingView(SingleFrameApplication app) {
		super(app);
		String title = getContext().getResourceMap().getString("Application.title");
		JModalFrame frame = new JModalFrame(title);
		frame.setName("mainFrame");
		setFrame(frame);

		initComponents();

		// status bar initialization - message timeout, idle icon and busy animation, etc
		ResourceMap resourceMap = getResourceMap();
		int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
		messageTimer = new Timer(messageTimeout, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				statusMessageLabel.setText("");
			}
		});
		messageTimer.setRepeats(false);
		int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
		for (int i = 0; i < busyIcons.length; i++) {
			busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
		}
		busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
				statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
			}
		});
		idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
		statusAnimationLabel.setIcon(idleIcon);
		progressBar.setVisible(false);

		// connecting action tasks to status bar via TaskMonitor
		TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
		taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
			public void propertyChange(java.beans.PropertyChangeEvent evt) {
				String propertyName = evt.getPropertyName();
				if ("started".equals(propertyName)) {
					if (!busyIconTimer.isRunning()) {
						statusAnimationLabel.setIcon(busyIcons[0]);
						busyIconIndex = 0;
						busyIconTimer.start();
					}
					progressBar.setVisible(true);
					progressBar.setIndeterminate(true);
				} else if ("done".equals(propertyName)) {
					busyIconTimer.stop();
					statusAnimationLabel.setIcon(idleIcon);
					progressBar.setVisible(false);
					progressBar.setValue(0);
				} else if ("message".equals(propertyName)) {
					String text = (String) evt.getNewValue();
					statusMessageLabel.setText(text == null ? "" : text);
					messageTimer.restart();
				} else if ("progress".equals(propertyName)) {
					int value = (Integer) evt.getNewValue();
					progressBar.setVisible(true);
					progressBar.setIndeterminate(false);
					progressBar.setValue(value);
				}
			}
		});
	}

	@Action
	public void showImportDialog() {
		JFileChooser jFileChooser = new JFileChooser(System.getProperty("user.home"));
		jFileChooser.setDialogTitle(getResourceMap().getString("dialog.import.title"));
		jFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFileChooser.setAcceptAllFileFilterUsed(false);
		jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("ZIP", "zip"));
		int resultValue = jFileChooser.showOpenDialog(getFrame());
		File file = jFileChooser.getSelectedFile();
		if (resultValue != JFileChooser.CANCEL_OPTION) {
			try {
				Importer importer = new Importer();
				Connection connection = FacadeContext.getJDBCConnection();
				importer.setConnection(connection);
				importer.setZipFile(file);
				importer.setTables(EXPORT_TABLES);
				importer.execute();
				JOptionPane.showMessageDialog(getFrame(), file.getAbsolutePath(), "Import", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(getFrame(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				Logger.getLogger(CuttingView.class.getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	@Action
	public void showExportDialog() {
		JFileChooser jFileChooser = new JFileChooser(System.getProperty("user.home"));
		jFileChooser.setDialogTitle(getResourceMap().getString("dialog.export.title"));
		jFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int resultValue = jFileChooser.showOpenDialog(getFrame());
		File dir = jFileChooser.getSelectedFile();
		if (resultValue != JFileChooser.CANCEL_OPTION) {
			try {
				Exporter exporter = new Exporter();
				exporter.setConnection(FacadeContext.getJDBCConnection());
				File file = new File(dir,
						new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()) + ".zip");
				exporter.setZipFile(file);
				exporter.setTables(EXPORT_TABLES);
				exporter.execute();
				JOptionPane.showMessageDialog(getFrame(), file.getAbsolutePath(), "Export", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(getFrame(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				Logger.getLogger(CuttingView.class.getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	@Action
	public void showPricesDialog() {
	}

	@Action
	public void showAboutBox() {
		if (aboutBox == null) {
			JFrame mainFrame = CuttingApp.getApplication().getMainFrame();
			aboutBox = new CuttingAboutBox(mainFrame);
			aboutBox.setLocationRelativeTo(mainFrame);
		}
		CuttingApp.getApplication().show(aboutBox);
	}

	@Action
	public void showEmployeesDialog() {
	}

	@Action
	public void showDesignersDialog() {
	}

	@Action
	public void showCustomersDialog() {
	}

	@Action
	public void showTextureDialog() {
	}

	@Action
	public void showManufacturersDialog() {

	}

	@Action
	public void showProvidersDialog() {
		StructureExplorer structureExplorer = new StructureExplorer();
		DialogShowers.showBy(structureExplorer, false);
	}

	@Action
	public void showServicesDialog() {
	}

	@Action
	public void showBoardDefsDialog() {
		DialogShowers.showTypesExplorer();
	}

	@Action
	public void showProfileDefDialog() {
		DialogShowers.getProfileDefDialogShower().show();
	}


	@Action
	public void showBordersDialog() {
	}


	@Action
	public void showBorderDefDialog() {
	}

	@Action
	public void showFurnituresDialog() {
	}

	@Action
	public void showDepartmentsDialog() {
	}

	@Action
	public void showShiftsDialog() {
	}

	@Action
	public void showDailyWizard() {
		NewDayManager newDayManager = new NewDayManager();
		newDayManager.checkDailysheet();
	}

	@Action
	public void showOrderArchiveDialog() {
	}

	@Action
	@Deprecated
	public void showReportsPanel() {
		// TODO what is the source of the data ?
		// ReportsModelImpl data = null;
		// FrameView mainFrame = CuttingApp.getApplication().getMainView();
		// mainFrame.getFrame().add(new ReportsPanel(data, CuttingApp.getApplication()).getComponent(), 0);
		// CuttingApp.getApplication().show(mainFrame);
	}

	@Action
	public void showDeliveryWizard() {
		DeliveryExplorer deliveryExplorer = new DeliveryExplorer();
		DialogShowers.showBy(deliveryExplorer, false);
	}

	@Action
	public void showNewOrderWizard() {
		//поднимается панель и нажимается кнопка на новый заказ
		RootNode rootNode = (RootNode) ((OrderExplorer) archiveOrdersPanel.getPanelResult()).getTreePanel().
				getTree().getModel().getRoot();
		rootNode.getListUpdater().getNewEditDeleteActions().newValue();

//        NewAction action = new NewAction();
//        action.actionPerformed(null);
	}

	@Action
	public void showStoreExplorer() {
		DialogShowers.showStoreExplorer();
	}

	@Action
	public void showTypesExplorer() {
		DialogShowers.showTypesExplorer();
	}

	@Action
	public void showOrderExplorer() {
		DialogShowers.showOrderExplorer();
	}

	@Action
	public void showPriceBoardDef() {
		DialogShowers.showPriceJasperViewer(MaterialType.board.name());
	}

	@Action
	public void showPriceBorderDef() {
		DialogShowers.showPriceJasperViewer(MaterialType.border.name());
	}

	@Action
	public void showPriceFurnitureType() {
		DialogShowers.showPriceJasperViewer(MaterialType.furniture.name());
	}

	@Action
	public void showPriceService() {
		DialogShowers.showPriceJasperViewer("service");
	}


	@Action
	public void showOrderBoard() {
		DialogShowers.showOrderJasperViewer(MaterialType.board);
	}

	@Action
	public void showOrderBorder() {
		OrderGroupsTab orderGroupsTab = new OrderGroupsTab();
		orderGroupsTab.init();
		orderGroupsTab.setValue(FacadeContext.getDailysheetFacade().loadCurrentDailysheet());

		ActionsPanel actionsPanel = new ActionsPanel();
		actionsPanel.setContentComponent(orderGroupsTab);
		actionsPanel.setSourceActionMap(orderGroupsTab.getListNaviTable().getListUpdater().getNewEditDeleteActions().getActionMap());
		actionsPanel.setActions(orderGroupsTab.getListNaviTable().getListUpdater().getNewEditDeleteActions().getActionNames());
		actionsPanel.setResourceMap(orderGroupsTab.getResourceMap());
		actionsPanel.init();
		DialogShowers.showBy(actionsPanel,
				CuttingApp.getApplication().getMainFrame(),
				false);


//        StoreBookingTab storeBookingTab = new StoreBookingTab();
//        storeBookingTab.init();
//        storeBookingTab.setValue(new StoreBooking());
//
//        DialogShowers.showOrderJasperViewer(MaterialType.border);
	}

	@Action
	public void showOrderFurniture() {
		DialogShowers.showOrderJasperViewer(MaterialType.furniture);
	}

	@Action
	public void showCurrency() {
		CurrencyListTab currencyListTab = new CurrencyListTab();
		currencyListTab.init();
		currencyListTab.setValue(FacadeContext.getDailysheetFacade().loadCurrentDailysheet());
		DialogShowers.showBy(currencyListTab, CuttingApp.getApplication().getMainFrame(), false);
	}

	@Action
	public void showStatistics() {
		StatisticsPanel panel = new StatisticsPanel();
		DialogShowers.showBy(panel, CuttingApp.getApplication().getMainFrame(), false);
	}

	@Action
	public void showDilerImportFileDialog() {
		DialerImportFilePanel panel = new DialerImportFilePanel();
		panel.setValue(new DilerImportFile());
		DialogShowers.showBy(panel, true);
	}

	@Action
	public void showDilerOrderStatisticDialog() {
		DilerOrderStatisticsPanel dilerOrderStatisticsPanel = new DilerOrderStatisticsPanel();
		IOrderWizardDelegator orderWizardDelegator = new RootOrderTableReloader(((OrderExplorer) archiveOrdersPanel.getPanelResult()).getListNaviTable());
		dilerOrderStatisticsPanel.setOrderWizardDelegator(orderWizardDelegator);
		DialogShowers.showBy(dilerOrderStatisticsPanel, CuttingApp.getApplication().getMainFrame(), false);
	}

	@Action
	public void showCuttersDialog() {
	}

	@Action
	public void showTemplatesDialog() {
		new ShowTemplatesExplorerAction().action();
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
	 * content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		mainPanel = new javax.swing.JPanel();
		archiveOrdersPanel = new by.dak.cutting.swing.archive.ArchiveOrdersPanel();
		menuBar = new javax.swing.JMenuBar();
		javax.swing.JMenu fileMenu = new javax.swing.JMenu();
		newOrderMenuItem = new javax.swing.JMenuItem();
		miStatistics = new javax.swing.JMenuItem();
		jSeparator3 = new javax.swing.JSeparator();
		menuItemDailyWizard = new javax.swing.JMenuItem();
		jSeparator2 = new javax.swing.JSeparator();
		miDilerImportFile = new javax.swing.JMenuItem();
		miDilerOrderStatistic = new javax.swing.JMenuItem();
		jSeparator8 = new javax.swing.JPopupMenu.Separator();
		miImport = new javax.swing.JMenuItem();
		miExport = new javax.swing.JMenuItem();
		jSeparator7 = new javax.swing.JPopupMenu.Separator();
		javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
		directoriesMenu = new javax.swing.JMenu();
		miDictionaries = new javax.swing.JMenuItem();
		menuStore = new javax.swing.JMenu();
		jMenuItem4 = new javax.swing.JMenuItem();
		jSeparator5 = new javax.swing.JSeparator();
		miOrderBorder = new javax.swing.JMenuItem();
		jSeparator6 = new javax.swing.JPopupMenu.Separator();
		miBoards = new javax.swing.JMenuItem();
		menuPrices = new javax.swing.JMenu();
		miTypesExplorer = new javax.swing.JMenuItem();
		miCurrency = new javax.swing.JMenuItem();
		jSeparator10 = new javax.swing.JPopupMenu.Separator();
		miTemplates = new javax.swing.JMenuItem();
		jSeparator4 = new javax.swing.JPopupMenu.Separator();
		miPriceBoardDef = new javax.swing.JMenuItem();
		miPriceBorderDef = new javax.swing.JMenuItem();
		miPriceFurnitureType = new javax.swing.JMenuItem();
		miPriceService = new javax.swing.JMenuItem();
		javax.swing.JMenu helpMenu = new javax.swing.JMenu();
		javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
		statusPanel = new javax.swing.JPanel();
		javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
		statusMessageLabel = new javax.swing.JLabel();
		statusAnimationLabel = new javax.swing.JLabel();
		dailyStatusPanel = new by.dak.cutting.DailyStatusPanel();
		progressBar = new javax.swing.JProgressBar();

		mainPanel.setName("mainPanel"); // NOI18N

		archiveOrdersPanel.setName("archiveOrdersPanel"); // NOI18N

		javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout.setHorizontalGroup(
				mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(mainPanelLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(archiveOrdersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
								.addContainerGap())
		);
		mainPanelLayout.setVerticalGroup(
				mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(mainPanelLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(archiveOrdersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
								.addContainerGap())
		);

		menuBar.setName("menuBar"); // NOI18N

		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(CuttingView.class);
		fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
		fileMenu.setName("fileMenu"); // NOI18N

		javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(CuttingView.class, this);
		newOrderMenuItem.setAction(actionMap.get("showNewOrderWizard")); // NOI18N
		newOrderMenuItem.setText(resourceMap.getString("newOrderMenuItem.text")); // NOI18N
		newOrderMenuItem.setName("newOrderMenuItem"); // NOI18N
		fileMenu.add(newOrderMenuItem);

		miStatistics.setAction(actionMap.get("showStatistics")); // NOI18N
		miStatistics.setText(resourceMap.getString("miStatistics.text")); // NOI18N
		miStatistics.setName("miStatistics"); // NOI18N
		fileMenu.add(miStatistics);

		jSeparator3.setName("jSeparator3"); // NOI18N
		fileMenu.add(jSeparator3);

		menuItemDailyWizard.setAction(actionMap.get("showDailyWizard")); // NOI18N
		menuItemDailyWizard.setText(resourceMap.getString("menuItemDailyWizard.text")); // NOI18N
		menuItemDailyWizard.setName("menuItemDailyWizard"); // NOI18N
		fileMenu.add(menuItemDailyWizard);

		jSeparator2.setName("jSeparator2"); // NOI18N
		fileMenu.add(jSeparator2);

		miDilerImportFile.setAction(actionMap.get("showDilerImportFileDialog")); // NOI18N
		miDilerImportFile.setText(resourceMap.getString("miDilerImportFile.text")); // NOI18N
		miDilerImportFile.setName("miDilerImportFile"); // NOI18N
		fileMenu.add(miDilerImportFile);

		miDilerOrderStatistic.setAction(actionMap.get("showDilerOrderStatisticDialog")); // NOI18N
		miDilerOrderStatistic.setText(resourceMap.getString("miDilerOrderStatistic.text")); // NOI18N
		miDilerOrderStatistic.setName("miDilerOrderStatistic"); // NOI18N
		fileMenu.add(miDilerOrderStatistic);

		jSeparator8.setName("jSeparator8"); // NOI18N
		fileMenu.add(jSeparator8);

		miImport.setAction(actionMap.get("showImportDialog")); // NOI18N
		miImport.setName("miImport"); // NOI18N
		fileMenu.add(miImport);

		miExport.setAction(actionMap.get("showExportDialog")); // NOI18N
		miExport.setName("miExport"); // NOI18N
		fileMenu.add(miExport);

		jSeparator7.setName("jSeparator7"); // NOI18N
		fileMenu.add(jSeparator7);

		exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
		exitMenuItem.setName("exitMenuItem"); // NOI18N
		fileMenu.add(exitMenuItem);

		menuBar.add(fileMenu);

		directoriesMenu.setAction(actionMap.get("showTextureDialog")); // NOI18N
		directoriesMenu.setText(resourceMap.getString("directoriesMenu.text")); // NOI18N
		directoriesMenu.setName("directoriesMenu"); // NOI18N

		miDictionaries.setAction(actionMap.get("showProvidersDialog")); // NOI18N
		miDictionaries.setText(resourceMap.getString("providersMenuItem.text")); // NOI18N
		miDictionaries.setName("providersMenuItem"); // NOI18N
		directoriesMenu.add(miDictionaries);

		menuBar.add(directoriesMenu);

		menuStore.setText(resourceMap.getString("menuStore.text")); // NOI18N
		menuStore.setName("menuStore"); // NOI18N

		jMenuItem4.setAction(actionMap.get("showDeliveryWizard")); // NOI18N
		jMenuItem4.setText(resourceMap.getString("deliveryMenuItem.text")); // NOI18N
		jMenuItem4.setName("deliveryMenuItem"); // NOI18N
		menuStore.add(jMenuItem4);

		jSeparator5.setName("jSeparator5"); // NOI18N
		menuStore.add(jSeparator5);

		miOrderBorder.setAction(actionMap.get("showOrderBorder")); // NOI18N
		miOrderBorder.setText(resourceMap.getString("miOrderBorder.text")); // NOI18N
		miOrderBorder.setName("miOrderBorder"); // NOI18N
		menuStore.add(miOrderBorder);

		jSeparator6.setName("jSeparator6"); // NOI18N
		menuStore.add(jSeparator6);

		miBoards.setAction(actionMap.get("showStoreExplorer")); // NOI18N
		miBoards.setText(resourceMap.getString("miStore.text")); // NOI18N
		miBoards.setName("miStore"); // NOI18N
		menuStore.add(miBoards);

		menuBar.add(menuStore);

		menuPrices.setText(resourceMap.getString("menuPrices.text")); // NOI18N
		menuPrices.setName("menuPrices"); // NOI18N

		miTypesExplorer.setAction(actionMap.get("showTypesExplorer")); // NOI18N
		miTypesExplorer.setText(resourceMap.getString("miTypesExplorer.text")); // NOI18N
		miTypesExplorer.setName("miTypesExplorer"); // NOI18N
		menuPrices.add(miTypesExplorer);

		miCurrency.setAction(actionMap.get("showCurrency")); // NOI18N
		miCurrency.setText(resourceMap.getString("miCurrency.text")); // NOI18N
		miCurrency.setName("miCurrency"); // NOI18N
		menuPrices.add(miCurrency);

		jSeparator10.setName("jSeparator10"); // NOI18N
		menuPrices.add(jSeparator10);

		miTemplates.setAction(actionMap.get("showTemplatesDialog")); // NOI18N
		miTemplates.setText(resourceMap.getString("miTemplates.text")); // NOI18N
		miTemplates.setName("miTemplates"); // NOI18N
		menuPrices.add(miTemplates);

		jSeparator4.setName("jSeparator4"); // NOI18N
		menuPrices.add(jSeparator4);

		miPriceBoardDef.setAction(actionMap.get("showPriceBoardDef")); // NOI18N
		miPriceBoardDef.setText(resourceMap.getString("miPriceBoardDef.text")); // NOI18N
		miPriceBoardDef.setName("miPriceBoardDef"); // NOI18N
		menuPrices.add(miPriceBoardDef);

		miPriceBorderDef.setAction(actionMap.get("showPriceBorderDef")); // NOI18N
		miPriceBorderDef.setText(resourceMap.getString("miPriceBorderDef.text")); // NOI18N
		miPriceBorderDef.setName("miPriceBorderDef"); // NOI18N
		menuPrices.add(miPriceBorderDef);

		miPriceFurnitureType.setAction(actionMap.get("showPriceFurnitureType")); // NOI18N
		miPriceFurnitureType.setText(resourceMap.getString("miPriceFurnitureType.text")); // NOI18N
		miPriceFurnitureType.setName("miPriceFurnitureType"); // NOI18N
		menuPrices.add(miPriceFurnitureType);

		miPriceService.setAction(actionMap.get("showPriceService")); // NOI18N
		miPriceService.setText(resourceMap.getString("miPriceService.text")); // NOI18N
		miPriceService.setName("miPriceService"); // NOI18N
		menuPrices.add(miPriceService);

		menuBar.add(menuPrices);

		helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
		helpMenu.setEnabled(false);
		helpMenu.setName("helpMenu"); // NOI18N

		aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
		aboutMenuItem.setName("aboutMenuItem"); // NOI18N
		helpMenu.add(aboutMenuItem);

		menuBar.add(helpMenu);

		statusPanel.setName("statusPanel"); // NOI18N

		statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

		statusMessageLabel.setName("statusMessageLabel"); // NOI18N

		statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

		dailyStatusPanel.setName("dailyStatusPanel"); // NOI18N

		progressBar.setName("progressBar"); // NOI18N

		javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
		statusPanel.setLayout(statusPanelLayout);
		statusPanelLayout.setHorizontalGroup(
				statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(statusPanelLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(dailyStatusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(5, 5, 5)
								.addComponent(statusMessageLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(544, Short.MAX_VALUE))
		);
		statusPanelLayout.setVerticalGroup(
				statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(statusPanelLayout.createSequentialGroup()
								.addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(statusPanelLayout.createSequentialGroup()
												.addGap(5, 5, 5)
												.addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(statusPanelLayout.createSequentialGroup()
												.addGap(6, 6, 6)
												.addComponent(statusMessageLabel))
										.addComponent(dailyStatusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap())
		);

		setComponent(mainPanel);
		setMenuBar(menuBar);
		setStatusBar(statusPanel);
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	by.dak.cutting.swing.archive.ArchiveOrdersPanel archiveOrdersPanel;
	by.dak.cutting.DailyStatusPanel dailyStatusPanel;
	javax.swing.JMenu directoriesMenu;
	javax.swing.JMenuItem jMenuItem4;
	javax.swing.JPopupMenu.Separator jSeparator10;
	javax.swing.JSeparator jSeparator2;
	javax.swing.JSeparator jSeparator3;
	javax.swing.JPopupMenu.Separator jSeparator4;
	javax.swing.JSeparator jSeparator5;
	javax.swing.JPopupMenu.Separator jSeparator6;
	javax.swing.JPopupMenu.Separator jSeparator7;
	javax.swing.JPopupMenu.Separator jSeparator8;
	javax.swing.JPanel mainPanel;
	javax.swing.JMenuBar menuBar;
	javax.swing.JMenuItem menuItemDailyWizard;
	javax.swing.JMenu menuPrices;
	javax.swing.JMenu menuStore;
	javax.swing.JMenuItem miBoards;
	javax.swing.JMenuItem miCurrency;
	javax.swing.JMenuItem miDictionaries;
	javax.swing.JMenuItem miDilerImportFile;
	javax.swing.JMenuItem miDilerOrderStatistic;
	javax.swing.JMenuItem miExport;
	javax.swing.JMenuItem miImport;
	javax.swing.JMenuItem miOrderBorder;
	javax.swing.JMenuItem miPriceBoardDef;
	javax.swing.JMenuItem miPriceBorderDef;
	javax.swing.JMenuItem miPriceFurnitureType;
	javax.swing.JMenuItem miPriceService;
	javax.swing.JMenuItem miStatistics;
	javax.swing.JMenuItem miTemplates;
	javax.swing.JMenuItem miTypesExplorer;
	javax.swing.JMenuItem newOrderMenuItem;
	javax.swing.JProgressBar progressBar;
	javax.swing.JLabel statusAnimationLabel;
	javax.swing.JLabel statusMessageLabel;
	javax.swing.JPanel statusPanel;
	// End of variables declaration//GEN-END:variables
	private final Timer messageTimer;
	private final Timer busyIconTimer;
	private final Icon idleIcon;
	private final Icon[] busyIcons = new Icon[15];
	private int busyIconIndex = 0;
	private JDialog aboutBox;
	private HashMap<Class<? extends JDialog>, JDialog> dialogsMap = new HashMap<Class<? extends JDialog>, JDialog>();


	public DailyStatusPanel getDailyStatusPanel() {
		return dailyStatusPanel;
	}

	public static class OrderCreator {
		public Order create() {
			String name = CuttingApp.getApplication().getContext().getResourceMap(CuttingView.class).getString(
					"default.order.name");
			Order order = FacadeContext.getOrderFacade().initNewOrderEntity(name);

			OrderItem orderItem = new OrderItem();
			orderItem.setName(StringValueAnnotationProcessor.getProcessor().convert(OrderItemType.first));
			orderItem.setType(OrderItemType.first);
			order.addOrderItem(orderItem);
			return order;
		}
	}


}
