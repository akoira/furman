package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import by.dak.persistence.entities.OrderFurniture;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jhotdraw.draw.io.DOMStorableInputOutputFormat;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * User: akoyro
 * Date: 16.07.2009
 * Time: 9:31:07
 */
public class MillingConverter
{
    private static final Log LOGGER = LogFactory.getLog(MillingConverter.class.getName());

    private DOMStorableInputOutputFormat ioFormat = new DOMStorableInputOutputFormat(new MillingFigureFactory());


    public String save(ElementDrawing elementDrawing)
    {
        if (!elementDrawing.isMilling())
        {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try
        {
            ioFormat.write(byteArrayOutputStream, elementDrawing);
            byteArrayOutputStream.flush();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            String s = new String(bytes, "UTF8");
            return s;
        }
        catch (IOException e1)
        {
            LOGGER.error(e1);
        }
        finally
        {
            try
            {
                byteArrayOutputStream.close();
            }
            catch (IOException e1)
            {
                LOGGER.warn(e1);
            }
        }
        return null;
    }

    public void restore(String string, ElementDrawing elementDrawing)
    {

        ByteArrayInputStream inputStream = null;
        try
        {
            inputStream = new ByteArrayInputStream(string.getBytes("UTF8"));
            ioFormat.read(inputStream, elementDrawing, true);
        }
        catch (UnsupportedEncodingException e)
        {
            LOGGER.warn(e);
        }
        catch (IOException e)
        {
            LOGGER.warn(e);
        }
        finally
        {

            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    LOGGER.warn(e);
                }
            }
        }
    }

    public ElementDrawing getElementDrawing(OrderFurniture furniture)
    {
        Dimension element = new Dimension(furniture.getLength().intValue(), furniture.getWidth().intValue());
        ElementDrawing elementDrawing = new ElementDrawing();
        elementDrawing.setOffset(0);
        elementDrawing.setElement(element);
        MillingConverter millingConverter = new MillingConverter();
        millingConverter.restore(furniture.getMilling(), elementDrawing);
        return elementDrawing;
    }


}
