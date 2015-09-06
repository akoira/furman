package by.dak.furman.templateimport.swing;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * User: akoyro
 * Date: 11/17/13
 * Time: 4:47 PM
 */
public class Main
{
    public static void main(String[] args)
    {
        final JFrame frame = new JFrame();
        //frame.add(new ProgressBarPanel());


        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowOpened(WindowEvent e)
            {
                //Platform.runLater(mew );
                frame.pack();
            }

            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        frame.setVisible(true);

    }
}
