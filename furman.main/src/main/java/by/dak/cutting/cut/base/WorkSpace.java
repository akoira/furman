/*
 * WorkSpace.java
 *
 * Created on 25. jen 2006, 21:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.base;

import java.util.ArrayList;

/**
 * @author Peca
 */
public class WorkSpace extends AbstractWorkSpace
{


    //private Dimension plateSize;       //rozmery desky k narezani
    //public QuickList<WorkArea> workAreas;
    public ArrayList<WorkArea> workAreas;
    private WorkArea[] workSheets;
    //    public int minElementWidth = 0;
//    public int minElementHeight = 0;    
    // public int minElementSize = 0;
    private int mostRightCut = 0;
    //  public ArrayList<Rect> boxes;

    /**
     * Creates a new instance of WorkSpace
     */
    protected WorkSpace()
    {
        workAreas = new ArrayList<WorkArea>();
    }

    public WorkSpace(WorkArea[] workSheets)
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
            workAreas.add(workSheets[i]);
        }
        //workAreas.add(new WorkArea(plateSize.width, plateSize.height));        
    }

    public WorkArea[] getWorkAreas()
    {
        WorkArea[] was = new WorkArea[workAreas.size()];
        for (int i = 0; i < workAreas.size(); i++)
        {
            was[i] = workAreas.get(i);
        }
        return was;
    }

    public WorkArea findTopLeftWorkArea(Dimension elementSize)
    {
        long min = Long.MAX_VALUE;
        WorkArea best = null;
        for (int i = 0; i < workAreas.size(); i++)
        {
            WorkArea wa = workAreas.get(i);
            long dist = wa.getY() + wa.getX() * getPlateSize().getHeight();
            if ((min > dist) && (wa.getWidth() >= elementSize.getWidth()) && (wa.getHeight() >= elementSize.getHeight()))
            {
                best = wa;
                min = dist;
            }
        }
        return best;
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
        ArrayList<WorkArea> wanew = new ArrayList<WorkArea>();  //seznam nove vytvorenych pracovnich ploch
        int i = 0;
        if (mostRightCut < rct.getX2()) mostRightCut = rct.getX2();
        while (i < workAreas.size())
        {          //projdeme vsechny pracovni plochy
            WorkArea wa = workAreas.get(i);
            Rect[] ra = wa.split(rct);                    //a vsechny je rozdelime (pokud to jde)  
            if (ra != null)
            {                               //slo to
                workAreas.remove(wa);                     //plochu kterou jsme rezali odstranime  
                for (Rect r : ra)
                {                         //vsechny nove casti pridame do seznamu novych
                    if ((r.getWidth() >= minElementSize) && (r.getHeight() >= minElementSize))
                        wanew.add(new WorkArea(r));
                }
            }
            else i++;
        }
        i = 0;
        workAreas.addAll(wanew);
        while (i < workAreas.size())
        {          //projdeme vsechny pracovni plochy
            WorkArea wa = workAreas.get(i);
            if (isWorkAreaOverlapped(wa))
                workAreas.remove(wa);     //pokud je nova ploska cela prekryta jinou ploskou, tak ji odstranime ze seznamu
            else i++;
        }
    }

    /*public void cut_O1(Rect rct){
        //ArrayList<WorkArea> wanew = new ArrayList<WorkArea>();  //seznam nove vytvorenych pracovnich ploch

        if(mostRightCut < rct.x2) mostRightCut = rct.x2;
        int n = workAreas.size();
        for(int i=0; i<n; i++){                     //projdeme vsechny pracovni plochy
            WorkArea wa =  workAreas.get(i);
            Rect[] ra = wa.split(rct);                    //a vsechny je rozdelime (pokud to jde)  
            if(ra != null){                               //slo to
                workAreas.remove(i);                     //plochu kterou jsme rezali odstranime  
                for(Rect r : ra){                         //vsechny nove casti pridame do seznamu novych
                  if((r.width >= minElementSize)&&(r.height >= minElementSize))
                      wanew.add(new WorkArea(r));
               }
            }
            else i++;
        }
        i = 0;
        workAreas.addAll(wanew);
        while(i < workAreas.size()){          //projdeme vsechny pracovni plochy
            WorkArea wa = workAreas.get(i);
            if(isWorkAreaOverlapped(wa)) workAreas.remove(i);     //pokud je nova ploska cela prekryta jinou ploskou, tak ji odstranime ze seznamu
            else i++;
        }
    }*/

    /*  public void by.dak.cutting.cut(Rect rct, WorkArea wa, boolean recursive){
        Rect[] ra = wa.split(rct);            //narezu pracovni plochu
        if(ra == null)return;       //nic se nenarezalo

        //odstranime wa ze seznamu
        workAreas.remove(wa);        
        
        //u vsem sousedum rekneme ze uz s nima tato plocha nesousedi
        for(WorkArea wan : wa.neighbours){      
            wan.neighbours.remove(this);
        }
        //podivame se jestli nove plosky nejsou obsazeny v nejakem sousedovi
        int newCount = 0;
        boolean isInvalid;
        for(int i=0; i<ra.length; i++){
            isInvalid = false;
            for(WorkArea wan : wa.neighbours){      
                if(wan.contains(ra[i])){        //nova ploska je uz obsazena v jiny, tak ji oznacime jako null
                   ra[i] = null;
                   isInvalid = true;
                   break;
                }
            }
            if(!isInvalid)newCount++;
        }

        //nove vytovrene plosky pridame do seznamu plosek
        WorkArea[] newWas = new WorkArea[newCount];
        int index = 0;
        for(int i=0; i<ra.length; i++){
            if(ra[i] == null)continue;
            newWas[index] = new WorkArea(ra[i]);
            workAreas.add(newWas[index]);
            index++;
        }
        
        //podivame se jak nove plosky sousedi samy s sebou
        for(int i=0; i<newWas.length; i++){
            for(int j=i+1; j<newWas.length; j++){
                if(newWas[i].intersect(newWas[j])){
                    newWas[i].neighbours.add(newWas[j]);
                    newWas[j].neighbours.add(newWas[i]);
                }
            }
        }
        
        //nove plosky co sousedi s tema stavajicima oznacime jako sousedi
        for(int i=0; i<newWas.length; i++){
            for(WorkArea wan : wa.neighbours){      
                if(wan.intersect(newWas[i])){        //nova ploska se dotyka jine plosky
                    newWas[i].neighbours.add(wan);
                    wan.neighbours.add(newWas[i]);
                }
            }
        }
        
       // narezeme i plosky co sousedi s wa
        if(recursive){
            WorkArea[] was = new WorkArea[wa.neighbours.size()];
            for(int i=0; i<wa.neighbours.size(); i++){      
                was[i] = wa.neighbours.get(i);
            }
            for(int i=0; i<was.length; i++){      
                by.dak.cutting.cut(rct, was[i], false);   //ale uz musime vypnout rekurzi
            }                
        }
        
    }*/


    /* private void removeTooSmalWorkAreas(){
        while(i < workAreas.size()){          //projdeme vsechny pracovni plochy
            WorkArea wa = workAreas.get(i);
            if(isWorkAreaOverlapped(wa)) workAreas.remove(wa);     //pokud je nova ploska cela prekryta jinou ploskou, tak ji odstranime ze seznamu
            else i++;
        }        
    }*/


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
