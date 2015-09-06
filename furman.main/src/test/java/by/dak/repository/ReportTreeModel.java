package by.dak.repository;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * @author dkoyro
 * @version 0.1 04.03.2009 13:11:42
 */
public class ReportTreeModel implements TreeModel
{
    private TreeNode root;

    public ReportTreeModel(TreeNode root)
    {
        this.root = root;
    }

    public Object getRoot()
    {
        return root;
    }

    public Object getChild(Object parent, int index)
    {
        return ((DefaultMutableTreeNode) parent).getChildAt(index);
    }

    public int getChildCount(Object parent)
    {
        return ((DefaultMutableTreeNode) parent).getChildCount();
    }

    public boolean isLeaf(Object node)
    {
        return ((DefaultMutableTreeNode) node).isLeaf();
    }

    public void valueForPathChanged(TreePath path, Object newValue)
    {

    }

    public int getIndexOfChild(Object parent, Object child)
    {
        return ((DefaultMutableTreeNode) parent).getIndex(((DefaultMutableTreeNode) child));
    }

    public void addTreeModelListener(TreeModelListener l)
    {

    }

    public void removeTreeModelListener(TreeModelListener l)
    {

    }
}
