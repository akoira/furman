package by.dak.cutting;

import by.dak.common.swing.ExceptionHandler;
import com.jidesoft.dialog.JideOptionPane;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;

import javax.swing.FocusManager;
import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 18.01.2009
 * Time: 14:26:23
 * To change this template use File | Settings | File Templates.
 */
public class DefaultExceptionHandler implements ExceptionHandler {
    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(DefaultExceptionHandler.class);

    @Override
    @AfterThrowing(argNames = "e")
    public void handle(final Throwable e) {
        LOGGER.error(e.getMessage(), e);
        Runnable runnable = () -> {
            JTextArea textArea = new JTextArea(ExceptionUtils.getStackTrace(e));
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            scrollPane.setPreferredSize(new Dimension(500, 500));
            int o = JideOptionPane.showConfirmDialog(FocusManager.getCurrentManager().getActiveWindow(), scrollPane, e.getLocalizedMessage(),
                    JideOptionPane.OK_CANCEL_OPTION,
                    JideOptionPane.ERROR_MESSAGE);
            if (o == JideOptionPane.CANCEL_OPTION)
                System.exit(-1);
        };
        SwingUtilities.invokeLater(runnable);
    }

    @Override
    public void handle(Object o, final Throwable e) {
        LOGGER.error(e.getMessage(), e);
        Runnable runnable = () -> JOptionPane.showMessageDialog(FocusManager.getCurrentManager().getActiveWindow(), e.getLocalizedMessage(),
                e.getLocalizedMessage(), JOptionPane.ERROR_MESSAGE);
        SwingUtilities.invokeLater(runnable);
    }

}
