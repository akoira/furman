/*
 * SVTabCtrl.java
 *
 * Created on 8 ��� 2007 �., 13:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.swing;

import com.jidesoft.swing.JideTabbedPane;

/**
 * Modyfied JTabbedPane control
 *
 * @author Rudak Alexei
 */
public class BaseTabCtrl extends JideTabbedPane
{
    private Integer selTab = 0;

    /**
     * Creates a new instance of SVTabCtrl
     */
    public BaseTabCtrl()
    {
    }

    public int getFromTab()
    {
        return selTab;
    }

    public int getToTab()
    {
        return getSelectedIndex();
    }

    public void setSelTab(int selTab)
    {
        this.selTab = selTab;
    }

}
