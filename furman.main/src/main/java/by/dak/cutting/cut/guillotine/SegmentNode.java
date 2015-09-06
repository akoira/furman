/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Element;

import java.util.ArrayList;

/**
 * @author Peca
 */
public class SegmentNode
{
    private Element element;
    private SegmentNode parent;
    private ArrayList<SegmentNode> items;
    //private boolean rotateElement;

    public SegmentNode()
    {
        items = new ArrayList<SegmentNode>();
    }

    public void addNode(SegmentNode child)
    {
        this.items.add(child);
        if (child.parent != null) child.parent.removeNode(child);
        child.parent = this;
    }

    public void removeNode(SegmentNode child)
    {
        items.remove(child);
        child.parent = null;
    }

    public SegmentNode removeNode(int index)
    {
        SegmentNode node = items.remove(index);
        node.parent = null;
        return node;
    }

    public Element getElement()
    {
        return element;
    }

    public void setElement(Element element)
    {
        this.element = element;
    }

    public SegmentNode[] getNodes()
    {
        return items.toArray(new SegmentNode[items.size()]);
    }

    public SegmentNode getNode(int index)
    {
        return items.get(index);
    }

    public int size()
    {
        return items.size();
    }

    public void createFromSegment(Segment segment)
    {
        this.items.clear();
        this.element = segment.getElement();
        for (Segment seg : segment.getItems())
        {
            SegmentNode node = new SegmentNode();
            node.createFromSegment(seg);
            this.addNode(node);
        }
    }

    @Override
    public SegmentNode clone()
    {
        SegmentNode res = new SegmentNode();
        res.element = this.element;
        //res.setRotateElement(this.isRotateElement());
        for (SegmentNode node : items)
        {
            res.addNode(node.clone());
        }
        return res;
    }

    @Override
    public String toString()
    {
        String s = "";
        for (SegmentNode node : items)
        {
            s = s + node.toString();
        }
        if (this.element != null) return String.valueOf(this.element.getId()) + ",";
        else return s;
    }

    public int getLevel()
    {
        if (parent == null) return 0;
        else return parent.getLevel() + 1;
    }

    /*  public boolean isRotateElement() {
        return rotateElement;
    }

    public void setRotateElement(boolean rotateElement) {
        this.rotateElement = rotateElement;
    }*/
}
