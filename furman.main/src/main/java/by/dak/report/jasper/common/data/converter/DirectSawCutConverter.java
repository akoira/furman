package by.dak.report.jasper.common.data.converter;

import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.persistence.entities.OrderFurniture;

/**
 * @author Denis Koyro
 * @version 0.1 29.03.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class DirectSawCutConverter extends ADirectSawCutConverter<OrderFurniture>
{
    public DirectSawCutConverter(CuttingModel cuttingModel)
    {
        super(cuttingModel);
    }
}