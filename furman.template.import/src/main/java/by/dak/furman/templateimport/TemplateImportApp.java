package by.dak.furman.templateimport;

import bibliothek.gui.dock.ToolbarItemDockable;
import by.dak.furman.templateimport.service.ITemplateImportService;
import by.dak.furman.templateimport.swing.MainPanel;
import by.dak.furman.templateimport.swing.category.CategoryPanelController;
import by.dak.furman.templateimport.swing.nodes.AValueNode;
import by.dak.furman.templateimport.swing.nodes.NodeFactory;
import by.dak.furman.templateimport.swing.template.TemplatePanelController;
import by.dak.furman.templateimport.values.Category;
import by.dak.furman.templateimport.values.Template;
import by.dak.furman.templateimport.values.TemplateCategory;
import by.dak.persistence.FacadeContext;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.Task;
import org.jdesktop.swingx.action.AbstractActionExt;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;

public class TemplateImportApp extends SingleFrameApplication {

	private MainPanel mainPanel;
	private CategoryPanelController categoryPanelController;
	private TemplatePanelController templatePanelController;
	private String baseDirPath;

	private ApplicationContext applicationContext;

	private NodeFactory nodeFactory = new NodeFactory();

	public TemplateImportApp() {
		//getContext().getResourceManager().setResourceFolder(StringUtils.EMPTY);
	}

	public static void main(String[] args) {
		Application.launch(TemplateImportApp.class, args);

	}

	@Override
	protected void initialize(String[] args) {
		baseDirPath = args[0];

		applicationContext = new ClassPathXmlApplicationContext("TemplateImportApp.xml");
		FacadeContext.setApplicationContext(applicationContext);

		Locale.setDefault(new Locale("ru", "RU"));
	}

	private MainPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new MainPanel();
			mainPanel.setResourceMap(getContext().getResourceMap(MainPanel.class));
			mainPanel.init();
			mainPanel.addAction(getRefreshCategories());
			mainPanel.addAction(getImportCategories());
		}
		return mainPanel;
	}

	@Override
	protected void startup() {
		Container content = getMainFrame().getContentPane();
		content.setLayout(new BorderLayout());
		content.add(getMainPanel());
		show(getMainView());
		getContext().getTaskService().execute(new Task(this) {
			@Override
			protected Object doInBackground() throws Exception {

				getCategoryPanelController().loadCategories();
				return null;
			}
		});
	}

	public CategoryPanelController getCategoryPanelController() {
		if (categoryPanelController == null) {
			categoryPanelController = new CategoryPanelController();
			categoryPanelController.setView(getMainPanel().getCategoryPanel());
			categoryPanelController.setTemplatePanelController(getTemplatePanelController());
			fillCommonProperties(categoryPanelController);
			categoryPanelController.init();
		}
		return categoryPanelController;
	}

	private void fillCommonProperties(AbstractController controller) {
		controller.setBaseDirPath(baseDirPath);
		controller.setResourceMap(getContext().getResourceMap(CategoryPanelController.class));
		controller.setContext(getContext());
		controller.setNodeFactory(nodeFactory);
	}

	public TemplatePanelController getTemplatePanelController() {
		if (templatePanelController == null) {
			templatePanelController = new TemplatePanelController();
			templatePanelController.setView(getMainPanel().getTemplatePanel());
			fillCommonProperties(templatePanelController);
			templatePanelController.init();
		}
		return templatePanelController;
	}

	public Action getRefreshCategories() {
		AbstractActionExt action = new AbstractActionExt() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getContext().getTaskService().execute(new Task(TemplateImportApp.this) {
					@Override
					protected Object doInBackground() throws Exception {
						getCategoryPanelController().loadCategories();
						return null;
					}
				});
			}
		};
		action.setLargeIcon(getContext().getResourceMap().getIcon("icon.refresh"));
		action.setSmallIcon(getContext().getResourceMap().getIcon("icon.refresh"));
		action.setLongDescription(getContext().getResourceMap().getString("label.refreshBase"));
		return action;
	}

	public Action getImportCategories() {
		AbstractActionExt action = new AbstractActionExt() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getContext().getTaskService().execute(new Task(TemplateImportApp.this) {
					@Override
					protected Object doInBackground() throws Exception {
						Object node = getCategoryPanelController().getView().getSelectedNode();
						if (node instanceof AValueNode) {
							ITemplateImportService templateImportService = applicationContext.getBean(ITemplateImportService.class);
							Object value = ((AValueNode) node).getValue();
							if (value instanceof Category) {
								templateImportService.importCategory((Category) value);
							} else if (value instanceof TemplateCategory) {
								templateImportService.importTemplateCategory((TemplateCategory) value);
							} else if (value instanceof Template) {
								templateImportService.importTemplate((Template) value);
							}
						}
						return null;
					}

					public void addProgress() {
						ToolbarItemDockable item = new ToolbarItemDockable();
						//item.setComponent(new ProgressBarPanel(), ExpandedState.SHRUNK);
						getMainPanel().getToolbarStation().drag(item);
					}
				});
			}
		};
		action.setLargeIcon(getContext().getResourceMap().getIcon("icon.import"));
		action.setSmallIcon(getContext().getResourceMap().getIcon("icon.import"));
		action.setLongDescription(getContext().getResourceMap().getString("label.import"));
		return action;
	}
}
