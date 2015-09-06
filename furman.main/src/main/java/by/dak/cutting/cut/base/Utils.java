/*
 * Utils.java
 *
 * Created on 28. jen 2006, 21:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.base;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.cut.common.Common;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Peca
 */
public class Utils
{

    public static final int MINUTE = 1000 * 60;
    public static final int SECOND = 1000;

    /**
     * Creates a new instance of ReportUtils
     */
    public static int getMinElementSize(Element[] elements)
    {
        int min = Integer.MAX_VALUE;
        for (Element el : elements)
        {
            if (min > Math.min(el.getWidth(), el.getHeight())) min = Math.min(el.getWidth(), el.getHeight());
        }
        return min;
    }

    /**
     * Najde nejmensi rozmer mensiho rozmeru elementu
     */
    public static int getMinMinElementSize(Element[] elements)
    {
        int min = Integer.MAX_VALUE;
        for (Element el : elements)
        {
            int size = Math.min(el.getWidth(), el.getHeight());
            if (min > size) min = size;
        }
        return min;
    }

    /**
     * Najde nejmensi rozmer vetsiho rozmeru elementu
     */
    public static int getMinMaxElementSize(Element[] elements)
    {
        int min = Integer.MAX_VALUE;
        for (Element el : elements)
        {
            int size = Math.max(el.getWidth(), el.getHeight());
            if (min > size) min = size;
        }
        return min;
    }


    public static int getElementsTotalArea(Element[] elements)
    {
        int area = 0;
        for (Element el : elements)
        {
            area += el.getArea();
        }
        return area;
    }

    public static Element[] generateElements(int count, int minSize, int maxSize, int grid, long seed)
    {
        Element[] elements = new Element[count];

        int i = 0;
        while (i < count)
        {
            int w = Math.max(Common.nextInt((maxSize / grid) + 1) * grid, minSize);
            int h = Math.max(Common.nextInt((maxSize / grid) + 1) * grid, minSize);
            int cnt = Common.nextInt(1);
            for (int j = cnt; (j >= 0) && (i < count); j--)
            {
                elements[i] = new Element(w, h, i);
                i++;
            }
        }
        return elements;
    }

    public static int[] arrayListToIntArray(ArrayList<Integer> list)
    {
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++)
        {
            array[i] = list.get(i);
        }
        return array;
    }

    public static boolean shouldStop()
    {
        try
        {
            return (System.in.available() > 0);
        }
        catch (IOException ex)
        {
            return false;
        }
    }


    public static final int PICTFILTER = 1;

    public static Long getNumberOrDaysPassed(Date now, Date st)
    {
        Long h = now.getTime() - st.getTime();
        h = h / (3600 * 24 * 1000);
        return h;
    }

    public static Date getCurrentTime()
    {
        Calendar c = new GregorianCalendar();
        return c.getTime();
    }

    public static Float strToFloat(String str)
    {
        if (str != null && str.length() > 0)
        {
            return Float.parseFloat(str);
        }
        else
            return 0.0f;
    }

    public static String floatToStr(Float f)
    {
        if (f != null)
        {
            return Float.toString(f);
        }
        else
            return "0";
    }

    public static Float getFValue(Float b)
    {
        DecimalFormat df = new DecimalFormat("0.00");
        String ff = df.format(b).replace(',', '.');
        return Float.parseFloat(ff);
    }

    public static void deleteFile(File file)
    {
        if (!file.exists())
        {
            return;
        }
        if (file.isDirectory())
        {
            for (File f : file.listFiles())
                deleteFile(f);
            file.delete();
        }
        else
        {
            file.delete();
        }
    }

    public static JFileChooser createFileFilter(JFileChooser jf, int type)
    {
        FileNameExtensionFilter f1 = new FileNameExtensionFilter("JPEG Image", "jpeg", "jpg");
        FileNameExtensionFilter f2 = new FileNameExtensionFilter("GIF Image", "gif");
        FileNameExtensionFilter f3 = new FileNameExtensionFilter("PNG Image", "png");
        switch (type)
        {
            case 1: // For Pictures
                jf.addChoosableFileFilter(f1);
                jf.addChoosableFileFilter(f2);
                jf.addChoosableFileFilter(f3);
                break;
            default:
                break;
        }
        return jf;
    }

    public static Rectangle GetScreenRes()
    {
        JFrame frame = new JFrame(GraphicsEnvironment.
                getLocalGraphicsEnvironment().
                getDefaultScreenDevice().
                getDefaultConfiguration());

        return frame.getGraphicsConfiguration().getBounds();
    }


    public static JDialog toScreenCenter(JDialog dlg)
    {
        Rectangle screenRect = GetScreenRes();
        int x = screenRect.x + screenRect.width / 2 - dlg.getSize().width / 2;
        int y = screenRect.y + screenRect.height / 2 - dlg.getSize().height / 2;
        dlg.setLocation(x, y);
        return dlg;
    }

    public static JFrame toScreenCenter(JFrame dlg)
    {
        Rectangle screenRect = GetScreenRes();
        int x = screenRect.x + screenRect.width / 2 - dlg.getSize().width / 2;
        int y = screenRect.y + screenRect.height / 2 - dlg.getSize().height / 2;
        dlg.setLocation(x, y);
        return dlg;
    }

    public static String getSaveValue(String str)
    {
        return (str != null && !str.isEmpty()) ? str : "0";
    }

    public static Integer getSaveValue(Integer value)
    {
        return (value != null) ? value : 0;
    }

    public static String getResourceString(Class clazz, String param)
    {
        ResourceMap rsMap = Application.getInstance(
                CuttingApp.class).getContext().getResourceMap(clazz);
        return rsMap.getString(param);
    }


}
