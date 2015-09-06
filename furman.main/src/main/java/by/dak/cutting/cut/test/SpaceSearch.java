/*
 * SpaceSearch.java
 *
 * Created on 22. listopad 2006, 21:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.test;

import by.dak.cutting.cut.base.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

//import

/**
 * @author Peca
 */
public class SpaceSearch
{
    static Element[] elements;
    static WorkArea[] workSheets;
    static AbstractWorkSpace ws;
    static PrimitiveCutter cutter;
    static float elementsTotalArea;
    static int optimumFound = 0;
    static int pos = 0;
    static int cnt = 0;
    static float percentDone = 0;
    static OutputStream os;
    static float bestRating = 0;

    public static void saveToXmlFile(String fileName)
    {
        try
        {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setIgnoringElementContentWhitespace(true);
            Document doc = docBuilderFactory.newDocumentBuilder().newDocument();

            Node root = doc.createElement("GeneticTest1");
            doc.appendChild(root);
            //population.writeToXml(root, doc);

            org.w3c.dom.Element xmlels = doc.createElement("Elements");
            root.appendChild(xmlels);
            for (Element el : elements)
            {
                org.w3c.dom.Element xmlel = doc.createElement("Element");
                xmlel.setAttribute("width", String.valueOf(el.getWidth()));
                xmlel.setAttribute("height", String.valueOf(el.getHeight()));
                //xmlel.setAttribute("id", el.id);
                xmlels.appendChild(xmlel);
            }

            org.w3c.dom.Element elwsheets = doc.createElement("WorkSheets");
            root.appendChild(elwsheets);
            for (WorkArea ws : workSheets)
            {
                org.w3c.dom.Element elws = doc.createElement("WorkSheet");
                elws.setAttribute("width", String.valueOf(ws.getWidth()));
                elws.setAttribute("height", String.valueOf(ws.getHeight()));
                //xmlel.setAttribute("id", el.id);
                elwsheets.appendChild(elws);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            FileOutputStream fos = new FileOutputStream(new File(fileName));
            StreamResult result = new StreamResult(fos);
            transformer.transform(source, result);
            fos.close();
        }
        catch (Exception ex)
        {

        }

    }

    //public static Node getAttribute(Node node, A)

    public static void loadFromXmlFile(String fileName)
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
            workSheets = new WorkArea[nl.getLength()];
            for (int i = 0; i < nl.getLength(); i++)
            {
                nel = (org.w3c.dom.Element) nl.item(i);
                int w = Integer.parseInt(nel.getAttribute("width"));
                int h = Integer.parseInt(nel.getAttribute("height"));
                WorkArea ws = new WorkArea(w, h);
                workSheets[i] = ws;
            }

            nl = root.getElementsByTagName("Element");
            elements = new Element[nl.getLength()];
            for (int i = 0; i < nl.getLength(); i++)
            {
                nel = (org.w3c.dom.Element) nl.item(i);
                int w = Integer.parseInt(nel.getAttribute("width"));
                int h = Integer.parseInt(nel.getAttribute("height"));
                Element el = new Element(w, h, i);
                elements[i] = el;
            }
        }
        catch (Exception ex)
        {

        }
    }

    static long lastTime = 0;
    static long lastProgressTime = 0;
    static int[] ratings;
    static int workSpaceTotalArea = 0;
    static float lastProgress = 0;

    static void permute(int[] v, int start, int n)
    {
        if (start == n - 1)
        {
            ws.clear();
            cutter.cut(elements, v, ws);
            float rating = (float) ws.getRightEmptyArea() / (workSpaceTotalArea - elementsTotalArea);
            //for(int i=0; i < v.length; i++) System.out.print(v[i]);
            if (rating >= bestRating)
            {
                try
                {
                    if (rating > bestRating)
                    {
                        optimumFound = 0;
                        for (int i = 0; i < 50; i++)
                        {
                            os.write('-');
                        }
                        os.write(13);
                        os.write(10);
                    }
                    optimumFound++;
                    bestRating = rating;
                    for (int i = 0; i < v.length; i++)
                    {
                        if (v[i] > 9)
                        {
                            os.write(v[i] / 10 + '0');
                            os.write(v[i] % 10 + '0');
                        }
                        else os.write(v[i] + '0');
                        os.write(',');
                    }
                    os.write(13);
                    os.write(10);
                }
                catch (Exception ex)
                {
                    System.out.println("chyba");
                    System.out.println(ex.getMessage());
                }

                //    System.out.format("optimum: %1d\n", optimumFound);
            }

            int index = (int) (rating * ws.getPlateSize().getWidth());
            ratings[index]++;
            //if(rating > 0.977250f)System.out.print(rating);

            float progress = ((float) pos / cnt) * 100;
            if ((System.currentTimeMillis() - lastTime > 1000))
            {
                long time = System.currentTimeMillis();
                //float estTime = (100.0f/(progress - lastProgress))*(time - lastProgressTime);
                float estTime = (100.0f - progress) / (progress - lastProgress);
                lastProgress = progress;
                lastProgressTime = time;
                System.out.format("progress: %1G%%   ", progress);
                System.out.format("optimum: %1d    ", optimumFound);
                System.out.format("rating: %1G   ", bestRating);
                System.out.format("est. time: %1G (%2G)\n", estTime / 60, estTime / 3600);
                percentDone = progress;
                lastTime = System.currentTimeMillis();
            }

            pos++;
        }
        else
        {
            for (int i = start; i < n; i++)
            {
                int tmp = v[i];

                v[i] = v[start];
                v[start] = tmp;
                permute(v, start + 1, n);
                v[start] = v[i];
                v[i] = tmp;
            }
        }
    }

    public static int fact(int n)
    {
        int f = 1;
        for (int i = 2; i <= n; i++)
        {
            f = f * i;
        }
        return f;
    }

    public static void main(String args[])
    {
        java.lang.Thread.currentThread().setPriority(java.lang.Thread.MIN_PRIORITY);
        if (args.length < 1) return;
        String fileName = args[0];
        //loadFromXmlFile(args[0]);
        //System.out.println("ryba");
        //String fileName = "1.xml";
        loadFromXmlFile(fileName);

        int[] order = new int[elements.length];
        for (int i = 0; i < order.length; i++) order[i] = i;

        ws = new WorkSpace(workSheets);
        cutter = new PrimitiveCutter();
        ratings = new int[ws.getPlateSize().getWidth() + 1];
        elementsTotalArea = Utils.getElementsTotalArea(elements);
        workSpaceTotalArea = ws.getTotalArea();

        ws.minMaxElementSize = Utils.getMinMaxElementSize(elements);
        ws.minMinElementSize = Utils.getMinMinElementSize(elements);

        for (int i = 0; i < ratings.length; i++) ratings[i] = 0;

        try
        {
            os = new FileOutputStream(new File(fileName + ".best"));

            cnt = fact(order.length);
            permute(order, 0, order.length);
            //permute(order, 0, 1);
            os.close();

            os = new FileOutputStream(new File(fileName + ".ratings"));
            OutputStreamWriter osw = new OutputStreamWriter(os);


            for (int i = 0; i < ratings.length; i++)
            {
                float r = ((float) i / ws.getPlateSize().getWidth());
                //osw.write(String.format("%1$5.2f:  %2$d", r, ratings[i]));
                if (ratings[i] > 0)
                {
                    osw.write(String.format("%1f:  %2d\n", r, ratings[i]));
                }
            }
            osw.flush();
            os.close();
        }
        catch (Exception ex)
        {
            System.out.println("chyba");
            System.out.println(ex.getMessage());
        }

        //System.out.format("\n %1$5.2f  %2$s" ,1.5f, "ryba");
    }

}
