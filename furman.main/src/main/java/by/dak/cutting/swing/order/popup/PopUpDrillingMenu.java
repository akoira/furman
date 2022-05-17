package by.dak.cutting.swing.order.popup;

import by.dak.cutting.swing.order.data.Drilling;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import org.jdesktop.swingx.JXFormattedTextField;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.util.function.Function;


public final class PopUpDrillingMenu extends AbstractSideMenu {
    public interface fun {
        Function<JXFormattedTextField, Integer> to_int = field ->
                field.getValue() != null ? ((Number) field.getValue()).intValue() : 0;
    }

    private JTextArea noteArea;
    private JXFormattedTextField number;
    private JXFormattedTextField numberForLoop;
    private JXFormattedTextField numberForHandle;

    private OrderDetailsDTO data;
    private Drilling drilling;

    public PopUpDrillingMenu(TableCellEditor tableCellEditor) {
        super(tableCellEditor);
    }

    protected void initComponents() {
        noteArea = new JTextArea(5, 20);
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        noteArea.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        number = new JXFormattedTextField("к-во");
        number.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter()));
        numberForLoop = new JXFormattedTextField("к-во");
        numberForHandle = new JXFormattedTextField("к-во");
    }


    protected void buildView() {

        FormLayout layout = new FormLayout("2dlu, pref, 2dlu, 50dlu, 2dlu", // столбцы
                "2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, min, 2dlu"); // строки

        // layout.setRowGroups(new int[][]{{2, 4, 6, 8, 10}});
        JPanel panel = new JPanel(layout);
        CellConstraints cc = new CellConstraints();
        panel.add(new JLabel("Присадка"), cc.xy(2, 2));
        panel.add(number, cc.xy(4, 2));
        panel.add(new JLabel("П-ка под петли"), cc.xy(2, 4));
        panel.add(numberForLoop, cc.xy(4, 4));
        panel.add(new JLabel("П-ка под ручки"), cc.xy(2, 6));
        panel.add(numberForHandle, cc.xy(4, 6));
        panel.add(new JLabel("Примечание"), cc.xyw(2, 8, 3));
        panel.add(noteArea, cc.xyw(2, 10, 3));
        panel.add(getOkButton(), cc.xy(2, 12));
        add(panel);
    }

    @Override
    public void updateData() {
        this.drilling.setNumber(fun.to_int.apply(number));
        this.drilling.setNumberForLoop(fun.to_int.apply(numberForLoop));
        this.drilling.setNumberForHandle(fun.to_int.apply(numberForHandle));
        this.drilling.setNote(noteArea.getText());
        this.data.setDrilling(this.drilling);
        refreshComponent();
    }


    private void refreshComponent() {
        if (getComponentToRefresh() != null)
            ((JButton) getComponentToRefresh()).setText(drilling.isEmpty() ? "NO" : "Yes");
    }

    @Override
    protected void flushComponentValues() {
        number.setValue(null);
        numberForLoop.setValue(null);
        numberForHandle.setValue(null);
        noteArea.setText(null);
    }


    @Override
    protected void createRules() {
    }

    @Override
    protected void refreshCompState() {
        refreshComponent();
    }

    @Override
    protected void beforeVisible() {
        number.setValue(this.drilling.getNumber());
        numberForLoop.setValue(this.drilling.getNumberForLoop());
        numberForHandle.setValue(this.drilling.getNumberForHandle());
        noteArea.setText(this.drilling.getNote());
        refreshCompState();
    }

    public OrderDetailsDTO getData() {
        return data;
    }

    public void setData(OrderDetailsDTO data) {
        this.data = data;
        this.drilling = data.getDrilling() == null ? new Drilling() : data.getDrilling();
    }
}
