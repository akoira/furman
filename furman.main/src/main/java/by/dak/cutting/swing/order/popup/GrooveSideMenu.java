package by.dak.cutting.swing.order.popup;

import by.dak.cutting.swing.order.data.DTO;
import by.dak.cutting.swing.order.data.Groove;
import by.dak.cutting.swing.text.NumericDocument;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableCellEditor;
import java.util.HashMap;
import java.util.Map;


public class GrooveSideMenu extends CommonSideMenu
{
    protected JTextField updepthTextField;
    protected JTextField downdepthTextFieled;
    protected JTextField rightdepthTextFieled;
    protected JTextField leftdepthTextField;

    protected JTextField upspaceTextField;
    protected JTextField downspaceTextField;
    protected JTextField rightspaceTextField;
    protected JTextField leftspaceTextField;

    public GrooveSideMenu(TableCellEditor tableCellEditor)
    {
        super(tableCellEditor);
    }

    @Override
    protected void initComponents()
    {
        super.initComponents();
        updepthTextField = new JTextField();
        updepthTextField.setDocument(new NumericDocument());
        downdepthTextFieled = new JTextField();
        downdepthTextFieled.setDocument(new NumericDocument());
        rightdepthTextFieled = new JTextField();
        rightdepthTextFieled.setDocument(new NumericDocument());
        leftdepthTextField = new JTextField();
        leftdepthTextField.setDocument(new NumericDocument());

        upspaceTextField = new JTextField();
        upspaceTextField.setDocument(new NumericDocument());
        downspaceTextField = new JTextField();
        downspaceTextField.setDocument(new NumericDocument());
        rightspaceTextField = new JTextField();
        rightspaceTextField.setDocument(new NumericDocument());
        leftspaceTextField = new JTextField();
        leftspaceTextField.setDocument(new NumericDocument());
    }

    private JPanel buildSubPanel(JLabel label, JCheckBox checkBox, JTextField textField, JTextField textField2)
    {
        FormLayout layoutUp = new FormLayout("24dlu, 1dlu, 11dlu, 4dlu, 14dlu, 1dlu, 42dlu",
                "10dlu, 5dlu, 10dlu");
        DefaultFormBuilder builder = new DefaultFormBuilder(layoutUp/*, new FormDebugPanel()*/);
        builder.append(label);
        builder.append(checkBox);
        builder.append(new JLabel("Гл."));
        builder.append(textField);
        builder.nextRow();
        builder.append(new JLabel(), 3);
        builder.append(new JLabel("Рас."));
        builder.append(textField2);
        JPanel upPanel = builder.getPanel();
        upPanel.setBorder(BorderFactory.createEtchedBorder());
        return upPanel;
    }

    @Override
    protected void buildView()
    {
        FormLayout layout = new FormLayout("65dlu, 5dlu, 32dlu",
                "30dlu, 1dlu, 30dlu, 1dlu, 30dlu, 1dlu, 30dlu, 1dlu, 38dlu");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout/*, new FormDebugPanel()*/);

        JPanel upPanel = buildSubPanel(upLabel, upChBox, updepthTextField, upspaceTextField);
        builder.append(upPanel, 3);
        builder.nextRow();

        JPanel leftPanel = buildSubPanel(leftLabel, leftChBox, leftdepthTextField, leftspaceTextField);
        builder.append(leftPanel, 3);
        builder.nextRow();

        JPanel rightPanel = buildSubPanel(rightLabel, rightChBox, rightdepthTextFieled, rightspaceTextField);
        builder.append(rightPanel, 3);
        builder.nextRow();

        JPanel downPanel = buildSubPanel(downLabel, downChBox, downdepthTextFieled, downspaceTextField);
        builder.append(downPanel, 3);

        builder.nextRow();
        builder.append(canvas);

        builder.append(getOkButton());
        JPanel panel = builder.getPanel();
        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        add(panel);
    }


    @Override
    protected DTO getDTO()
    {
        Groove dto = getData().getGroove();
        if (dto == null)
        {
            dto = new Groove();
            getData().setGroove(dto);
        }
        return dto;
    }

    @Override
    public void updateData()
    {
        Groove dto = (Groove) getDTO();
        dto.setUp(upChBox.isSelected());
        dto.setDown(downChBox.isSelected());
        dto.setLeft(leftChBox.isSelected());
        dto.setRight(rightChBox.isSelected());
        dto.setDepthLeft(dto.isLeft() ? getValueFromFieled(leftdepthTextField) : 0);
        dto.setDepthRight(dto.isRight() ? getValueFromFieled(rightdepthTextFieled) : 0);
        dto.setDepthDown(dto.isDown() ? getValueFromFieled(downdepthTextFieled) : 0);
        dto.setDepthUp(dto.isUp() ? getValueFromFieled(updepthTextField) : 0);

        dto.setDistanceDown(dto.isDown() ? getValueFromFieled(downspaceTextField) : 0);
        dto.setDistanceLeft(dto.isLeft() ? getValueFromFieled(leftspaceTextField) : 0);
        dto.setDistanceRight(dto.isRight() ? getValueFromFieled(rightspaceTextField) : 0);
        dto.setDistanceUp(dto.isUp() ? getValueFromFieled(upspaceTextField) : 0);

        refreshComponent();
    }

    private int getValueFromFieled(JTextField textField)
    {
        if (StringUtils.isBlank(textField.getText()))
        {
            return 0;
        }
        return Integer.parseInt(textField.getText());
    }


    @Override
    protected void flushComponentValues()
    {
        super.flushComponentValues();
        updepthTextField.setText("0");
        upspaceTextField.setText("0");
        downdepthTextFieled.setText("0");
        downspaceTextField.setText("0");
        rightdepthTextFieled.setText("0");
        rightspaceTextField.setText("0");
        leftdepthTextField.setText("0");
        leftspaceTextField.setText("0");
    }

    @Override
    protected void mergeCompAndValues()
    {
        super.mergeCompAndValues();
        Groove grove = (Groove) getDTO();
        updepthTextField.setText(String.valueOf(grove.getDepthUp()));
        upspaceTextField.setText(String.valueOf(grove.getDistanceUp()));
        downdepthTextFieled.setText(String.valueOf(grove.getDepthDown()));
        downspaceTextField.setText(String.valueOf(grove.getDistanceDown()));
        rightdepthTextFieled.setText(String.valueOf(grove.getDepthRight()));
        rightspaceTextField.setText(String.valueOf(grove.getDistanceRight()));
        leftdepthTextField.setText(String.valueOf(grove.getDepthUp()));
        leftspaceTextField.setText(String.valueOf(grove.getDistanceLeft()));
    }

    @Override
    protected void createRules()
    {
        rules = new HashMap<JCheckBox, JComponent[]>();
        rules.put(upChBox, new JComponent[]{updepthTextField, upspaceTextField});
        rules.put(downChBox, new JComponent[]{downdepthTextFieled, downspaceTextField});
        rules.put(rightChBox, new JComponent[]{rightdepthTextFieled, rightspaceTextField});
        rules.put(leftChBox, new JComponent[]{leftdepthTextField, leftspaceTextField});
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
