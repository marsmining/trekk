package trekk.server.io;

import trekk.server.model.Customer;

public interface TrekkService {

    Iterable<Customer> findAll();
}
