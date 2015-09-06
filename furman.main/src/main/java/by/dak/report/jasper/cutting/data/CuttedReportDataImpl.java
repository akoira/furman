package by.dak.report.jasper.cutting.data;

import by.dak.persistence.entities.AOrder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author akoyro
 * @version 0.1 02.02.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class CuttedReportDataImpl implements CuttedReportData
{
    private AOrder order;
    private List<MaterialCuttedData> list = new ArrayList<MaterialCuttedData>();

    @Override
    public AOrder getOrder()
    {
        return order;
    }

    public void setOrder(AOrder order)
    {
        this.order = order;
    }

    @Override
    public List<MaterialCuttedData> getMaterials()
    {
        return Collections.unmodifiableList(list);
    }

    public void add(MaterialCuttedData data)
    {
        list.add(data);
    }
}
