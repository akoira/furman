package by.dak.cutting.swing.order.controls;

import org.bushe.swing.event.annotation.AnnotationProcessor;

import javax.swing.*;


public abstract class GenericControl<V extends JComponent>
{

    private V view;

    public GenericControl(V view)
    {
        this.view = view;
        AnnotationProcessor.process(this);
    }

    public V getView()
    {
        return view;
    }

    public void setView(V view)
    {
        this.view = view;
    }

    protected abstract void addActions();
}
