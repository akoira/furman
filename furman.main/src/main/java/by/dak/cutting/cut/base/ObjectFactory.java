/*
 * ObjectFactory.java
 *
 * Created on 1. prosinec 2006, 17:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.base;

/**
 * @author Peca
 */
public class ObjectFactory
{

    public static WorkSpace createWorkSpaceInstance()
    {
        WorkSpace ws = new WorkSpace();
        return ws;
    }

}
