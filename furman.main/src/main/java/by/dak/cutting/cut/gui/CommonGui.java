/*
 * CommonGui.java
 *
 * Created on 28. ��jen 2006, 18:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui;

import by.dak.cutting.cut.base.AbstractWorkSpace;
import by.dak.cutting.cut.base.CuttedElement;
import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.PIndividual;
import by.dak.cutting.cut.guillotine.*;

import javax.swing.*;

/**
 * @author Peca
 */
public class CommonGui
{

    private static Element[] elements;
    private static Strips strips;
    private static float forceMinimumRatio = 0.0f;
    private static boolean forceBestFit = false;

    public static IndividualVisualizer visualizeIndividual(int x, int y, Individual individual)
    {

        if (individual.getClass() == GTreeIndividual.class)
        {
            GTreeIndividualVisuallizer gv = new GTreeIndividualVisuallizer((GTreeIndividual) individual, elements);
            gv.show(x, y);
            return gv;
        }
        else if (individual.getClass().equals(GIndividual.class))
        {
            GIndividualVisualizer gv = new GIndividualVisualizer((GIndividual) individual, strips, elements);
            gv.show(x, y);
            return gv;
        }
        else if (individual.getClass() == AIndividual.class)
        {
            AIndividualVisualizer av = new AIndividualVisualizer((AIndividual) individual, strips, elements);
            av.show(x, y);
            return av;
        }
        else if (individual.getClass() == BIndividual.class)
        {
            BIndividualVisualizer bv = new BIndividualVisualizer((BIndividual) individual, strips, elements, forceMinimumRatio, forceBestFit);
            bv.show(x, y);
            return bv;
        }
        else if (individual.getClass() == CIndividual.class)
        {
            CIndividualVisualizer cv = new CIndividualVisualizer((CIndividual) individual, strips, elements, forceMinimumRatio, forceBestFit);
            cv.show(x, y);
            return cv;
        }
        else if (individual.getClass() == PIndividual.class)
        {
            PIndividualVisualizer pv = new PIndividualVisualizer((PIndividual) individual);
            pv.show(x, y);
            return pv;
        }
        else return null;
        //showIndividualWorkSpace(x, y, individual);
    }

    public static JFrame visualizeWorkSpace(AbstractWorkSpace ws, CuttedElement[] cuttedElements)
    {
        PanelWorkSpace pw = new PanelWorkSpace();
        pw.setWorkSpace(ws);
        pw.setCuttedElements(cuttedElements);
        pw.setShowWorkAreas(true);
        JFrame frm = new JFrame();

        frm.getContentPane().add(pw);
        frm.pack();
        //frm.setLocation(x, y);
        frm.setVisible(true);
        return frm;
        //showIndividualWorkSpace(x, y, individual);
    }

    public static Element[] getElements()
    {
        return elements;
    }

    public static void setElements(Element[] elements)
    {
        CommonGui.elements = elements;
    }

    public static Strips getStrips()
    {
        return strips;
    }

    public static void setStrips(Strips strips)
    {
        CommonGui.strips = strips;
    }

    public static void setForceMinimumRatio(float forceMinimumRatio)
    {
        CommonGui.forceMinimumRatio = forceMinimumRatio;
    }

    public static void setForceBestFit(boolean forceBestFit)
    {
        CommonGui.forceBestFit = forceBestFit;
    }


}
