/*
 * WorkSpace_O1.java
 *
 * Created on 1. prosinec 2006, 17:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.base;

/**
 * @author Peca
 */
public class WorkSpace_O1 extends AbstractWorkSpace
{


    public QuickList<WorkArea_O1> workAreas;
    private WorkArea[] workSheets;
    // public int minElementSize = 0;
    private int mostRightCut = 0;


    /**
     * Creates a new instance of WorkSpace
     */
    protected WorkSpace_O1()
    {
        workAreas = new QuickList<WorkArea_O1>();
    }

    public WorkSpace_O1(WorkArea[] workSheets)
    {
        this();
        this.workSheets = workSheets;
        clear();
    }

    public Dimension getPlateSize()
    {
        return workSheets[0];
    }
   
/*    public void setPlateSize(Dimension plateSize){
        this.plateSize = plateSize;
        clear();
    }    */

    public void clear()
    {
        workAreas.clear();
        mostRightCut = 0;
        for (int i = 0; i < workSheets.length; i++)
        {
            workAreas.add(new WorkArea_O1(workSheets[i]));
        }
        //workAreas.add(new WorkArea(plateSize.width, plateSize.height));        
    }


    public int getRightFreeLength()
    {
        return mostRightCut;
    }

    public int getRightEmptyArea(WorkArea workSheet)
    {
        int r = 0;
        for (WorkArea wa : workAreas)
        {
            if (!wa.intersect(workSheet)) continue;   //nepatri to do tohoto sheetu
            if (wa.getX2() != workSheet.getX2()) continue;     //neni to workarea co se dotyka konce
            if (wa.getY() != workSheet.getY()) continue;
            if (wa.getY2() != workSheet.getY2()) continue;
            r = wa.getArea();
            break;
        }
        return r;
    }


    public int getRightEmptyArea()
    {
        int r = 0;
        for (WorkArea ws : workSheets)
        {
            r += getRightEmptyArea(ws);
        }
        return r;
    }

    /**
     * Plocha workarea nedotykajicich se konce desky *
     */
    public int getLeftEmptyArea(WorkArea workSheet)
    {
        int area = 0;
        for (WorkArea wa : workAreas)
        {
            if (!wa.intersect(workSheet)) continue;   //nepatri to do tohoto sheetu
            if (wa.getX2() == workSheet.getX2()) continue;      //dotyka se to konce desky
            area += wa.getArea();
        }
        return area;
    }

    public int getLeftEmptyArea()
    {
        int area = 0;
        for (WorkArea ws : workSheets)
        {
            area += getLeftEmptyArea(ws);
        }
        return area;
    }

    public int getTotalArea()
    {
        int area = 0;
        for (WorkArea ws : workSheets)
        {
            area += ws.getArea();
        }
        return area;
    }


    public void cut(Rect rct)
    {
        if (mostRightCut < rct.getX2()) mostRightCut = rct.getX2();
        int n = workAreas.size();
        for (int i = 0; i < n; i++)
        {                     //projdeme vsechny pracovni plochy
            WorkArea_O1 wa = workAreas.get(i);
            wa.isNew = false;                             //stavajici plosky oznacime ze nejsou nove
            Rect[] ra = wa.split(rct);                    //a vsechny je rozdelime (pokud to jde)  
            if (ra != null)
            {                               //slo to
                //workAreas.remove(wa);                   //plochu kterou jsme rezali odstranime  
                workAreas.set(i, null);                   //plochu kterou jsme rezali odstranime ze seznamu

                for (Rect r : ra)
                {                         //vsechny nove casti pridame do seznamu
                    //if((r.width >= minElementSize)&&(r.height >= minElementSize)){
                    if ((Math.min(r.getWidth(), r.getHeight()) >= minMinElementSize) &&
                            (Math.max(r.getWidth(), r.getHeight()) >= minMaxElementSize))
                    {
                        //wanew.add(new WorkArea(r));
                        WorkArea_O1 wan = new WorkArea_O1(r);
                        wan.isNew = true;
                        workAreas.add(wan);
                    }
                }
            }

        }
        workAreas.pack();

        //workAreas.addAll(wanew);
        int i = 0;
        while (i < workAreas.size())
        {          //projdeme vsechny pracovni plochy
            WorkArea_O1 wa = workAreas.get(i);
            if (!wa.isNew)
            {    //ploska neni nova tak to preskocime
                i++;
                continue;
            }
            if (isWorkAreaOverlapped(wa))
                workAreas.remove(i);     //pokud je nova ploska cela prekryta jinou ploskou, tak ji odstranime ze seznamu
            else i++;
        }
        //workAreas.pack();
    }

    public WorkArea findTopLeftWorkArea(Dimension elementSize)
    {
        long min = Long.MAX_VALUE;
        WorkArea_O1 best = null;
        for (int i = 0; i < workAreas.size(); i++)
        {
            WorkArea_O1 wa = workAreas.get(i);
            long dist = wa.getY() + wa.getX() * getPlateSize().getHeight();
            if ((min > dist) && (wa.getWidth() >= elementSize.getWidth()) && (wa.getHeight() >= elementSize.getHeight()))
            {
                best = wa;
                min = dist;
            }
        }
        return best;
    }

    public WorkArea[] getWorkAreas()
    {
        WorkArea_O1[] was = new WorkArea_O1[workAreas.size()];
        for (int i = 0; i < workAreas.size(); i++)
        {
            was[i] = workAreas.get(i);
        }
        return was;
    }


    /**
     * zjistuje jestli je pracovni prostor wa cely prekryt jinym pracovnim prostorem
     */
    protected boolean isWorkAreaOverlapped(WorkArea wa)
    {
        for (int i = 0; i < workAreas.size(); i++)
        {
            WorkArea w = workAreas.get(i);
            if (w == wa) continue;
            if (w.contains(wa)) return true;
        }
        return false;
    }

    public int getFreeArea()
    {
        int area = 0;
        for (WorkArea wa : workAreas)
        {
            area += wa.getArea();
        }
        return area;
    }


}
