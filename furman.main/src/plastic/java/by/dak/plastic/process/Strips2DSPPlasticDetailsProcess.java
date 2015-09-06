package by.dak.plastic.process;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.OrderItem;
import by.dak.persistence.entities.TextureEntity;
import by.dak.plastic.DSPPlasticDetail;
import by.dak.plastic.ExpressionsFacadeImpl;
import by.dak.plastic.swing.DSPPlasticValue;
import by.dak.plastic.swing.SidePair;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 24.09.11
 * Time: 15:45
 */
public class Strips2DSPPlasticDetailsProcess extends AProcess<List<DSPPlasticDetail>>
{
    private Strips strips;
    private DSPPlasticValue plasticValue;
    private OrderItem orderItem;


    @Override
    public void process()
    {
        if (getResult() == null)
        {
            setResult(new ArrayList<DSPPlasticDetail>());
        }

        Strips2SegmentsProcess strips2SegmentsProcess = new Strips2SegmentsProcess();
        strips2SegmentsProcess.setStrips(strips);
        strips2SegmentsProcess.process();
        List<Segment> segments = strips2SegmentsProcess.getResult();

        for (Segment segment : segments)
        {
            //convert to dsp plastic detail
            Segment2DSPPlasticDetailProcess process = new Segment2DSPPlasticDetailProcess();
            process.setBoardDef(plasticValue.getDspPair().getBoardDef());
            process.setTexture(plasticValue.getDspPair().getTexture());
            convertToPlasticDetail(segment, process);

            //convert to plastic detail
            process = new Segment2DSPPlasticDetailProcess();
            process.setBoardDef((BoardDef) strips.getStripsEntity().getPriceAware());
            process.setTexture((TextureEntity) strips.getStripsEntity().getPriced());
            process.setResultExpressions(ExpressionsFacadeImpl.getExpressionsFacade().getExpressionsBy("Segment2PlasticDetailProcess").getExpressions());
            DSPPlasticDetail first = convertToPlasticDetail(segment, process);

            TextureBoardDefPair firstPair = new TextureBoardDefPair((TextureEntity) strips.getStripsEntity().getPriced(),
                    (BoardDef) strips.getStripsEntity().getPriceAware());

            //convert to second side plastic detail

            SidePair sidePair = plasticValue.getSidePairBy(firstPair);

            if (sidePair.getNeed())
            {
                process = new Segment2DSPPlasticDetailProcess();
                process.setBoardDef(sidePair.getSecond().getBoardDef());
                process.setTexture(sidePair.getSecond().getTexture());
                process.setResultExpressions(ExpressionsFacadeImpl.getExpressionsFacade().getExpressionsBy("Segment2PlasticDetailProcess").getExpressions());
                DSPPlasticDetail second = convertToPlasticDetail(segment, process);
                //этот флаг устанавлевается в false чтобы не торцевать еще раз пластик
                second.setPrimary(Boolean.FALSE);
                FacadeContext.getDSPPlasticDetailFacade().save(second);
            }
        }
    }

    private DSPPlasticDetail convertToPlasticDetail(Segment segment, Segment2DSPPlasticDetailProcess process)
    {
        process.setSegment(segment);
        process.setOrderItem(getOrderItem());
        process.setNumber(getResult().size() + 1);
        process.process();
        getResult().add(process.getResult());
        FacadeContext.getDSPPlasticDetailFacade().save(process.getResult());
        return process.getResult();
    }


    public DSPPlasticValue getPlasticValue()
    {
        return plasticValue;
    }

    public void setPlasticValue(DSPPlasticValue plasticValue)
    {
        this.plasticValue = plasticValue;
    }


    public Strips getStrips()
    {
        return strips;
    }

    public void setStrips(Strips strips)
    {
        this.strips = strips;
    }

    public OrderItem getOrderItem()
    {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem)
    {
        this.orderItem = orderItem;
    }
}
