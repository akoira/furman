package by.dak.plastic.process;

import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.entities.OrderItem;
import by.dak.plastic.DSPPlasticDetail;
import by.dak.plastic.swing.DSPPlasticValue;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 24.09.11
 * Time: 15:16
 */
public class CuttingModel2DSPPlasticDetailsProcess extends AProcess<List<DSPPlasticDetail>>
{
    private CuttingModel cuttingModel;
    private DSPPlasticValue plasticValue;
    private OrderItem orderItem;

    public void process()
    {
        if (getResult() == null)
        {
            setResult(new ArrayList<DSPPlasticDetail>());
        }

        List<TextureBoardDefPair> pairs = cuttingModel.getPairs();
        for (TextureBoardDefPair pair : pairs)
        {
            ExpressionParser parser = new SpelExpressionParser();
            EvaluationContext context = new StandardEvaluationContext(pair);
            for (String resultExpression : getResultExpressions())
            {
                Expression expression = parser.parseExpression(resultExpression);
                if (expression.getValue(context, Boolean.class))
                {
                    Strips strips = cuttingModel.getStrips(pair);
                    Strips2DSPPlasticDetailsProcess strips2DSPPlasticDetailesProcess = new Strips2DSPPlasticDetailsProcess();
                    strips2DSPPlasticDetailesProcess.setStrips(strips);
                    strips2DSPPlasticDetailesProcess.setPlasticValue(plasticValue);
                    strips2DSPPlasticDetailesProcess.setOrderItem(getOrderItem());
                    strips2DSPPlasticDetailesProcess.setResult(getResult());
                    strips2DSPPlasticDetailesProcess.process();
                }
            }
        }
    }

    public CuttingModel getCuttingModel()
    {
        return cuttingModel;
    }

    public void setCuttingModel(CuttingModel cuttingModel)
    {
        this.cuttingModel = cuttingModel;
    }

    public OrderItem getOrderItem()
    {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem)
    {
        this.orderItem = orderItem;
    }

    public DSPPlasticValue getPlasticValue()
    {
        return plasticValue;
    }

    public void setPlasticValue(DSPPlasticValue plasticValue)
    {
        this.plasticValue = plasticValue;
    }
}
