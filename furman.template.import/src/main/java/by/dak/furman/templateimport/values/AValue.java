package by.dak.furman.templateimport.values;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 9/22/13
 * Time: 11:00 AM
 */
public abstract class AValue<P extends AValue, C extends AValue>
{
    private P parent;
    private List<C> children = new ArrayList<C>();
    private List<Message> messages = new ArrayList<Message>();
    private String name;

    public void addChild(C child)
    {
        child.setParent(this);
        children.add(child);
    }

    public List<C> getChildren()
    {
        return children;
    }

    public void setChildren(List<C> children)
    {
        this.children = children;
    }

    public P getParent()
    {
        return parent;
    }

    public void setParent(P parent)
    {
        this.parent = parent;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void addMessage(Message message)
    {
        getMessages().add(message);
    }

    public List<Message> getMessages()
    {
        return messages;
    }

    public List<Message> getAllMessages()
    {
        ArrayList<Message> messages = new ArrayList<Message>();
        messages.addAll(this.getMessages());
        for (C c : children)
        {
            messages.addAll(c.getAllMessages());
        }
        return messages;
    }
}
