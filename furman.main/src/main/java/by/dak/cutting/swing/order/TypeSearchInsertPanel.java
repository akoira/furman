package by.dak.cutting.swing.order;

import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.cutting.swing.renderer.EntityListRenderer;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;


public class TypeSearchInsertPanel extends SearchInsertPanel
{

    public TypeSearchInsertPanel()
    {
        super(true);
    }

    public TypeSearchInsertPanel(boolean isRenderer)
    {
        super(isRenderer);
    }

    @Override
    protected void bind()
    {
        dComboBox.setRenderer(new EntityListRenderer<BoardDef>());
        dComboBox.setModel(new DefaultComboBoxModel(new Vector<BoardDef>(FacadeContext.getBoardDefFacade().loadAllSortedBy(BoardDef.PROPERTY_name))));
    }

    @Override
    protected void showDialog()
    {
//        setCallNew(true);
//        BoardDefPanel boardDefPanel = new BoardDefPanel(CuttingApp.getApplication().getMainFrame());
//        boardDefPanel.setValue(new BoardDef());
//        boardDefPanel.showDialog();
    }

    @Override
    protected void initEnvironment()
    {
        super.initEnvironment();
        dComboBox.addItemListener(new ItemListener()
        {

            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                    BoardDef boardDef = (BoardDef) e.getItem();
                    if (boardDef == null)
                        return;
                    OrderDetailsDTO dto = getData();
                    dto.setMatWidth(boardDef.getDefaultWidth());
                    dto.setMatLength(boardDef.getDefaultLength());
                }
            }
        });
    }

    @Override
    public void setData(OrderDetailsDTO data)
    {
        super.setData(data);
        dComboBox.setSelectedItem(data.getBoardDef());
    }

}
