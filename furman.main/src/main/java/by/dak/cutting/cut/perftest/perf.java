/*
 * perf.java
 *
 * Created on 30. listopad 2006, 15:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.perftest;

/**
 *
 * @author Peca
 */
public class perf {
    
    private static long startTime;
    private static long stopTime;
    
    public static void startMeas(){
        startTime = System.nanoTime();
    }
    
    public static long stopMeas(){
        stopTime = System.nanoTime();
        return stopTime - startTime;
    }
    
    public static void printMeas(String s){
        System.out.print(s);
        float t = (float)(stopTime - startTime)/1000000;
        System.out.print(t);
        System.out.println();
    }
    
    public static void stopMeasAndPrint(String s){
        stopMeas();
        printMeas(s);
    }
}
