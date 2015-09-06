package by.dak.cutting.swing.struct.tree;

import by.dak.cutting.swing.tree.EntityNode;
import by.dak.persistence.entities.*;
import by.dak.swing.tree.ARootNode;

/**
 * User: akoyro
 * Date: 05.04.11
 * Time: 20:45
 */
public class RootNode extends ARootNode
{
    @Override
    protected void initChildren()
    {
        EntityNode<Provider> providerNode = new EntityNode<Provider>(Provider.class);
        add(providerNode);

        EntityNode<Manufacturer> manufacturerNode = new EntityNode<Manufacturer>(Manufacturer.class);
        add(manufacturerNode);

        EntityNode<Employee> employeeNode = new EntityNode<Employee>(Employee.class);
        add(employeeNode);

        EntityNode<DepartmentEntity> departmentNode = new EntityNode<DepartmentEntity>(DepartmentEntity.class);
        add(departmentNode);

        EntityNode<Customer> customerNode = new EntityNode<Customer>(Customer.class);
        add(customerNode);

        EntityNode<Cutter> cutterNode = new EntityNode<Cutter>(Cutter.class);
        add(cutterNode);
    }
}
