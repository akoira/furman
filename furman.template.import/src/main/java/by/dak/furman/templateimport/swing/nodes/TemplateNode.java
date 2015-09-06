package by.dak.furman.templateimport.swing.nodes;

import by.dak.furman.templateimport.Property;
import by.dak.furman.templateimport.ValueConverter;
import by.dak.furman.templateimport.values.Template;

@ValueConverter(converter = TemplateNodeConverter.class)
public class TemplateNode extends AValueNode<Template>
{

    public TemplateNode()
    {
        setProperties(Property.valueOf("description.description", false), SELECT_PROPERTY);
    }
}
