package trekk.server.io;

import trekk.server.model.Customer;
import trekk.server.model.Navigation;

public interface TrekkService {

    Iterable<Customer> findAll();

    Customer create(Customer customer);

    Customer findCustomer(Long id);

    Customer update(Customer customer);

    void delete(Long id);

    Iterable<Navigation> findByCustomerId(Long id);
}
