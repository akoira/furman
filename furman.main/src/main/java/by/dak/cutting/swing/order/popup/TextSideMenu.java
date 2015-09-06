package by.dak.cutting.swing.order.popup;

import by.dak.cutting.configuration.Constants;
import by.dak.cutting.swing.order.data.A45;
import by.dak.cutting.swing.order.data.DTO;
import by.dak.cutting.swing.text.NumericDocument;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableCellEditor;
import java.util.HashMap;
import java.util.Map;


public class TextSideMenu extends CommonSideMenu
{

    protected JTextField upTextField;
    protected JTextField leftTextField;
    protected JTextField rightTextField;
    protected JTextField downTextField;

    public TextSideMenu(TableCellEditor tableCellEditor)
    {
        super(tableCellEditor);
    }

    protected void initComponents()
    {
        super.initComponents();
        upTextField = new JTextField();
        upTextField.setDocument(new NumericDocument(90));
        leftTextField = new JTextField();
        leftTextField.setDocument(new NumericDocument(90));
        rightTextField = new JTextField();
        rightTextField.setDocument(new NumericDocument(90));
        downTextField = new JTextField();
        downTextField.setDocument(new NumericDocument(90));
    }

    protected void buildView()
    {
        FormLayout layout = new FormLayout("25dlu, 1dlu, 12dlu, 5dlu, 17dlu, 3dlu, 30dlu, 5dlu",
                "10dlu, 5dlu, 10dlu, 5dlu, 10dlu, 5dlu, 10dlu, 5dlu,  40dlu, 1dlu, 1dlu");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout/*, new FormDebugPanel()*/);
        builder.append(upLabel);
        builder.append(upChBox);
        builder.append(upTextField, 3);
        builder.nextRow();

        builder.append(leftLabel);
        builder.append(leftChBox);
        builder.append(leftTextField, 3);
        builder.nextRow();

        builder.append(rightLabel);
        builder.append(rightChBox);
        builder.append(rightTextField, 3);
        builder.nextRow();

        builder.append(downLabel);
        builder.append(downChBox);
        builder.append(downTextField, 3);
        builder.nextRow();

        builder.append(canvas, 5);

        builder.append(getOkButton(), 2);

        JPanel panel = builder.getPanel();
        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        add(panel);
    }

    @Override
    public void updateData()
    {
        A45 dto = (A45) getDTO();
        dto.setUp(upChBox.isSelected());
        dto.setUpValue(dto.isUp() ? upTextField.getText() : null);
        dto.setDown(downChBox.isSelected());
        dto.setDownValue(dto.isDown() ? downTextField.getText() : null);
        dto.setLeft(leftChBox.isSelected());
        dto.setLeftValue(dto.isLeft() ? leftTextField.getText() : null);
        dto.setRight(rightChBox.isSelected());
        dto.setRightValue(dto.isRight() ? rightTextField.getText() : null);
        refreshComponent();
    }

    @Override
    protected DTO getDTO()
    {
        DTO dto = getData().getA45();
        if (dto == null)
        {
            dto = new A45();
            getData().setA45((A45) dto);
        }
        return dto;
    }

    protected void beforeVisible()
    {
        updateComponentValues();
    }

    @Override
    protected void flushComponentValues()
    {
        super.flushComponentValues();
        upTextField.setText(Constants.DEFAULT_A45_VALUE);
        downTextField.setText(Constants.DEFAULT_A45_VALUE);
        rightTextField.setText(Constants.DEFAULT_A45_VALUE);
        leftTextField.setText(Constants.DEFAULT_A45_VALUE);
        refreshCompState();
    }


    @Override
    protected void mergeCompAndValues()
    {
        super.mergeCompAndValues();
        A45 a45 = (A45) getDTO();
        upTextField.setText(StringUtils.isBlank(a45.getUpValue()) ? Constants.DEFAULT_A45_VALUE : a45.getUpValue());
        downTextField.setText(StringUtils.isBlank(a45.getDownValue()) ? Constants.DEFAULT_A45_VALUE : a45.getDownValue());
        leftTextField.setText(StringUtils.isBlank(a45.getLeftValue()) ? Constants.DEFAULT_A45_VALUE : a45.getLeftValue());
        rightTextField.setText(StringUtils.isBlank(a45.getRightValue()) ? Constants.DEFAULT_A45_VALUE : a45.getRightValue());
    }

    @Override
    protected void createRules()
    {
        rules = new HashMap<JCheckBox, JComponent[]>();
        rules.put(upChBox, new JComponent[]{upTextField});
        rules.put(downChBox, new JComponent[]{downTextField});
        rules.put(rightChBox, new JComponent[]{rightTextField});
        rules.put(leftChBox, new JComponent[]{leftTextField});
    }

    @Override
    protected void refreshCompState()
    {
        for (Map.Entry<JCheckBox, JComponent[]> rule : rules.entrySet())
        {
            for (JComponent comp : rule.getValue())
                comp.setEnabled(rule.getKey().isSelected());
        }
    }
}
