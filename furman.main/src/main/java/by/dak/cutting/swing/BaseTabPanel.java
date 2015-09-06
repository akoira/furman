package by.dak.cutting.swing;

import by.dak.swing.HasValue;
import by.dak.swing.SwingUtils;
import by.dak.utils.BindingUtils;
import by.dak.utils.DBindingGroup;
import by.dak.utils.validator.ValidatorAnnotationProcessor;
import com.jgoodies.validation.ValidationResult;
import org.jdesktop.application.Application;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Tab class for TabCtrl
 *
 * @author Rudak Alexei
 */
public class BaseTabPanel<V> extends DPanel implements HasValue<V>
{


    protected ActionMap actionMap = Application.getInstance().getContext().getActionMap(BaseTabPanel.class,
            this);


    protected ButtonListener btLst
            = new ButtonListener();
    protected MouseTableHandler msHdlr
            = new MouseTableHandler();


    private DBindingGroup bindingGroup = new DBindingGroup();
    private V value;

    //используется для отображение ошибки в tab
    private WarningList warningList;


    protected BaseTabPanel(boolean changeBindingSource)
    {
        if (changeBindingSource)
        {
            addPropertyChangeListener("value", new ValueChangedController());
        }

        addPropertyChangeListener("editable", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                SwingUtils.setPropertyForAllComponents("editable", evt.getNewValue(), BaseTabPanel.this);
            }
        });

    }

    /**
     * Creates a new instance of SVTabSimple
     */
    public BaseTabPanel()
    {
        this(true);
    }

    public ValidationResult validateGui()
    {
        return ValidatorAnnotationProcessor.getProcessor().validate(getValue());
    }


    protected void onNew()
    {

    }

    protected void onOpen()
    {

    }

    protected void onDel()
    {

    }

    protected void onDelAll()
    {

    }

    protected void onMouseClick(String table, Long id)
    {

    }

    protected void onMouseDbClick(String table, Long id)
    {

    }

    protected void onMouseRight()
    {

    }

    protected void onMouseLeft()
    {

    }

    public void buttonProc(String cmd)
    {

    }

    public void enableTab(boolean f, Container cmp)
    {
        int count = cmp.getComponentCount();
        if (count == 0)
        {
            cmp.setEnabled(f);
            return;
        }
        else
        {
            for (int a = 0; a < cmp.getComponentCount(); a++)
            {
                Container o = (Container) cmp.getComponent(a);
                o.setEnabled(f);
                enableTab(f, o);
            }
        }
    }

    public DBindingGroup getBindingGroup()
    {
        return bindingGroup;
    }

    public V getValue()
    {
        return value;
    }

    public void setValue(V value)
    {
        V old = this.value;
        this.value = value;
        firePropertyChange("value", old, value);
    }

    //invoked when new value is set
    protected void valueChanged()
    {

    }

    public class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String cmd = e.getActionCommand();
            if (cmd.equals("NEW"))
            {
                onNew();
            }
            else if (cmd.equals("OPEN"))
            {
                onOpen();
            }
            else if (cmd.equals("DEL"))
            {
                onDel();
            }
            else if (cmd.equals("DELALL"))
            {
                onDelAll();
            }
            else
                buttonProc(cmd);

        }
    }

    class MouseTableHandler extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            BaseTable t = (BaseTable) e.getSource();
            TableModel tm = t.getModel();
            if (e.getClickCount() == 2)
            {
                onMouseDbClick(t.getName(),
                        (Long) tm.getValueAt(t.getSelectedRow(), 0));
            }
            else
                onMouseClick(t.getName(),
                        (Long) tm.getValueAt(t.getSelectedRow(), 0));
        }

        public void mousePressed(MouseEvent e)
        {
            if (SwingUtilities.isRightMouseButton(e))
            {
                onMouseRight();
            }
            if (SwingUtilities.isLeftMouseButton(e))
            {
                onMouseLeft();
            }
        }
    }

    public class ValueChangedController implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            BindingUtils.setSourceObject(getBindingGroup(), getValue());
            valueChanged();
        }
    }

    public WarningList getWarningList()
    {
        return warningList;
    }

    public void setWarningList(WarningList warningList)
    {
        this.warningList = warningList;
    }
}
