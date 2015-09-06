package by.dak.cutting.linear.swing;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.cut.gui.cuttingApp.CSawyer;
import by.dak.cutting.cut.gui.cuttingApp.Sawyer;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.SegmentType;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.cut.measurement.utils.Watch;
import by.dak.cutting.cut.swing.NeedStop;
import by.dak.cutting.linear.LinearCuttingModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 16.04.11
 * Time: 13:35
 * To change this template use File | Settings | File Templates.
 */
public class LinearCutterController
{
    private LinearCuttingModel cuttingModel = null;
    private LinearCutterPanel linearCutterPanel = null;

    private StripsChangedListener stripsChangedListener = new StripsChangedListener();
    private StopStartHandler stopStartHandler = new StopStartHandler();

    public LinearCutterPanel getLinearCutterPanel()
    {
        return linearCutterPanel;
    }

    public void setLinearCutterPanel(LinearCutterPanel linearCutterPanel)
    {
        this.linearCutterPanel = linearCutterPanel;
    }

    public LinearCuttingModel getCuttingModel()
    {
        return cuttingModel;
    }

    public void setCuttingModel(LinearCuttingModel cuttingModel)
    {
        this.cuttingModel = cuttingModel;
    }

    public StripsChangedListener getStripsChangedListener()
    {
        return stripsChangedListener;
    }

    public StopStartHandler getStopStartHandler()
    {
        return stopStartHandler;
    }


    private class StripsChangedListener implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            if (getCuttingModel() != null)
            {
                Strips strips = getCuttingModel().getStrips(getLinearCutterPanel().getPair());
                if (strips != null)
                {
                    getLinearCutterPanel().getLinearSegmentPanel().setStrips(strips);
                    getLinearCutterPanel().updateBars(strips);
                }
            }
        }

    }

    public class StopStartHandler
    {
        private Sawyer sawyer = null;
        private java.util.Timer timer = new java.util.Timer();
        private TimerTask cuttingTimer;
        private Watch watch = new Watch();

        private Sawyer initSawyer()
        {
            Sawyer sawyer = new CSawyer();
            sawyer.setForceBestFit(true);
            sawyer.setForceMinimumRatio(0.2f);
            sawyer.setForceMigrationRatio(0.5f);

            sawyer.setNewSolutionListener(new Sawyer.INewSolutionListener()
            {

                public void newSolutionFound(final Strips strips)
                {
                    Runnable runnable = new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            sortSegments(strips);
                            getCuttingModel().putStrips(getLinearCutterPanel().getPair(), strips);
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                }
            });

            sawyer.setCutSettings(getCuttingModel().getCutSettings(getLinearCutterPanel().getPair()));
            return sawyer;
        }

        public void stop()
        {
            if (sawyer != null)
            {
                sawyer.stop();

                SwingWorker swingWorker = new SwingWorker()
                {
                    @Override
                    protected Object doInBackground() throws Exception
                    {
                        while (sawyer.getState() != Sawyer.States.STOPPED)
                        {
                            try
                            {
                                Thread.sleep(10);
                            }
                            catch (InterruptedException ex)
                            {
                                ex.printStackTrace();
                                break;
                            }
                        }
                        return null;
                    }
                };

                DialogShowers.showWaitDialog(swingWorker, getLinearCutterPanel());

                watch.stop();
                cuttingTimer.cancel();
            }
            getLinearCutterPanel().setState(LinearCutterPanel.State.STOPED);
        }

        public void generate()
        {
            sawyer = initSawyer();
            if (sawyer == null)
                return;


            sawyer.start();
            watch.reset();
            watch.start();
            cuttingTimer = new TimerTask()
            {
                @Override
                public void run()
                {
                    Runnable runnable = new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            Strips strips = getCuttingModel().getStrips(getLinearCutterPanel().getPair());
                            if (strips != null)
                            {
                                getLinearCutterPanel().updateBarProgress((int) watch.getDurationInSeconds(), (int) (strips.getSegmentsCount() * by.dak.cutting.configuration.Constants.TIME_FOR_STRIP));
                                NeedStop needStop = new NeedStop(watch.getDurationInSeconds(), strips);
                                if (needStop.need() &&
                                        sawyer.getState() != Sawyer.States.STOPPED &&
                                        sawyer.getState() != Sawyer.States.STOPPING
                                        )
                                {
                                    stop();
                                }
                            }
                        }
                    };
                    SwingUtilities.invokeLater(runnable);
                }
            };
            getLinearCutterPanel().setState(LinearCutterPanel.State.STARTED);
            timer.schedule(cuttingTimer, 0, 1000);
        }
    }


    private void sortSegments(Strips strips)
    {
        Collections.sort(strips.getItems(), new SegmentComparator());
    }

    /**
     * сравнивает свободную область красного сегмента(область, в которой лежат все палки (сегменты) после раскроя)
     */
    private class SegmentComparator implements Comparator<Segment>
    {
        @Override
        public int compare(Segment s1, Segment s2)
        {
            Segment segment1 = findRedSegment(s1);
            Segment segment2 = findRedSegment(s2);
            if (segment1 == null || segment2 == null)
            {
                return 0;
            }
            int freeLength1 = segment1.getFreeLength();
            int freeLength2 = segment2.getFreeLength();

            return freeLength1 < freeLength2 ? -1 : 1;
        }

        private Segment findRedSegment(Segment segment)
        {
            for (Segment s : segment.getItems())
            {
                if (s.getSegmentType().equals(SegmentType.red))
                {
                    return s;
                }
                else
                {
                    findRedSegment(s);
                }
            }
            return null;
        }

    }

}
