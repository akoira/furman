/*
 * SWorkSpace.java
 *
 * Created on 1. listopad 2006, 12:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.base;

/**
 *
 * @author phorny
 */
public class SWorkSpace extends WorkSpace{
    
    /** Creates a new instance of SWorkSpace */
    public SWorkSpace(WorkArea[] workSheets) {
        super(workSheets);
    }
    
    public void cut(Rect rct){ 
        super.cut(rct);
        int i = 0;
        while(i < workAreas.size()){          //projdeme vsechny pracovni plochy
            WorkArea wa = workAreas.get(i);
            if(wa.getX() < rct.getX()){
                wa.setX(rct.getX());
                if(super.isWorkAreaOverlapped(wa)){
                    workAreas.remove(wa);
                    continue;
                }
            }
            if((wa.getX2() < rct.getX()) || wa.getWidth() <= 0)workAreas.remove(wa);     //pokud je ploska vic vlevo nez je rezany objekt tak ji odebereme
            else i++;
        }

    }
}
