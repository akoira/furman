package by.dak.swing.tree;

/**
 * User: akoyro
 * Date: 05.04.11
 * Time: 20:45
 */
public abstract class ARootNode extends ATreeNode
{
    public ARootNode()
    {
        setUserObject(getResourceMap().getString("node.name"));
        setClosedIcon(getResourceMap().getIcon("node.icon"));
        setLeafIcon(getResourceMap().getIcon("node.icon"));
        setOpenIcon(getResourceMap().getIcon("node.icon"));

    }
}
