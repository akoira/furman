package by.dak.swing.image;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.test.TestUtils;
import nl.jj.swingx.gui.modal.JModalFrame;
import org.jdesktop.application.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * User: akoyro
 * Date: 03.03.11
 * Time: 14:19
 */
public class TImage
{
    public static void main(String[] args) throws FileNotFoundException
    {

        SpringConfiguration springConfiguration = new SpringConfiguration();
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
        String suuid = uuid.toString();

        InputStream inputStream = TImage.class.getResourceAsStream("test.pdf");

        FacadeContext.getRepositoryService().store(inputStream, suuid);
        FacadeContext.getRepositoryService().read(suuid, new File("test.pdf"));

        if (true)
            return;

        final JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        JButton jButton = new JButton();
        jPanel.add(jButton, BorderLayout.CENTER);
        jButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                final JFileChooser fc = new JFileChooser();

                Application.getInstance().getContext().getSessionStorage().putProperty(JFileChooser.class, new FileChooserProperty());

                fc.setName("Test");
                try
                {
                    Application.getInstance().getContext().getSessionStorage().restore(fc, "Test");
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                fc.addChoosableFileFilter(new ImageFilter());
                fc.setAcceptAllFileFilterUsed(false);

                //Add custom icons for file types.
                //fc.setFileView(new ImageFileView(fc));

                //Add the preview pane.
                fc.setAccessory(new ImagePreview(fc));


                int result = fc.showDialog(jPanel, "Select");
                try
                {
                    Application.getInstance().getContext().getSessionStorage().save(fc, "Test");
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                if (result == JFileChooser.APPROVE_OPTION)
                {
                    final Image screenImage = Toolkit.getDefaultToolkit().getImage(fc.getSelectedFile().getAbsolutePath());
                    JModalFrame frame = new JModalFrame()
                    {
                        public void paint(Graphics g)
                        {
                            if (screenImage != null) // if screenImage is not null (image loaded and ready)
                                g.drawImage(screenImage, // draw it
                                        0, // at the center
                                        0,
                                        640,
                                        480,
                                        this);
                            // to draw image at the center of screen
                            // we calculate X position as a half of screen width minus half of image width
                            // Y position as a half of screen height minus half of image height
                        }
                    };
                    frame.setUndecorated(true);
                    frame.setSize(640, 480);
                    frame.setVisible(true);
                }


            }
        });
        TestUtils.showFrame(jPanel, "Test");

    }
}
