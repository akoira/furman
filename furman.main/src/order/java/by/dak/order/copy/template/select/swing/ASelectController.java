package by.dak.order.copy.template.select.swing;

import by.dak.common.swing.IController;
import by.dak.persistence.MainFacade;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.swingx.JXPanel;

public abstract class ASelectController<P extends JXPanel> implements IController<P>
{
    protected ApplicationContext applicationContext;
    private MainFacade mainFacade;

    public MainFacade getMainFacade()
    {
        return mainFacade;
    }

    public void setMainFacade(MainFacade mainFacade)
    {
        this.mainFacade = mainFacade;
    }

    public ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }
}
