package by.dak.order.copy.template.select.swing;

import by.dak.cutting.SpringConfiguration;
import by.dak.order.copy.template.select.swing.tree.action.ActionLoadCategories;
import org.jdesktop.application.SingleFrameApplication;

import javax.swing.*;

public class TSelectPanel extends SingleFrameApplication
{

    @Override
    protected void startup()
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();

        SelectPanelController selectPanelController = new SelectPanelController();
        selectPanelController.setApplicationContext(getContext());
        selectPanelController.setMainFacade(springConfiguration.getMainFacade());
        selectPanelController.init();


        JFrame frame = new JFrame();
        setMainFrame(frame);
        frame.setContentPane(selectPanelController.getComponent());
        show(getMainFrame());

        ActionLoadCategories actionLoadCategoriesNode = new ActionLoadCategories();
        actionLoadCategoriesNode.setCategoryNode(null);
        actionLoadCategoriesNode.setSelectPanelController(selectPanelController);

        actionLoadCategoriesNode.execute();
    }

    public static void main(String[] args)
    {

        TSelectPanel.launch(TSelectPanel.class, args);
    }

}
