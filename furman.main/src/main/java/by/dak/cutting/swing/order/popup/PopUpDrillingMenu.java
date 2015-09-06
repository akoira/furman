package by.dak.cutting.swing.order.popup;

import by.dak.cutting.swing.order.cellcomponents.editors.DrillingCellEditor;
import by.dak.cutting.swing.order.data.DTO;
import by.dak.cutting.swing.order.data.Drilling;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableCellEditor;


public class PopUpDrillingMenu extends CommonSideMenu
{
    private JTextArea noteArea;
    private JTextField textField;

    public PopUpDrillingMenu(TableCellEditor tableCellEditor)
    {
        super(tableCellEditor);
    }

    protected void initComponents()
    {
        noteArea = new JTextArea(5, 20);
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        noteArea.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        textField = new JTextField();
    }

    protected void buildView()
    {
        ResourceMap resourceMap = ((DrillingCellEditor) getTableCellEditor()).getContext().getResourceMap(PopUpDrillingMenu.class);
        FormLayout layout = new FormLayout("1dlu, 45dlu, 45dlu, 1dlu", "10dlu, 20dlu, 10dlu, 50dlu, 15dlu");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.nextColumn();
        builder.append(new JLabel(resourceMap.getString("label.pic")), 2);
        builder.nextLine();
        builder.nextColumn();
        builder.append(textField, 2);
        builder.nextLine();
        builder.nextColumn();
        builder.append(new JLabel(resourceMap.getString("label.text")));
        builder.nextLine();
        builder.nextColumn();
        JScrollPane scroll = new JScrollPane();
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.add(noteArea);
        builder.append(noteArea, 2);
        builder.nextLine();
        builder.nextColumn(2);
        builder.append(getOkButton());
        JPanel panel = builder.getPanel();
        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        add(panel);
    }

    @Override
    public void updateData()
    {
        Drilling dto = (Drilling) getDTO();
        dto.setPicName(textField.getText());
        dto.setNotes(noteArea.getText());
        refreshComponent();
    }

    @Override
    protected DTO getDTO()
    {
        Drilling dto = getData().getDrilling();
        if (dto == null)
        {
            dto = new Drilling();
            getData().setDrilling(dto);
        }
        return dto;
    }

    @Override
    protected void refreshComponent()
    {
        if (getComponentToRefresh() == null)
            return;
        ((JButton) getComponentToRefresh()).setText(StringUtils.isBlank(textField.getText()) ? "NO" : "Yes");
    }

    @Override
    protected void flushComponentValues()
    {
        textField.setText("");
        noteArea.setText("");
    }


    @Override
    protected void mergeCompAndValues()
    {
        Drilling drilling = (Drilling) getDTO();
        textField.setText(drilling.getPicName());
        noteArea.setText(drilling.getNotes());
    }
}
