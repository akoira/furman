/*
 * SRIndividualVisualizer.java
 *
 * Created on 21. kv�ten 2007, 17:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui;

import by.dak.cutting.cut.base.*;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.SChromosome;
import by.dak.cutting.cut.genetics.SRIndividual;

import javax.swing.*;

/**
 * @author Peca
 */
public class SRIndividualVisualizer extends IndividualVisualizer
{
    WorkSpace ws;
    JFrame frame;
    SRIndividual individual;
    Element[] elements;
    PanelWorkSpace pw;

    /**
     * Creates a new instance of SRIndividualVisualizer
     */
    public SRIndividualVisualizer(SRIndividual individual, WorkArea[] workSheets, Element[] elements)
    {
        this.individual = individual;
        this.elements = elements;
        pw = new PanelWorkSpace();
        ws = new WorkSpace(workSheets);
        ws.minElementSize = Utils.getMinElementSize(elements);
        frame = new JFrame(individual.toString());
        frame.getContentPane().add(pw);
        frame.pack();
        this.update();
    }

    public void update()
    {
        CuttedElement[] cuttedElements = null;
        ws.clear();
        cuttedElements = (new PrimitiveCutter()).cut(elements, ((SChromosome) individual.getChromosome(0)).getElements(), ((by.dak.cutting.cut.genetics.RChromosome) individual.getChromosome(1)).getElements(), ws);
        pw.setWorkSpace(ws);
        pw.setCuttedElements(cuttedElements);

    }

    public JFrame show(int x, int y)
    {
        frame.setLocation(x, y);
        frame.setVisible(true);
        return frame;
    }

    public void setIndividual(Individual individual)
    {
        this.individual = (SRIndividual) individual;
        this.update();
    }

}
