package by.dak.cutting.swing.order.cellcomponents.editors;

import by.dak.cutting.swing.order.OrderTable;
import by.dak.cutting.swing.order.cellcomponents.editors.cuttoff.CutoffEditorPanel;
import by.dak.cutting.swing.order.cellcomponents.editors.cuttoff.CutoffValidator;
import by.dak.cutting.swing.order.data.Cutoff;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.cutting.swing.order.models.OrderDetailsTableModel;
import by.dak.cutting.swing.table.CellContext;
import by.dak.swing.table.AbstractComponentProvider;

import javax.swing.*;
import java.awt.*;

/**
 * User: akoyro
 * Date: 25.04.2009
 * Time: 23:12:36
 */
public class CutoffComponentProvider extends AbstractComponentProvider
{
    private CutoffEditorPanel panel;

    @Override
    public JComponent getPopupComponent()
    {
        return getPanel();
    }

    @Override
    public void setCellContext(CellContext cellContext)
    {
        Cutoff cutoff = (Cutoff) cellContext.getValue();
        if (cutoff == null)
        {
            cutoff = new Cutoff();
        }

        OrderDetailsTableModel model = (OrderDetailsTableModel) ((OrderTable) cellContext.getTable()).getModel();
        OrderDetailsDTO dto = model.getRowBy(cellContext.getRow());
        getPanel().setElement(new Dimension(dto.getLength().intValue(),
                dto.getWidth().intValue()));
        getPanel().setCutoff(cutoff);
    }

    @Override
    public Object getValue()
    {
        return new CutoffValidator(getPanel().getCutoff()).validate() ? getPanel().getCutoff() : null;
    }

    @Override
    public void init()
    {
        super.init();
        if (panel != null)
        {
            panel.getButtonOk().setAction(null);
        }
        panel = new CutoffEditorPanel();
        getPanel().getButtonOk().setAction(getCommitAction());
    }

    public CutoffEditorPanel getPanel()
    {
        return panel;
    }
}
