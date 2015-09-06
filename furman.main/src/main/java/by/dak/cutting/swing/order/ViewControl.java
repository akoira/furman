package by.dak.cutting.swing.order;

import javax.swing.*;


public interface ViewControl<E, Item>
{

    JPanel buildView();

    JComponent getFocusedComponent();

    void setData(E data);

    E getData();

    Item getSelectedItem();
}
