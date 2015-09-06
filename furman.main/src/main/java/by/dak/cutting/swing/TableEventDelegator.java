/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.swing;

import org.jdesktop.application.Action;

/**
 * Interface for handling mouse actions
 *
 * @author Rudak Alexei
 */
public interface TableEventDelegator
{
    @Action
    void onMouseClick();

    @Action
    void onMouseDbClick();

    @Action
    void onMouseRight();

    @Action
    void onMouseLeft();

    void onKeyDelete();

    void updateGrid();
}
