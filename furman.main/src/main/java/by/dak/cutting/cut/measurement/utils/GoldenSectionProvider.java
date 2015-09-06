/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.measurement.utils;

/**
 *
 * @author Peca
 */
public class GoldenSectionProvider {
    private static final double GOLDEN_NUMBER = 0.6180339887498948482045868343656;
    
    double actualNumber;
    double minValue;
    double maxValue;
    
    public GoldenSectionProvider(){
        this(0.0, 1.0);
    }
    
    public GoldenSectionProvider(double minValue, double maxValue){
        this.minValue = minValue;
        this.maxValue = maxValue;
        reset();
    }
    
    public void reset(){
        actualNumber = 0;
    }
    
    private double normalize(double value){
        return value*(maxValue - minValue)+minValue;
    }
    
    public double nextValue(){
        actualNumber += GOLDEN_NUMBER;
        if(actualNumber > 1.0){
            actualNumber -= 1.0;
        }
        return normalize(actualNumber);
    }
    
    public int nextIntValue(){
        return (int) Math.round(nextValue());
    }
}
