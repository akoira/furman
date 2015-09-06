package by.dak.cutting.doors.mech.panels;

import com.jgoodies.validation.ValidationResult;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 21.08.2009
 * Time: 12:56:06
 * To change this template use File | Settings | File Templates.
 */
public interface WaringPanel
{
    public ValidationResult validateGUI();

    public JPanel getPanel();
}
