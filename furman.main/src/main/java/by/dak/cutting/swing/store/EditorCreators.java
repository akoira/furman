package by.dak.cutting.swing.store;

import by.dak.buffer.importer.swing.FileChooserSelector;
import by.dak.buffer.importer.swing.FileChooserSelectorHelpHandler;
import by.dak.cutting.agt.AGTType;
import by.dak.cutting.swing.ItemSelector;
import by.dak.cutting.swing.component.MaskTextField;
import by.dak.cutting.swing.store.helpers.*;
import by.dak.cutting.zfacade.ZProfileColor;
import by.dak.cutting.zfacade.ZProfileType;
import by.dak.design.draw.components.BoardElement;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.ServiceType;
import by.dak.persistence.entities.predefined.Side;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;
import by.dak.report.PriceReportType;
import by.dak.swing.APropertyEditorCreator;
import by.dak.swing.table.NewEditDeleteActions;
import com.toedter.calendar.JDateChooser;
import org.jdesktop.beansbinding.*;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.List;

/**
 * User: akoyro
 * Date: 09.09.2010
 * Time: 13:43:01
 */
public class EditorCreators
{

    public static final HashMap<Class, APropertyEditorCreator> EDITOR_CREATORS = new HashMap<Class, APropertyEditorCreator>();

    static
    {
        EDITOR_CREATORS.put(double.class, new ClassEditorCreator<MaskTextField>(MaskTextField.class, "text"));
        EDITOR_CREATORS.put(Integer.class, new ClassEditorCreator<MaskTextField>(MaskTextField.class, "text"));
        EDITOR_CREATORS.put(Long.class, new ClassEditorCreator<MaskTextField>(MaskTextField.class, "text"));
        EDITOR_CREATORS.put(Double.class, new ClassEditorCreator<MaskTextField>(MaskTextField.class, "text"));
        EDITOR_CREATORS.put(Float.class, new ClassEditorCreator<MaskTextField>(MaskTextField.class, "text"));
        EDITOR_CREATORS.put(Date.class, new ClassEditorCreator<JDateChooser>(JDateChooser.class, "date"));
        EDITOR_CREATORS.put(java.sql.Date.class, new ClassEditorCreator<JDateChooser>(JDateChooser.class, "date"));
        EDITOR_CREATORS.put(String.class, new ClassEditorCreator<JTextField>(JTextField.class, "text"));
        EDITOR_CREATORS.put(TextureEntity.class, new ItemSelectorEditorCreator(Collections.emptyList()));
        EDITOR_CREATORS.put(BoardDef.class, new EntitySelectorEditorCreator(BoardDef.class, BoardDefNEDActions.class));
        EDITOR_CREATORS.put(BorderDefEntity.class, new EntitySelectorEditorCreator(BorderDefEntity.class, BorderDefNEDActions.class));
        EDITOR_CREATORS.put(Provider.class, new EntitySelectorEditorCreator(Provider.class, ProviderNEDActions.class));
        EDITOR_CREATORS.put(Unit.class, new ItemSelectorEditorCreator(Arrays.asList(Unit.values())));
        EDITOR_CREATORS.put(FurnitureType.class, new EntitySelectorEditorCreator(FurnitureType.class, FurnitureTypeNEDActions.class));
        EDITOR_CREATORS.put(ZProfileType.class, new EntitySelectorEditorCreator(ZProfileType.class, FurnitureTypeNEDActions.class));
        EDITOR_CREATORS.put(AGTType.class, new EntitySelectorEditorCreator(AGTType.class, FurnitureTypeNEDActions.class));
        EDITOR_CREATORS.put(FurnitureCode.class, new ItemSelectorEditorCreator(Collections.emptyList(), getNewAction(new FurnitureTypeNEDActions())));
        EDITOR_CREATORS.put(ZProfileColor.class, new ItemSelectorEditorCreator(Collections.emptyList(), getNewAction(new FurnitureTypeNEDActions())));
        EDITOR_CREATORS.put(DepartmentEntity.class, new EntitySelectorEditorCreator(DepartmentEntity.class, DepartmentNEDActions.class));
        EDITOR_CREATORS.put(StoreElementStatus.class, new ItemSelectorEditorCreator(Arrays.asList(StoreElementStatus.values()), null));
        EDITOR_CREATORS.put(by.dak.persistence.entities.predefined.MaterialType.class, new ItemSelectorEditorCreator(Arrays.asList(by.dak.persistence.entities.predefined.MaterialType.parentTypes()), null));
        EDITOR_CREATORS.put(Cutter.class, new EntitySelectorEditorCreator(Cutter.class, null));
        EDITOR_CREATORS.put(Side.class, new ItemSelectorEditorCreator(Arrays.asList(Side.values()), null));
        EDITOR_CREATORS.put(Order.class, new ItemSelectorEditorCreator(Collections.singletonList(Order.NULL_Order), null));
        EDITOR_CREATORS.put(File.class, new FileChooserEditorCreator(null));
        EDITOR_CREATORS.put(Customer.class, new CustomerSelectorEditorCreator(Customer.class, null));
        EDITOR_CREATORS.put(ServiceType.class, new ItemSelectorEditorCreator(Arrays.asList(ServiceType.values()), null));
        EDITOR_CREATORS.put(Boolean.class, new CheckBoxEditorCreator());
        EDITOR_CREATORS.put(boolean.class, new CheckBoxEditorCreator());
        EDITOR_CREATORS.put(Dailysheet.class, new EntitySelectorEditorCreator(Dailysheet.class, null)
        {
            @Override
            public List getList()
            {
                return FacadeContext.getDailysheetFacade().loadAllSortedBy(Dailysheet.PROPERTY_date, false);
            }
        });

        EDITOR_CREATORS.put(OrderStatus.class, new ItemSelectorEditorCreator(Arrays.asList(OrderStatus.values()), null));
        EDITOR_CREATORS.put(PriceReportType.class, new ItemSelectorEditorCreator(Arrays.asList(PriceReportType.values())));
        EDITOR_CREATORS.put(BoardElement.Type.class, new ItemSelectorEditorCreator(Arrays.asList(BoardElement.Type.values())));
        EDITOR_CREATORS.put(BoardElement.Location.class, new ItemSelectorEditorCreator(Arrays.asList(BoardElement.Location.values())));
        EDITOR_CREATORS.put(CutDirection.class, new ItemSelectorEditorCreator(Arrays.asList(CutDirection.values())));

    }

    public static Action getNewAction(NewEditDeleteActions actions)
    {
        return actions != null ? actions.getActionMap().get("newValue") : null;
    }

    public static NewEditDeleteActions createBy(Class<? extends NewEditDeleteActions> actionsClass)
    {
        if (actionsClass == null)
        {
            return null;
        }
        try
        {
            return actionsClass.newInstance();
        }
        catch (Throwable e)
        {
            throw new IllegalArgumentException(e);
        }
    }


    public static interface ListPrivider<E>
    {
        public List<E> getList();
    }

    public static class EntitySelectorEditorCreator extends ItemSelectorEditorCreator
    {
        private Class<? extends PersistenceEntity> entityClass;
        private Class<? extends NewEditDeleteActions> actionsClass;


        public EntitySelectorEditorCreator(Class<? extends PersistenceEntity> entityClass, Class<? extends NewEditDeleteActions> actionsClass)
        {
            super(null, null);
            this.entityClass = entityClass;
            this.actionsClass = actionsClass;
        }

        @Override
        public ItemSelector createEditor(Object value, String property, PropertyDescriptor descriptor, BindingGroup bindingGroup)
        {
            final NewEditDeleteActions actions = createBy(actionsClass);
            final ItemSelector itemSelector = createItemSelectorEditorBy(value, property, bindingGroup, getList(), getNewAction(actions));
            if (actions != null)
            {
                actions.addPropertyChangeListener(new PropertyChangeListener()
                {
                    @Override
                    public void propertyChange(PropertyChangeEvent propertyChangeEvent)
                    {
                        itemSelector.addItem(propertyChangeEvent.getNewValue());
                    }
                });
            }


            return itemSelector;
        }

        @Override
        public List getList()
        {
            List list = FacadeContext.getFacadeBy(entityClass).loadAllSortedBy("name");
            return list;
        }
    }

    public static class FileChooserEditorCreator extends APropertyEditorCreator
    {
        private File file;

        public FileChooserEditorCreator(File file)
        {
            this.file = file;
        }


        @Override
        public Component createEditor(Object value, String property, PropertyDescriptor descriptor, BindingGroup bindingGroup)
        {
            return createFileChooserSelectorEditorBy(value, property, bindingGroup, this.file);
        }

        private FileChooserSelector createFileChooserSelectorEditorBy(Object value, String property, BindingGroup bindingGroup, File file)
        {
            FileChooserSelectorHelpHandler selectorHelpHandler = new FileChooserSelectorHelpHandler(file);
            Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
                    value, BeanProperty.create(property),
                    selectorHelpHandler.getFileChooserSelector(), BeanProperty.create("file"), property);
            binding.setSourceUnreadableValue(null);
            Binding oldBinding = bindingGroup.getBinding(property);
            if (oldBinding != null)
            {
                bindingGroup.removeBinding(oldBinding);
            }
            bindingGroup.addBinding(binding);

            return selectorHelpHandler.getFileChooserSelector();
        }
    }

    public static class ItemSelectorEditorCreator extends APropertyEditorCreator
    {
        private java.util.List list;

        private Action action;

        public ItemSelectorEditorCreator(java.util.List list)
        {
            this(list, null);
        }

        public ItemSelectorEditorCreator(java.util.List list, Action action)
        {
            this.list = list;
            this.action = action;
        }

        @Override
        public Component createEditor(Object value, String property, PropertyDescriptor descriptor, BindingGroup bindingGroup)
        {
            return createItemSelectorEditorBy(value, property, bindingGroup, getList(), getAction());
        }

        public ItemSelector createItemSelectorEditorBy(Object value, String property, BindingGroup bindingGroup, java.util.List list, Action action)
        {
            ItemSelectorHelpHandler itemSelectorHandler = new ItemSelectorHelpHandler(list, action);

            Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
                    value, BeanProperty.create(property),
                    itemSelectorHandler.getItemSelector().getComboBoxItem(), BeanProperty.create("selectedItem"), property);
            binding.setSourceUnreadableValue(null);
            Binding oldBinding = bindingGroup.getBinding(property);
            if (oldBinding != null)
            {
                bindingGroup.removeBinding(oldBinding);
            }
            bindingGroup.addBinding(binding);

            return itemSelectorHandler.getItemSelector();
        }

        /**
         * @return the list
         */
        public java.util.List getList()
        {
            return list;
        }

        public void setList(List list)
        {
            this.list = list;
        }


        /**
         * @return the action
         */
        public Action getAction()
        {
            return action;
        }

        public void setAction(Action action)
        {
            this.action = action;
        }


    }


    public static class ClassEditorCreator<C extends Component> extends APropertyEditorCreator
    {
        private Class<C> editorClass;
        private String componentValueProperty;

        public ClassEditorCreator(Class<C> editorClass, String componentValueProperty)
        {
            this.editorClass = editorClass;
            this.componentValueProperty = componentValueProperty;
        }

        protected C createEditor()
        {
            try
            {
                Constructor<C> constructor = editorClass.getConstructor(null);
                return constructor.newInstance(null);
            }
            catch (Throwable e)
            {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public C createEditor(Object value, String property, PropertyDescriptor descriptor, BindingGroup bindingGroup)
        {
            final C editor = createEditor();
            //todo для MaskTextField в случае Double надо установить паттерн
//            if(editor instanceof MaskTextField)
//            {
//                final String regExp = "\\d*";
//                ((MaskTextField) editor).setMask(regExp);
//            }
            if (editor instanceof JTextComponent)
            {
                editor.addFocusListener(new FocusAdapter()
                {
                    @Override
                    public void focusGained(FocusEvent e)
                    {
                        ((JTextComponent) editor).selectAll();
                    }
                });
            }
            Binding binding = Bindings.createAutoBinding(descriptor.getWriteMethod() == null ? AutoBinding.UpdateStrategy.READ : AutoBinding.UpdateStrategy.READ_WRITE, value, BeanProperty
                    .create(property), editor, BeanProperty.create(componentValueProperty), property);
            binding.setSourceUnreadableValue(null);
            bindingGroup.addBinding(binding);
            return editor;
        }
    }

    public static class CheckBoxEditorCreator extends APropertyEditorCreator
    {
        @Override
        public Component createEditor(Object value, String property, PropertyDescriptor descriptor, BindingGroup bindingGroup)
        {
            JCheckBox checkBox = new JCheckBox();
            Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, value, BeanProperty
                    .create(property), checkBox, BeanProperty.create("selected"), property);
            binding.setSourceUnreadableValue(Boolean.FALSE);
            bindingGroup.addBinding(binding);
            return checkBox;
        }
    }

    public static class CustomerSelectorEditorCreator extends EntitySelectorEditorCreator
    {
        public CustomerSelectorEditorCreator(Class<? extends PersistenceEntity> entityClass, Class<? extends NewEditDeleteActions> actionsClass)
        {
            super(entityClass, actionsClass);
        }

        @Override
        public List getList()
        {
            List list = super.getList();
            list.add(0, Customer.NULL_CUSTOMER);
            return list;
        }
    }


}
