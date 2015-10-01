package by.dak.template.swing;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.swing.table.CellContext;
import by.dak.persistence.FacadeContext;
import by.dak.swing.ButtonEditor;
import by.dak.swing.table.AListUpdater;
import by.dak.template.TemplateOrder;
import nl.jj.swingx.gui.modal.JModalFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;

/**
 * User: akoyro
 * Date: 21.03.11
 * Time: 16:00
 */
public class ImageViewEditor extends ButtonEditor
{
    private File file;
    private AListUpdater<TemplateOrder> updater;
    private Delegator delegator;

    public ImageViewEditor(AListUpdater<TemplateOrder> updater)
    {
        this.updater = updater;
        this.delegator = new Delegator();
        setActionListener(delegator);
        setResultValue(delegator);
    }


    public class Delegator implements ActionListener, ReturnValue
    {
        private CellContext cellContext;

        @Override
        public Object getValue()
        {
            return cellContext.getValue();
        }

        @Override
        public void setCommitAction(Action action)
        {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setCancelAction(Action action)
        {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setCellContext(CellContext cellContext)
        {
            this.cellContext = cellContext;
        }

        @Override
        public void hidePopup()
        {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            TemplateOrder templateOrder = updater.getList().get(cellContext.getRow());
            if (templateOrder != null && templateOrder.getFileUuid() != null)
            {
                try
                {
                    file = FacadeContext.getRepositoryService().readTempFile(templateOrder.getFileUuid());
                    final Image screenImage = Toolkit.getDefaultToolkit().getImage(file.getAbsolutePath());

                    final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                    final JModalFrame frame = new JModalFrame()
                    {
                        public void paint(Graphics g)
                        {
                            if (screenImage != null) // if screenImage is not null (image loaded and ready)
                                g.drawImage(screenImage, // draw it
                                        0, 0,
                                        640,
                                        480,
                                        this);
                            // to draw image at the center of screen
                            // we calculate X position as a half of screen width minus half of image width
                            // Y position as a half of screen height minus half of image height
                        }
                    };
                    frame.setTitle(templateOrder.getFileUuid());
                    frame.setUndecorated(true);
                    frame.setLocation(dimension.width / 2 - 640 / 2, dimension.height / 2 - 480 / 2);
                    frame.setSize(640, 480);
                    frame.addFocusListener(new FocusAdapter()
                    {
                        @Override
                        public void focusLost(FocusEvent e)
                        {
                            cancelCellEditing();
                            frame.dispose();
                            file.deleteOnExit();
                            screenImage.flush();
                        }
                    });
                    frame.setVisible(true);
                }
                catch (Throwable throwable)
                {
                    CuttingApp.getApplication().getExceptionHandler().handle(throwable);
                } finally {
                    if (file != null) {
                        file.deleteOnExit();
                    }
                }

            }
        }
    }
}
