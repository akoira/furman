/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.measurement.utils;

/**
 *
 * @author Peca
 */
public class Watch {
    private long startTime;
    private long endTime;
    private long totalTime;
    private boolean stopped = true;
    
    public Watch(){
        
    }
    
    public void reset(){
        startTime = 0;
        endTime = 0;
        stopped = true;
        totalTime = 0;
    }
    
    public void start(){
        startTime = System.nanoTime();
        stopped = false;
    }
    
    public void stop(){
        endTime = System.nanoTime();
        totalTime += endTime - startTime;
        stopped = true;
    }
    
    public long getDuration(){
        if(stopped){
            return endTime - startTime;
        }
        return  System.nanoTime() - startTime;
    }
    
    public float getDurationInSeconds(){
        return getDuration() * 1e-9f;
    }
    
    public float getDurationInMinutes(){
        return getDuration() / 60e9f;
    }
    
    public float getTotalTimeInSeconds(){
        return totalTime * 1e-9f;
    }
}
