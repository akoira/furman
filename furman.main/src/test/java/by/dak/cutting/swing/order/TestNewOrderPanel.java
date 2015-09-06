package by.dak.cutting.swing.order;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import org.jdesktop.application.FrameView;

/**
 * @author akoyro
 * @version 0.1 28.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class TestNewOrderPanel
{

    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();

        NewOrderPanel newOrderPanel = new NewOrderPanel();
        newOrderPanel.setOrder(FacadeContext.getOrderFacade().initNewOrderEntity("test"));

        FrameView frameView = new FrameView(CuttingApp.getApplication());
        frameView.setComponent(newOrderPanel);

        CuttingApp.getApplication().show(frameView);
    }

}
