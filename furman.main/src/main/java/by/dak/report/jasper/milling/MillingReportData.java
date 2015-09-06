/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.report.jasper.milling;

import by.dak.cutting.swing.order.data.Milling;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.report.jasper.ReportUtils;
import by.dak.utils.convert.StringValueAnnotationProcessor;

import java.io.File;

/**
 * @author admin
 */
public class MillingReportData
{

    private Value value = new Value();

    public MillingReportData(OrderFurniture furniture)
    {
        prepare(furniture, value);
    }

    private void prepare(OrderFurniture furniture, Value value)
    {
        if (furniture != null)
        {
            Milling milling = (Milling) XstreamHelper.getInstance().fromXML(furniture.getMilling());
            value.name = StringValueAnnotationProcessor.getProcessor().convert(furniture);
            File file = FacadeContext.getRepositoryService().readTempFile(milling.getImageFileUuid());
            value.path = file.getAbsolutePath();
            if (milling.getBorderDef() != null && milling.getTexture() != null)
            {
                value.material = ReportUtils.formatGlueingValue(milling.getBorderDef(), milling.getTexture());
            }
        }
    }

    public String getPath()
    {
        return value.path;
    }

    public String getName()
    {
        return value.name;
    }

    public String getMaterial()
    {
        return value.material;
    }


    public static class Value
    {
        public String path;
        public String name = "";
        public String material = "";
    }
}