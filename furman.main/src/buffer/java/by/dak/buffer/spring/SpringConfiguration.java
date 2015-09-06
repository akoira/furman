package by.dak.buffer.spring;

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
                        "by/dak/buffer/spring/dao-context.xml",
                        "by/dak/buffer/spring/service-context.xml",
                };
        return paths;
    }

}
