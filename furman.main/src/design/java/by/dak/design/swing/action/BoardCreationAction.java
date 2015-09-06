package by.dak.design.swing.action;

import by.dak.design.draw.BoardFigureCreationTool;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.action.AbstractDrawingViewAction;
import org.jhotdraw.draw.event.ToolAdapter;
import org.jhotdraw.draw.event.ToolEvent;
import org.jhotdraw.draw.event.ToolListener;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 07.08.11
 * Time: 16:29
 * To change this template use File | Settings | File Templates.
 */
public class BoardCreationAction extends AbstractDrawingViewAction
{
    private BoardFigureCreationTool boardFigureCreationTool;
    /**
     * кнопка, которой передастся управление, после создания борда
     */
    private AbstractButton delegator;

    public BoardCreationAction(DrawingEditor editor, BoardFigureCreationTool toolFigure, AbstractButton delegator)
    {
        super(editor);
        this.boardFigureCreationTool = toolFigure;
        this.delegator = delegator;
        initEnv();
    }

    private void initEnv()
    {
        ToolListener toolHandler = new ToolAdapter()
        {
            @Override
            public void toolDone(ToolEvent event)
            {
                delegator.setSelected(true);
            }
        };

        boardFigureCreationTool.addToolListener(toolHandler);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        getEditor().setTool(boardFigureCreationTool);
    }
}
