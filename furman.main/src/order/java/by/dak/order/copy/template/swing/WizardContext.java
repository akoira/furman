package by.dak.order.copy.template.swing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: akoyro
 * Date: 11/22/13
 * Time: 8:09 PM
 */
public class WizardContext
{
    private List<IStep> steps;
    private String title;

    private String[] descriptions;
    private String[] names;
    private Map<String, IStep> stepMap;

    public void init()
    {
        descriptions = new String[steps.size()];
        names = new String[steps.size()];
        stepMap = new HashMap<>();
        for (int i = 0; i < steps.size(); i++)
        {
            IStep iStep = steps.get(i);
            descriptions[i] = iStep.getDescription();
            names[i] = iStep.getName();
            stepMap.put(names[i], iStep);
        }
    }

    public IStep getStepBy(String name)
    {
        return stepMap.get(name);
    }


    public String[] getDescriptions()
    {
        return descriptions;
    }

    public String getTitle()
    {
        return title;
    }


    public void setSteps(List<IStep> steps)
    {
        this.steps = steps;
    }

    public String[] getNames()
    {
        return names;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public static WizardContext valueOf(String title, IStep... steps)
    {

        WizardContext result = new WizardContext();
        result.setSteps(Arrays.asList(steps));
        result.setTitle(title);
        result.init();
        return result;
    }

}
