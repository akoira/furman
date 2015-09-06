/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Dimension;
import by.dak.cutting.cut.base.Element;

public class ElementDescriptor implements Comparable<ElementDescriptor>{

    private Element element;
    private Dimension dimension;
    private boolean rotation;
    private boolean cutted;
    private ElementDescriptor relatedDescriptor;
    private Object tag;

    @Override
    public int compareTo(ElementDescriptor o)
    {
        return (element.getWidth() - o.element.getWidth())*-1;
    }

    public ElementDescriptor(Element element, boolean rotation) {
        this.element = element;
        this.dimension = new Dimension(element);
        if (rotation) {
            this.dimension.rotate();
        }
        this.rotation = rotation;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public Element getElement() {
        return this.element;
    }

    public boolean isRotation() {
        return rotation;
    }

    public boolean isCutted() {
        return cutted;
    }

    public void setCutted(boolean cutted) {
        this.cutted = cutted;
    }

    public ElementDescriptor getRelatedDescriptor() {
        return relatedDescriptor;
    }

    public void setRelatedDescriptor(ElementDescriptor relatedDescriptor) {
        this.relatedDescriptor = relatedDescriptor;
    }

    @Override
    public String toString() {
        return this.element.toString() + " (" + this.dimension.toString() + ")";
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
    
    
}
