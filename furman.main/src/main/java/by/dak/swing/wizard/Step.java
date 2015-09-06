package by.dak.swing.wizard;

import by.dak.cutting.swing.DPanel;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPanel;
import org.netbeans.spi.wizard.WizardPanelNavResult;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * @author akoyro
 * @version 0.1 08.02.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public abstract class Step<V extends JComponent> extends DPanel implements WizardPanel
{
    private V view;

    public Step(V view)
    {
        super(new BorderLayout());
        setView(view);
    }


    @Override
    public WizardPanelNavResult allowBack(String s, Map map, Wizard wizard)
    {
        return WizardPanelNavResult.PROCEED;
    }

    @Override
    public WizardPanelNavResult allowFinish(String s, Map map, Wizard wizard)
    {
        return WizardPanelNavResult.PROCEED;
    }

    @Override
    public WizardPanelNavResult allowNext(String s, Map map, Wizard wizard)
    {
        if (validate(map, wizard))
        {
            proceedNext(map, wizard);
            return WizardPanelNavResult.PROCEED;
        }
        else
        {
            remainOnPage(map, wizard);
            return WizardPanelNavResult.REMAIN_ON_PAGE;
        }
    }

    protected abstract boolean validate(Map map, Wizard wizard);

    protected abstract void proceedNext(Map map, Wizard wizard);

    protected abstract void remainOnPage(Map map, Wizard wizard);


    public V getView()
    {
        return view;
    }

    public void setView(V view)
    {
        if (view != null)
        {
            add(view, BorderLayout.CENTER);
        }
        this.view = view;
    }

}
