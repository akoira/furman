package by.dak.cutting;


import by.dak.persistence.FacadeContext;
import by.dak.persistence.MainFacade;
import by.dak.swing.wizard.DWizardDisplayerImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author admin
 * @version 0.1 27.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class SpringConfiguration
{

    private ClassPathXmlApplicationContext applicationContext;

    static
    {
        System.setProperty("WizardDisplayer.default", DWizardDisplayerImpl.class.getName());
    }


    public SpringConfiguration()
    {
        initContext();
    }

    private void initContext()
    {
        applicationContext = new ClassPathXmlApplicationContext(getPaths());
        FacadeContext.setApplicationContext(applicationContext);
    }

    public String[] getPaths()
    {
        ArrayList<String> result = new ArrayList<String>();
        String[] paths =
                {
                        "spring-configuration/application.xml",
                };
        result.addAll(Arrays.asList(paths));

        by.dak.cutting.agt.spring.SpringConfiguration agt = new by.dak.cutting.agt.spring.SpringConfiguration();
        result.addAll(Arrays.asList(agt.getPaths()));
        by.dak.buffer.spring.SpringConfiguration buffer = new by.dak.buffer.spring.SpringConfiguration();
        result.addAll(Arrays.asList(buffer.getPaths()));
        return result.toArray(new String[]{});
    }

    public ClassPathXmlApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    public MainFacade getMainFacade()
    {
        return applicationContext.getBean(MainFacade.class);
    }
}
