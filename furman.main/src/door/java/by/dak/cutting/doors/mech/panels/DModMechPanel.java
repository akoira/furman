package by.dak.cutting.doors.mech.panels;

import com.jgoodies.validation.ValidationResultModel;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 24.08.2009
 * Time: 9:46:38
 * To change this template use File | Settings | File Templates.
 */
public class DModMechPanel<V extends WaringPanel> extends by.dak.cutting.swing.DModPanel
{
    V panel;

    public DModMechPanel(V panel)
    {
        this.panel = panel;
        initComponents();
    }

    @Override
    public void save()
    {
    }

    @Override
    public Boolean validateGUI()
    {
        ValidationResultModel validationResultModel = getWarningList().getModel();
        validationResultModel.setResult(panel.validateGUI());
        getWarningList().setModel(validationResultModel);

        if (!validationResultModel.hasErrors())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void initComponents()
    {
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(layout.createSequentialGroup()
                                .addContainerGap()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                        .add(panel.getPanel(), org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                        .add(getWarningList(), org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .add(panel.getPanel(), org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(getWarningList(), org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                .addContainerGap())
        );
    }

    public V getPanel()
    {
        return panel;
    }

}

