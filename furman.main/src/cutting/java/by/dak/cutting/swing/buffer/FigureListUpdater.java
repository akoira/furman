package by.dak.cutting.swing.buffer;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.swing.ElementFigure;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.NewEditDeleteActions;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 31.01.11
 * Time: 11:54
 * To change this template use File | Settings | File Templates.
 */
public class FigureListUpdater extends AListUpdater<ElementFigure>
{
    private NewEditDeleteActions actions;
    //private CutterBufferController controller;


    @Override
    public String[] getVisibleProperties()
    {
        return new String[]
                {
                        ElementFigure.PROPERTY_element + "." + Element.PROPERTY_description
                };
    }

    @Override
    public ResourceMap getResourceMap()
    {
        return Application.getInstance().getContext().getResourceMap(FigureListUpdater.class);
    }

    @Override
    public void update()
    {
    }

    @Override
    public int getCount()
    {
        return getList().size();
    }

    public void add(ElementFigure elementFigure)
    {
        getList().add(elementFigure);
    }

    public void remove(ElementFigure elementFigure)
    {
        getList().remove(elementFigure);
    }

    @Override
    public NewEditDeleteActions getNewEditDeleteActions()
    {
        if (actions == null)
        {
            actions = new UpdaterNEDActions();
        }
        return actions;
    }

    public class UpdaterNEDActions extends NewEditDeleteActions
    {
        @Override
        public void newValue()
        {
        }

        @Override
        public void openValue()
        {
            ElementFigure elementFigure = (ElementFigure) getSelectedElement();
            firePropertyChange("removedElement", null, elementFigure);
        }

        @Override
        public void deleteValue()
        {
        }
    }
}
