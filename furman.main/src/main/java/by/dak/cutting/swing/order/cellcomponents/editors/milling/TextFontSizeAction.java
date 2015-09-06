package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.AttributeAction;
import org.jhotdraw.undo.CompositeEdit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 27.8.2009
 * Time: 10.54.54
 * To change this template use File | Settings | File Templates.
 */
public class TextFontSizeAction extends AttributeAction
{
    private boolean isIncrease;
    private static final float increase = 2;  //увеличение fontSize
    private static final float decrease = 2; //уменьшение fontSize

    public TextFontSizeAction(DrawingEditor editor, AttributeKey key, Icon icon, Boolean isIncrease)
    {
        super(editor, key, null, icon);
        this.isIncrease = isIncrease;
    }

    @Override
    public void actionPerformed(ActionEvent evt)
    {
        if (getView() != null && getView().getSelectionCount() >= 0)
        {
            CompositeEdit edit = new CompositeEdit(labels.getString("drawAttributeChange"));
            fireUndoableEditHappened(edit);
            changeAttribute();
            fireUndoableEditHappened(edit);
        }
    }

    public void changeAttribute()
    {
        CompositeEdit edit = new CompositeEdit("attributes");
        fireUndoableEditHappened(edit);
        Drawing drawing = getDrawing();
        List<TextFigure> textFigures;
        Iterator i = getView().getDrawing().getChildren().iterator();
        while (i.hasNext())
        {
            Figure figure = (Figure) i.next();

            if (figure instanceof TextFigureRelated)
            {
                figure.willChange();
                TextFigureRelated textFigureRelated = (TextFigureRelated) figure;
                textFigures = textFigureRelated.getTextFigures();
                for (TextFigure textFigure : textFigures)
                {
                    textFigure.willChange();
                    if (isIncrease)
                    {
                        textFigure.setFontSize((float) setIncreaseFontSize(textFigure));
                    }
                    else
                    {
                        textFigure.setFontSize((float) setDecreaseFontSize(textFigure));
                    }
                    textFigure.changed();
                }
                figure.changed();
            }
        }
        fireUndoableEditHappened(edit);
    }

    private double setIncreaseFontSize(TextFigure textFigure)
    {
        if (textFigure.getFontSize() < 24)
            return textFigure.getFontSize() + increase;
        else
            return textFigure.getFontSize();
    }

    private double setDecreaseFontSize(TextFigure textFigure)
    {
        if (textFigure.getFontSize() > 12)
            return textFigure.getFontSize() - decrease;
        else
            return textFigure.getFontSize();
    }
}
