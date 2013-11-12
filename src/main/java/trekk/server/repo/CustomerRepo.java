package trekk.server.repo;

import org.springframework.data.repository.CrudRepository;

import trekk.server.model.Customer;

public interface CustomerRepo extends CrudRepository<Customer, Long> {

}
