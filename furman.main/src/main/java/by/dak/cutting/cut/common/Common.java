/*
 * Common.java
 *
 * Created on 9. kv�ten 2007, 11:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.common;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.guillotine.Strips;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashSet;
import java.util.Random;

/**
 * @author Peca
 */
public class Common
{
    private static Random rnd = new Random();

    public static Element[] loadElementsFromXmlFile(String fileName)
    {
        try
        {
            DocumentBuilder docBuilder;
            Document doc = null;
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setIgnoringElementContentWhitespace(true);
            docBuilder = docBuilderFactory.newDocumentBuilder();
            File sourceFile = new File(fileName);
            doc = docBuilder.parse(sourceFile);

            org.w3c.dom.Node node;
            org.w3c.dom.Element nel;
            org.w3c.dom.Element root = doc.getDocumentElement();
            NodeList nl = root.getElementsByTagName("WorkSheet");

            nl = root.getElementsByTagName("Element");
            Element[] elements = new Element[nl.getLength()];
            for (int i = 0; i < nl.getLength(); i++)
            {
                nel = (org.w3c.dom.Element) nl.item(i);
                int w = Integer.parseInt(nel.getAttribute("width"));
                int h = Integer.parseInt(nel.getAttribute("height"));
                Element el = new Element(w, h, i);
                elements[i] = el;
            }
            return elements;
        }
        catch (Exception ex)
        {

        }
        return null;
    }

    public static int nextInt(int n)
    {
        return rnd.nextInt(n);
    }

    public static int nextInt(int min, int max)
    {
        return rnd.nextInt(max - min) + min;
    }

    public static int nextIntGauss(int min, int max, int mean)
    {
        double num = rnd.nextGaussian() * (max - min) * 0.5 + mean;
        return Math.min(max, Math.max(min, (int) num));
    }

    public static int nextIntGauss(int min, int max, int mean, int sigma)
    {
        double num = rnd.nextGaussian() * (sigma) * 0.5 + mean;
        return Math.min(max, Math.max(min, (int) num));
    }

    public static int nextInt()
    {
        return rnd.nextInt();
    }

    public static float nextFloat()
    {
        return rnd.nextFloat();
    }

    public static boolean nextBoolean()
    {
        return rnd.nextBoolean();
    }


    public static boolean luck(float probability)
    {
        return rnd.nextFloat() < probability;
    }

    public static Element[] createRandomeElements(int count, int grid, int minSize, int maxSize, int maxRepetions)
    {
        int i = 0;
        Element[] elements = new Element[count];
        while (i < count)
        {
            int w = Math.max(Common.nextInt((maxSize / grid) + 1) * grid, minSize);
            int h = Math.max(Common.nextInt((maxSize / grid) + 1) * grid, minSize);
            int cnt = Common.nextInt(maxRepetions) + 1;
            for (int j = cnt; (j >= 0) && (i < count); j--)
            {
                elements[i] = new Element(w, h, i);
                i++;
            }
        }
        assert (i == elements.length);
        return elements;
    }

    public static boolean validateSolution(Strips strips, Element[] elements)
    {
        Element[] cuttedElements = strips.getCuttedElements();
        if (cuttedElements.length != elements.length) return false;

        HashSet<Element> hashset = new HashSet<Element>();
        for (Element element : elements)
        {
            hashset.add(element);
        }

        //zkouska zda tam jsou vsechny elementy
        for (Element element : cuttedElements)
        {
            if (!hashset.contains(element))
            {
                return false;
            }
        }

        //zkouska zda tam neni nejaky element vicekrat
        hashset.clear();
        for (Element element : cuttedElements)
        {
            if (hashset.contains(element))
            {
                return false;
            }
            hashset.add(element);
        }

        return true;
    }
}
