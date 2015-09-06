package by.dak.cutting.cut.swing;

import by.dak.cutting.cut.guillotine.Strips;

/**
 * @author admin
 * @version 0.1 20.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class NeedStop
{
    //time to cutting one strip in second
    private static float ACCEPT_QUALITY = 0.1f;

    private float time;
    private Strips strips;

    public NeedStop(float time, Strips strips)
    {
        this.time = time;
        this.strips = strips;
    }

    public boolean need()
    {
//        return false;
        return (strips.getWasteRatio() <= ACCEPT_QUALITY ||
                strips.getSegmentsCount() * by.dak.cutting.configuration.Constants.TIME_FOR_STRIP <= time);
    }

}
