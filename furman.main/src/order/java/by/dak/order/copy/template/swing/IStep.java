package by.dak.order.copy.template.swing;

import javax.swing.*;

/**
 * User: akoyro
 * Date: 11/23/13
 * Time: 9:51 AM
 */
public interface IStep
{
    String getName();

    String getDescription();

    JComponent getView();
}
