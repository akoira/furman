package by.dak.plastic.process;

import by.dak.plastic.ExpressionsFacadeImpl;

/**
 * User: akoyro
 * Date: 24.09.11
 * Time: 15:29
 */
public abstract class AProcess<R>
{

    private R result;
    private String[] resultExpressions;


    public abstract void process();


    public String[] getResultExpressions()
    {
        if (resultExpressions == null)
        {
            resultExpressions = ExpressionsFacadeImpl.getExpressionsFacade().getExpressionsBy(this.getClass().getSimpleName()).getExpressions();
        }
        return resultExpressions;
    }

    public void setResultExpressions(String[] resultExpressions)
    {
        this.resultExpressions = resultExpressions;
    }

    public R getResult()
    {
        return result;
    }

    public void setResult(R result)
    {
        this.result = result;
    }

}
