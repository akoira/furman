package by.dak.plastic;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: akoyro
 * Date: 23.09.11
 * Time: 13:07
 */
public class ExpressionsFacadeImpl implements ExpressionsFacade
{
    private static ExpressionsFacade expressionsFacade = new ExpressionsFacadeImpl();
    private ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("by/dak/plastic/expressions.xml");

    public static ExpressionsFacade getExpressionsFacade()
    {
        return expressionsFacade;
    }

    @Override
    public Expressions getExpressionsBy(String name)
    {
        return (Expressions) applicationContext.getBean(name);
    }
}
