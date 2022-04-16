package by.dak.cutting;


import by.dak.persistence.FacadeContext;
import by.dak.persistence.MainFacade;
import by.dak.swing.wizard.DWizardDisplayerImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author admin
 * @version 0.1 27.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class SpringConfiguration {

    private final boolean liquibaseShouldRun;
    private String configPath;
    private ClassPathXmlApplicationContext applicationContext;

    static {
        System.setProperty("WizardDisplayer.default", DWizardDisplayerImpl.class.getName());
    }

    public SpringConfiguration() {
        this(false, "spring-configuration");
    }

    public SpringConfiguration(boolean liquibaseShouldRun) {
        this(liquibaseShouldRun, "spring-configuration");
    }

    public SpringConfiguration(boolean liquibaseShouldRun, String configPath) {
        this.liquibaseShouldRun = liquibaseShouldRun;
        this.configPath = configPath;
        initContext();
    }

    private void initContext() {
        applicationContext = new ClassPathXmlApplicationContext(getPaths());
        FacadeContext.setApplicationContext(applicationContext);
    }

    public String[] getPaths() {
        String[] paths =
                {
                        this.liquibaseShouldRun ? configPath + "/application.with.liquibase.xml" : configPath + "/application.xml"
                };
        ArrayList<String> result = new ArrayList<>(Arrays.asList(paths));

        if (!this.liquibaseShouldRun) {
            by.dak.cutting.agt.spring.SpringConfiguration agt = new by.dak.cutting.agt.spring.SpringConfiguration();
            result.addAll(Arrays.asList(agt.getPaths()));
            by.dak.buffer.spring.SpringConfiguration buffer = new by.dak.buffer.spring.SpringConfiguration();
            result.addAll(Arrays.asList(buffer.getPaths()));
        }

        return result.toArray(new String[]{});
    }

    public ClassPathXmlApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public MainFacade getMainFacade() {
        return applicationContext.getBean(MainFacade.class);
    }

    public static final Supplier<SpringConfiguration> prod = SpringConfiguration::new;
    public static final Supplier<SpringConfiguration> prod_liquibase = () -> new SpringConfiguration(true);
    public static final Supplier<SpringConfiguration> home = () -> new SpringConfiguration(false, "spring-configuration/home");
    public static final Supplier<SpringConfiguration> home_liquibase = () -> new SpringConfiguration(true, "spring-configuration/home");
}
