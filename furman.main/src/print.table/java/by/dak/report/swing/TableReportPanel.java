package by.dak.report.swing;

import by.dak.cutting.swing.BaseTabPanel;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 14.10.11
 * Time: 18:43
 * To change this template use File | Settings | File Templates.
 */
public class TableReportPanel extends BaseTabPanel<JasperPrint>
{

    public TableReportPanel()
    {
        setLayout(new BorderLayout());
    }

    @Override
    protected void valueChanged()
    {
        showReport();
    }

    private void showReport()
    {
        if (getValue() != null)
        {
            if (getValue().getPages() != null && getValue().getPages().size() > 0)
            {
                final JRViewer jrViewer = new JRViewer(getValue(), Locale.getDefault());

                add(jrViewer, BorderLayout.CENTER);
                validate();
                Runnable runnable = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        jrViewer.setFitPageZoomRatio();
                    }
                };
                SwingUtilities.invokeLater(runnable);
            }
        }
    }
}
