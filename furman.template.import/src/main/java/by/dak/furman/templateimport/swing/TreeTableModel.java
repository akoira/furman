package by.dak.furman.templateimport.swing;

import by.dak.furman.templateimport.swing.nodes.AValueNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableNode;

import java.util.List;

/**
 * User: akoyro
 * Date: 10/7/13
 * Time: 10:27 PM
 */
public class TreeTableModel extends DefaultTreeTableModel
{
    public TreeTableModel()
    {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public TreeTableModel(TreeTableNode root)
    {
        super(root);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public TreeTableModel(TreeTableNode root, List<?> columnNames)
    {
        super(root, columnNames);    //To change body of overridden methods use File | Settings | File Templates.
    }


    public Class getColumnClass(Object node, int column)
    {
        if (node instanceof AValueNode)
        {
            if (column < ((AValueNode) node).getColumnCount())
                return ((AValueNode) node).getColumnClass(column);
        }
        return getColumnClass(column);
    }
}
