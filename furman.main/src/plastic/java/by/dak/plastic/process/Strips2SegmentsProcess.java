package by.dak.plastic.process;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 24.09.11
 * Time: 16:17
 */
public class Strips2SegmentsProcess extends AProcess<List<Segment>>
{
    private Strips strips;

    @Override
    public void process()
    {
        if (getResult() == null)
        {
            setResult(new ArrayList<Segment>());
        }
        List<Segment> segments = strips.getItems();
        for (Segment gray : segments)
        {
            List<Segment> redSegments = gray.getItems();
            for (Segment red : redSegments)
            {
                if (red.getElements().length > 0)
                {
                    getResult().add(red);
                }
            }
        }
    }

    public Strips getStrips()
    {
        return strips;
    }

    public void setStrips(Strips strips)
    {
        this.strips = strips;
    }
}
