package by.dak.design.settings;

import org.jdesktop.beans.AbstractBean;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 17.09.11
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
public class Settings extends AbstractBean implements Serializable
{
    public static final String PROPERTY_boardNumeration = "boardNumeration";
    public static final String PROPERTY_cellNumeration = "cellNumeration";

    private boolean boardNumeration = false;
    private boolean cellNumeration = false;

    public boolean isBoardNumeration()
    {
        return boardNumeration;
    }

    public void setBoardNumeration(boolean boardNumeration)
    {
        boolean old = this.boardNumeration;
        this.boardNumeration = boardNumeration;
        firePropertyChange(PROPERTY_boardNumeration, old, boardNumeration);
    }

    public boolean isCellNumeration()
    {
        return cellNumeration;
    }

    public void setCellNumeration(boolean cellNumeration)
    {
        boolean old = this.cellNumeration;
        this.cellNumeration = cellNumeration;
        firePropertyChange(PROPERTY_cellNumeration, old, cellNumeration);
    }
}
