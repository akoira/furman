/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CellPanel.java
 *
 * Created on Aug 8, 2011, 8:08:43 PM
 */
package by.dak.design.swing;

import by.dak.cutting.swing.store.Constants;
import by.dak.swing.AValueTab;
import org.jdesktop.application.Action;
import org.jdesktop.beans.AbstractBean;

/**
 * @author alkoyro
 */
public class CellPanel extends AValueTab<CellPanel.Cell>
{


    public CellPanel()
    {
        setVisibleProperties(new String[]{
                "length",
                "height",
                "width"
        });
        setCacheEditorCreator(Constants.getEntityEditorCreators(Cell.class));
    }

    @Action
    public void prepareCell()
    {
        Cell cell = getValue();

        firePropertyChange("cell", null, cell);
    }

    public static class Cell extends AbstractBean
    {
        private Long length;
        private Long height;
        private Long width;

        public Long getLength()
        {
            return length;
        }

        public void setLength(Long length)
        {
            Long old = this.length;
            this.length = length;
            firePropertyChange("length", old, length);
        }

        public Long getHeight()
        {
            return height;
        }

        public void setHeight(Long height)
        {
            Long old = this.height;
            this.height = height;
            firePropertyChange("height", old, height);
        }

        public Long getWidth()
        {
            return width;
        }

        public void setWidth(Long width)
        {
            Long old = this.width;
            this.width = width;
            firePropertyChange("width", old, width);
        }
    }
}
