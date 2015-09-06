/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui.cuttingApp;

import by.dak.cutting.cut.common.Common;
import by.dak.cutting.cut.gui.CutSettings;
import by.dak.cutting.cut.guillotine.Strips;

/**
 * @author Peca
 */
public abstract class Sawyer
{
    public interface INewSolutionListener
    {
        void newSolutionFound(Strips strips);
    }

    public enum States
    {
        STOPPED, STOPPING, RUNNING, STARTING
    }

    private CutSettings cutSettings;
    private States state = States.STOPPED;
    private INewSolutionListener newSolutionListener;
    private int iterations;
    private boolean forceBestFit = false;
    private float forceMinimumRatio = 0.0f;
    private float forceMigrationRatio = 0.0f;

    private void run()
    {
        assert (state == States.STOPPED);
        state = States.STARTING;
        Thread thread = new Thread()
        {

            @Override
            public void run()
            {
                state = States.RUNNING;
                setIterations(0);
                compute();
                synchronized (Sawyer.this)
                {
                    state = States.STOPPED;
                }
            }

        };
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    protected abstract void compute();

    public synchronized States getState()
    {
        return state;
    }

    public synchronized void stop()
    {
        if (this.state == States.RUNNING)
        {
            this.state = States.STOPPING;
        }
    }

    public synchronized void start()
    {
        if (this.state == States.STOPPED)
        {
            run();
        }
    }

    public CutSettings getCutSettings()
    {
        return cutSettings;
    }

    public void setCutSettings(CutSettings cutSettings)
    {
        this.cutSettings = cutSettings;
    }

    public INewSolutionListener getNewSolutionListener()
    {
        return newSolutionListener;
    }

    public void setNewSolutionListener(INewSolutionListener newSolutionListener)
    {
        this.newSolutionListener = newSolutionListener;
    }

    protected void newSolutionFound(Strips strips)
    {
        assert Common.validateSolution(strips, cutSettings.getElements());
        if (newSolutionListener != null)
        {
            newSolutionListener.newSolutionFound(strips);
        }
    }

    public int getIterations()
    {
        return iterations;
    }

    protected void setIterations(int iterations)
    {
        this.iterations = iterations;
    }

    protected void incrementIterations()
    {
        this.iterations++;
    }

    public boolean isForceBestFit()
    {
        return forceBestFit;
    }

    public float getForceMigrationRatio()
    {
        return forceMigrationRatio;
    }

    public float getForceMinimumRatio()
    {
        return forceMinimumRatio;
    }

    public void setForceBestFit(boolean forceBestFit)
    {
        this.forceBestFit = forceBestFit;
    }

    public void setForceMinimumRatio(float forceMinimumRatio)
    {
        this.forceMinimumRatio = forceMinimumRatio;
    }

    public void setForceMigrationRatio(float forceMigrationRatio)
    {
        this.forceMigrationRatio = forceMigrationRatio;
    }


}
