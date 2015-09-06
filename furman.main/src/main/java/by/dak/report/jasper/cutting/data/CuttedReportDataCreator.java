package by.dak.report.jasper.cutting.data;

import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.persistence.entities.OrderFurniture;

/**
 * @author akoyro
 * @version 0.1 08.02.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class CuttedReportDataCreator extends ACuttedReportDataCreator<OrderFurniture>
{
    public CuttedReportDataCreator(CuttingModel cuttingModel)
    {
        super(cuttingModel);
    }
}