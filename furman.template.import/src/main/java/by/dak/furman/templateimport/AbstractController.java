package by.dak.furman.templateimport;

import by.dak.furman.templateimport.swing.TreeTableModel;
import by.dak.furman.templateimport.swing.nodes.NodeFactory;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;

public class AbstractController<V extends JComponent>
{
    private ResourceMap resourceMap;
    private ApplicationContext context;
    private NodeFactory nodeFactory;
    private String baseDirPath;
    private V view;

    private TreeTableModel model = new TreeTableModel();

    public ResourceMap getResourceMap()
    {
        return resourceMap;
    }

    public void setResourceMap(ResourceMap resourceMap)
    {
        this.resourceMap = resourceMap;
    }

    public V getView()
    {
        return view;
    }

    public void setView(V view)
    {
        this.view = view;
    }

    public ApplicationContext getContext()
    {
        return context;
    }

    public void setContext(ApplicationContext context)
    {
        this.context = context;
    }

    public NodeFactory getNodeFactory()
    {
        return nodeFactory;
    }

    public void setNodeFactory(NodeFactory nodeFactory)
    {
        this.nodeFactory = nodeFactory;
    }

    public String getBaseDirPath()
    {
        return baseDirPath;
    }

    public void setBaseDirPath(String baseDirPath)
    {
        this.baseDirPath = baseDirPath;
    }

    public TreeTableModel getModel()
    {
        return model;
    }
}
