/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Peca
 */
public class XmlSerializer
{
    private XStream xstream;

    public XmlSerializer()
    {
        xstream = new XStream(new DomDriver());
    }

    public void writeToXmlFile(Object o, String fileName) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(fileName);
        xstream.toXML(o, fos);
        fos.close();
    }

    public void alias(String alias, Class classType)
    {
        xstream.alias(alias, classType);
    }

    public Object readFromXmlFile(String fileName) throws IOException
    {
        FileInputStream fis = new FileInputStream(fileName);
        Object o = xstream.fromXML(fis);
        fis.close();
        return o;
    }

}
