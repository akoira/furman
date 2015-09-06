/*
 * CuttingTest.java
 *
 * Created on 30. listopad 2006, 14:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.test;

import by.dak.cutting.cut.base.*;
import by.dak.cutting.cut.gui.CommonGui;
import by.dak.cutting.cut.perftest.perf;

import javax.swing.*;

/**
 * @author Peca
 */
public class CuttingTest
{

    public static void main(String args[])
    {
        Element[] elements;
        CuttedElement[] cuttedElements;
        WorkArea[] workSheets;
        AbstractWorkSpace ws, ws2;
        PrimitiveCutter cutter;
        int elementsCount = 40;

        elements = Utils.generateElements(elementsCount, 10, 100, 1, 1283);


        workSheets = new WorkArea[1];
        workSheets[0] = new WorkArea(800, 200);
        ws = new WorkSpace(workSheets);
        ws2 = new WorkSpace_O1(workSheets);
        cutter = new PrimitiveCutter();


        //ws.cutMethod = 1;        
        perf.startMeas();
        for (int i = 0; i < 1000; i++)
        {
            ws.clear();
            cutter.cut(elements, ws);
        }
        perf.stopMeasAndPrint("");

        //ws.cutMethod = 0;        
        //ws2.minElementSize = ReportUtils.getMinElementSize(elements);
        ws2.minMinElementSize = Utils.getMinMinElementSize(elements);
        ws2.minMaxElementSize = Utils.getMinMaxElementSize(elements);
        perf.startMeas();
        for (int i = 0; i < 1000; i++)
        {
            ws2.clear();
            cutter.cutQuick(elements, ws2);
        }
        perf.stopMeasAndPrint("");

        /*QuickList ql = new QuickList();
        ql.add(new Integer(0));
        ql.add(new Integer(1));
        ql.add(new Integer(2));
        ql.add(new Integer(3));
        ql.add(new Integer(4));
        ql.add(new Integer(5));
        ql.set(0, null);
        ql.set(1, null);
        ql.set(2, null);
        ql.set(3, null);
        ql.set(4, null);
        ql.set(5, null);
        ql.pack();
        for(Object o : ql){
            System.out.println(o);
        }*/

        ws.clear();
        cuttedElements = cutter.cut(elements, ws);
        JFrame frm = CommonGui.visualizeWorkSpace(ws, cuttedElements);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ws2.clear();
        cuttedElements = cutter.cut(elements, ws2);
        frm = CommonGui.visualizeWorkSpace(ws2, cuttedElements);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);/**/
    }

}
