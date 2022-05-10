package by.dak.cutting.swing.order.popup;

import by.dak.cutting.swing.order.cellcomponents.editors.DrillingCellEditor;
import by.dak.cutting.swing.order.data.DTO;
import by.dak.cutting.swing.order.data.Drilling;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableCellEditor;


public final class PopUpDrillingMenu extends CommonSideMenu {
    private JTextArea noteArea;
    private JTextField textField;

    private final JXFormattedTextField number = new JXFormattedTextField("к-во");
    private final JXFormattedTextField numberForLoop = new JXFormattedTextField("к-во");
    private final JXFormattedTextField numberForHandle= new JXFormattedTextField("к-во");

    public PopUpDrillingMenu(TableCellEditor tableCellEditor) {
        super(tableCellEditor);
    }

    protected void initComponents() {
        noteArea = new JTextArea(5, 20);
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        noteArea.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        textField = new JTextField();
    }

    protected void buildView() {
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

    protected void buildView_1() {

        FormLayout layout = new FormLayout(
                "2dlu, pref, 2dlu, 50dlu, 2dlu, min", // столбцы
                "pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu"); // строки

        layout.setRowGroups(new int[][]{{1, 3, 5}});
        JPanel panel = new JPanel(layout);
        CellConstraints cc = new CellConstraints();
        panel.add(new JLabel("Присадка"), cc.xy(2, 1));
        panel.add(new JXFormattedTextField("к-во"), cc.xy(4, 1));
        panel.add(new JLabel("П-ка под петли"), cc.xy(2, 3));
        panel.add(new JTextField(), cc.xy(4, 3));
        panel.add(new JLabel("П-ка под ручки"), cc.xy(2, 5));
        panel.add(new JTextField(), cc.xy(4, 5));
        panel.add(new JLabel("Примечание"), cc.xyw(2, 7, 3));
        panel.add(noteArea, cc.xyw(2, 9, 3));
//        panel.add(new JButton(), cc.xy (5, 5));
        add(panel);
    }

    @Override
    public void updateData() {
        Drilling dto = (Drilling) getDTO();
        dto.setPicName(textField.getText());
        dto.setNotes(noteArea.getText());
        refreshComponent();
    }

    @Override
    protected DTO getDTO() {
        Drilling dto = getData().getDrilling();
        if (dto == null) {
            dto = new Drilling();
            getData().setDrilling(dto);
        }
        return dto;
    }

    @Override
    protected void refreshComponent() {
        if (getComponentToRefresh() == null)
            return;
        ((JButton) getComponentToRefresh()).setText(StringUtils.isBlank(textField.getText()) ? "NO" : "Yes");
    }

    @Override
    protected void flushComponentValues() {
        textField.setText("");
        noteArea.setText("");
    }


    @Override
    protected void mergeCompAndValues() {
        Drilling drilling = (Drilling) getDTO();
        textField.setText(drilling.getPicName());
        noteArea.setText(drilling.getNotes());
    }
}
