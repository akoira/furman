package by.dak.template.swing.action;

import by.dak.cutting.DialogShowers;
import by.dak.persistence.FacadeContext;
import by.dak.swing.explorer.ExplorerPanel;
import by.dak.template.TemplateOrder;
import by.dak.template.swing.TemplateExplorerPanel;
import by.dak.template.swing.tree.ACategoryNode;
import by.dak.template.swing.tree.CategoryTreeCellEditor;
import by.dak.template.swing.tree.RootNode;
import org.jdesktop.application.Application;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * User: akoyro
 * Date: 25.03.11
 * Time: 13:18
 */
public class ShowTemplatesExplorerAction extends AbstractAction
{
    private static final DataFlavor DF_TemplateOrder = new DataFlavor(TemplateOrder.class, "TemplateOrder");

    @Override
    public void actionPerformed(ActionEvent e)
    {
        action();
    }

    public void action()
    {
        final TemplateExplorerPanel explorerPanel = new TemplateExplorerPanel();
        explorerPanel.getTreePanel().getTree().setRootVisible(false);
        explorerPanel.getTreePanel().getTree().setEditable(true);
        explorerPanel.getTreePanel().getTree().setCellEditor(
                new CategoryTreeCellEditor(explorerPanel.getTreePanel().getTree(),
                        new DefaultTreeCellRenderer()));

        RootNode rootNode = new RootNode();
        rootNode.setContext(Application.getInstance().getContext());
        rootNode.init();

        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        explorerPanel.getTreePanel().getTree().setModel(treeModel);
        explorerPanel.getTreePanel().getTree().setSelectionPath(new TreePath(treeModel.getRoot()));

        explorerPanel.getListNaviTable().getTable().setDragEnabled(true);

        explorerPanel.getListNaviTable().getTable().setTransferHandler(createTableTransferHandler(explorerPanel));
        explorerPanel.getTreePanel().getTree().setTransferHandler(createTreeTransferHandler(explorerPanel));

        DialogShowers.showBy(explorerPanel, false);

        ActionLoadCategories loadCategories = new ActionLoadCategories();
        loadCategories.setCategoryFacade(FacadeContext.getCategoryFacade());
        loadCategories.setModel(treeModel);
        Application.getInstance().getContext().getTaskService().execute(loadCategories);
    }

    private TransferHandler createTreeTransferHandler(final ExplorerPanel explorerPanel)
    {
        return new TransferHandler()
        {
            @Override
            public int getSourceActions(JComponent c)
            {
                return TransferHandler.COPY_OR_MOVE;
            }

            @Override
            public boolean canImport(TransferSupport support)
            {
                Point point = support.getDropLocation().getDropPoint();
                if (point != null)
                {
                    TreePath treePath = explorerPanel.getTreePanel().getTree().getClosestPathForLocation(support.getDropLocation().getDropPoint().x, support.getDropLocation().getDropPoint().y);
                    return treePath != null &&
                            support.getTransferable().isDataFlavorSupported(DF_TemplateOrder);
                }
                else
                {
                    return false;
                }
            }

            @Override
            public boolean importData(JComponent comp, Transferable t)
            {
                try
                {
                    TemplateOrder templateOrder = (TemplateOrder) t.getTransferData(DF_TemplateOrder);
                    if (explorerPanel.getTreePanel().getTree().getSelectionPath() != null)
                    {
                        TreePath currentPath = explorerPanel.getTreePanel().getTree().getSelectionPath();
                        templateOrder.setCategory(((ACategoryNode) currentPath.getLastPathComponent()).getCategory());
                        FacadeContext.getTemplateOrderFacade().save(templateOrder);
                        explorerPanel.getTreePanel().getTree().clearSelection();
                        explorerPanel.getTreePanel().getTree().setSelectionPath(currentPath);
                    }
                }
                catch (Throwable e)
                {
                    return false;
                }
                return true;
            }
        };
    }

    private TransferHandler createTableTransferHandler(final ExplorerPanel explorerPanel)
    {
        return new TransferHandler()
        {
            @Override
            public int getSourceActions(JComponent c)
            {
                return TransferHandler.COPY_OR_MOVE;
            }

            @Override
            protected Transferable createTransferable(final JComponent c)
            {
                final TemplateOrder templateOrder = (TemplateOrder) explorerPanel.getListNaviTable().getListUpdater().getList().get(explorerPanel.getListNaviTable().getTable().getSelectedRow());
                Transferable transferable = new Transferable()
                {
                    @Override
                    public DataFlavor[] getTransferDataFlavors()
                    {
                        return new DataFlavor[]{DF_TemplateOrder};
                    }

                    @Override
                    public boolean isDataFlavorSupported(DataFlavor flavor)
                    {
                        return flavor == DF_TemplateOrder;
                    }

                    @Override
                    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException
                    {
                        if (flavor == DF_TemplateOrder)
                        {
                            return templateOrder;
                        }
                        return null;
                    }
                };
                return transferable;
            }
        };
    }
}
