package by.dak.plastic.process;

import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Order;
import by.dak.plastic.swing.DSPPlasticValue;

/**
 * User: akoyro
 * Date: 23.09.11
 * Time: 12:49
 */
public class TFurnitureProcessor
{
    public static void main(String[] args)
    {
        new SpringConfiguration();


        Order order = FacadeContext.getOrderFacade().findUniqueByField(Order.PROPERTY_name, "TEST_PLASTIC");
        CuttingModel cuttingModel = FacadeContext.getStripsFacade().loadCuttingModel(order).load();

        CuttingModel2DSPPlasticDetailsProcess process = new CuttingModel2DSPPlasticDetailsProcess();
        process.setCuttingModel(cuttingModel);
        DSPPlasticValue value = null;
        process.setPlasticValue(value);
        process.process();
        System.out.println(process.getResult());
    }
}
