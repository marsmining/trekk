package trekk.server.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import trekk.server.model.Customer;

public interface CustomerRepo extends CrudRepository<Customer, Long> {

    @Query("from Customer c order by c.lastContact")
    Iterable<Customer> findAllOrderByLastContact();
}
