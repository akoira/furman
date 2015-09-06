package by.dak.plastic.process;

import by.dak.cutting.cut.Segment;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.OrderItem;
import by.dak.persistence.entities.TextureEntity;
import by.dak.plastic.DSPPlasticDetail;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * User: akoyro
 * Date: 20.09.11
 * Time: 18:05
 */

public class Segment2DSPPlasticDetailProcess extends AProcess<DSPPlasticDetail>
{
    private BoardDef boardDef;
    private TextureEntity texture;
    private OrderItem orderItem;
    private Segment segment;
    private int number;

    public void process()
    {
        if (getResult() == null)
        {
            setResult(new DSPPlasticDetail());
        }
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext(this);
        for (String resultExpression : getResultExpressions())
        {
            Expression expression = parser.parseExpression(resultExpression);
            expression.getValue(context);
        }
    }

    public Segment getSegment()
    {
        return segment;
    }

    public void setSegment(Segment segment)
    {
        this.segment = segment;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public BoardDef getBoardDef()
    {
        return boardDef;
    }

    public void setBoardDef(BoardDef boardDef)
    {
        this.boardDef = boardDef;
    }

    public TextureEntity getTexture()
    {
        return texture;
    }

    public void setTexture(TextureEntity texture)
    {
        this.texture = texture;
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
