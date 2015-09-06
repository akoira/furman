package by.dak.autocad.com;

import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.persistence.FacadeContext;
import by.dak.test.TestUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * User: akoyro
 * Date: 22.02.11
 * Time: 11:30
 */
public class TAutocadCom
{
    public static void main(String[] args)
    {
        try
        {
            TestUtils.showFrameWithButtonAction(new AbstractAction()
            {

                @Override
                public void actionPerformed(ActionEvent actionEvent)
                {
                    SpringConfiguration springConfiguration = new SpringConfiguration();
                    OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
                    orderDetailsDTO.setLength(1000l);
                    orderDetailsDTO.setWidth(900l);

                    FacadeContext.getAutocadFacade().startApplication(orderDetailsDTO);
                    FacadeContext.getAutocadFacade().newMilling(orderDetailsDTO);
                }
            });

            while (true)
            {
                Thread.sleep(1000);
            }

        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }
        finally
        {
        }
    }

}
