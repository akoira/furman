package by.dak.cutting.swing.struct.tree;

import by.dak.cutting.swing.tree.EntityNode;
import by.dak.persistence.entities.Customer;

public class CustomerNode extends EntityNode<Customer> {
    public CustomerNode() {
        super(Customer.class);
        setListUpdater(new CustomerListUpdater());
    }
}
