package by.dak.cutting.agt.spring;

/**
 * User: akoyro
 * Date: 05.09.2010
 * Time: 14:39:48
 */
public class SpringConfiguration
{
    public String[] getPaths()
    {
        String[] paths =
                {
                        "by/dak/cutting/agt/spring/dao-context.xml",
                        "by/dak/cutting/agt/spring/service-context.xml",
                };
        return paths;
    }

}
