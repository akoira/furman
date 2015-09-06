package by.dak.furman.templateimport.swing.nodes;

public class TemplateNodeConverter extends AValueConverter<TemplateNode>
{
    public TemplateNodeConverter()
    {
        setIconKey("icon.template");
    }

    @Override
    public String getString(TemplateNode value)
    {
        return value.getValue().getDescription().getCode();
    }
}
