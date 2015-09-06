package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import by.dak.cutting.swing.order.cellcomponents.editors.milling.glueing.Gluieng;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.glueing.GluiengDialog;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.glueing.GluiengPanel;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BorderDefEntity;
import by.dak.persistence.entities.TextureEntity;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.DefaultAttributeAction;
import org.jhotdraw.undo.CompositeEdit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys.GLUEING;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 25.8.2009
 * Time: 16.07.40
 * To change this template use File | Settings | File Templates.
 */
public class GluiengAttributeAction extends DefaultAttributeAction
{
    private BorderDefEntity defaultBorderDef;
    private GluiengDialog gluiengDialog;
    final ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(GluiengPanel.class);
    private OrderDetailsDTO orderDetailsDTO;

    public GluiengAttributeAction(DrawingEditor editor, AttributeKey key, Icon icon)
    {
        super(editor, key, icon);
    }

    @Override
    public void setEnabled(boolean newValue)
    {
        if (getView() != null && getView().getSelectionCount() == 0)
        {
            super.setEnabled(false);
        }
        else
            super.setEnabled(newValue);
    }

    @Override
    public void actionPerformed(ActionEvent evt)
    {
        if (getView() != null && getView().getSelectionCount() > 0)
        {
            Window w = evt.getSource() instanceof JComponent ? SwingUtilities.getWindowAncestor((JComponent) evt.getSource()) : null;
            if (w != null)
            {
                if (w instanceof JFrame)
                {
                    gluiengDialog = new GluiengDialog((JFrame) w, true);
                }
                else if (w instanceof JDialog)
                {
                    gluiengDialog = new GluiengDialog((JDialog) w, true);
                }
                else
                {
                    throw new IllegalArgumentException();
                }
                gluiengDialog.setLocationRelativeTo(w);
            }

            AbstractAction action = new AbstractAction()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    changeAttribute();
                    gluiengDialog.dispose();
                }
            };
            action.putValue(Action.NAME, resourceMap.getString("okButton.text"));

            gluiengDialog.getGluiengPanel().getOkButton().setAction(action);
            for (Figure figure : getView().getSelectedFigures())
            {
                Gluieng gluieng = figure.get(GLUEING);
                if (gluieng == null)
                {
                    gluieng = new Gluieng();
                    gluieng.setBorderDef(getDefaultBorderDef());
                    gluieng.setTexture(getOrderDetailsDTO() != null ? getTexture(getOrderDetailsDTO().getTexture(), getDefaultBorderDef()) : null);
                }
                gluiengDialog.setGluieng(gluieng);
                break;
            }
            gluiengDialog.setVisible(true);

        }
    }


    private BorderDefEntity getDefaultBorderDef()
    {
        if (defaultBorderDef == null)
            return FacadeContext.getBorderDefFacade().findDefaultBorderDef();
        return defaultBorderDef;
    }

    private TextureEntity getTexture(TextureEntity detailTexture, BorderDefEntity gluingBorderDef)
    {
        return FacadeContext.getTextureFacade().findBy(gluingBorderDef, detailTexture);

    }


    @Override
    public void changeAttribute()
    {
        if (gluiengDialog == null)
            return;
        CompositeEdit edit = new CompositeEdit("attributes");
        fireUndoableEditHappened(edit);
        Drawing drawing = getDrawing();
        if (gluiengDialog.getValidationResult().getErrors().size() == 0)
        {
            Gluieng result = gluiengDialog.getGluieng();
            for (Figure figure : getView().getSelectedFigures())
            {
                if (figure instanceof AbstractAttributedFigure)
                {
                    figure.willChange();
                    figure.set(GLUEING, (Gluieng) result.clone());
                    figure.changed();
                }
            }

            fireUndoableEditHappened(edit);
        }
    }

    public OrderDetailsDTO getOrderDetailsDTO()
    {
        return orderDetailsDTO;
    }

    public void setOrderDetailsDTO(OrderDetailsDTO orderDetailsDTO)
    {
        this.orderDetailsDTO = orderDetailsDTO;
    }
}