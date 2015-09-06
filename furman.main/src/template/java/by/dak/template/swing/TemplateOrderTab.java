package by.dak.template.swing;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.swing.AEntityEditorTab;
import by.dak.cutting.swing.store.EditorCreators;
import by.dak.order.swing.IOrderStepDelegator;
import by.dak.persistence.FacadeContext;
import by.dak.swing.APropertyEditorCreator;
import by.dak.swing.image.ImageSelectComponent;
import by.dak.swing.image.ImageSelectDelegator;
import by.dak.template.TemplateOrder;

import javax.swing.*;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * User: akoyro
 * Date: 16.03.11
 * Time: 18:15
 */
public class TemplateOrderTab extends AEntityEditorTab<TemplateOrder> implements IOrderStepDelegator<TemplateOrder>
{
    public static final String[] VisibleProperties = new String[]{
            TemplateOrder.PROPERTY_name,
            TemplateOrder.PROPERTY_dialerCost,
            TemplateOrder.PROPERTY_fileUuid,
            TemplateOrder.PROPERTY_description,
    };

    @Override
    public String[] getVisibleProperties()
    {
        return VisibleProperties;
    }


    @Override
    public void setOrder(TemplateOrder order)
    {
        setValue(order);
    }

    @Override
    public TemplateOrder getOrder()
    {
        return getValue();
    }


    @Override
    protected APropertyEditorCreator getEditorCreator(PropertyDescriptor descriptor)
    {
        if (descriptor.getName().equals(TemplateOrder.PROPERTY_description))
        {
            return new EditorCreators.ClassEditorCreator<JTextArea>(JTextArea.class, "text");
        }
        else if (descriptor.getName().equals(TemplateOrder.PROPERTY_fileUuid))
        {
            return createFileUuidEditorCreator();
        }
        return super.getEditorCreator(descriptor);
    }

    private APropertyEditorCreator createFileUuidEditorCreator()
    {
        return new EditorCreators.ClassEditorCreator<ImageSelectComponent>(ImageSelectComponent.class, "value")
        {
            @Override
            protected ImageSelectComponent createEditor()
            {
                ImageSelectComponent imageSelectComponent = super.createEditor();
                imageSelectComponent.setImageSelectDelegator(new ImageSelectDelegator()
                {
                    @Override
                    public String delegate(File selectedFile)
                    {
                        String suuid = getOrder().getFileUuid();
                        if (suuid == null)
                        {
                            UUID uuid = UUID.randomUUID();
                            suuid = uuid.toString();
                        }
                        try
                        {
                            InputStream inputStream = new FileInputStream(selectedFile);
                            FacadeContext.getRepositoryService().store(inputStream, suuid);
                            return suuid;
                        }
                        catch (Throwable e)
                        {
                            CuttingApp.getApplication().getExceptionHandler().handle(TemplateOrderTab.this, e);
                            return null;
                        }
                    }
                });
                return imageSelectComponent;
            }
        };
    }
}
