package by.dak.view3d;

import by.dak.cutting.swing.DPanel;

import java.awt.*;

/**
 * User: akoyro
 * Date: 12.09.11
 * Time: 21:54
 */
public class View3dPanel extends DPanel
{

    private View3dController view3dController = new View3dController();

    public View3dPanel()
    {
        setLayout(new BorderLayout());
        view3dController.setView3dPanel(this);
    }

    public void init()
    {
        //view3dController.init();
    }

    public View3dController getView3dController()
    {
        return view3dController;
    }

}