package trekk.server.io;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;
import javax.inject.Named;

import trekk.server.model.Customer;
import trekk.server.model.Navigation;
import trekk.server.repo.CustomerRepo;
import trekk.server.repo.NavigationRepo;

@Named
public class TrekkServiceImpl implements TrekkService {

    private final CustomerRepo customerRepo;
    private final NavigationRepo navigationRepo;

    @Inject
    public TrekkServiceImpl(final CustomerRepo customerRepo,
            final NavigationRepo navigationRepo) {
        this.customerRepo = checkNotNull(customerRepo);
        this.navigationRepo = checkNotNull(navigationRepo);
    }

    @Override
    public Iterable<Customer> findAll() {
        return customerRepo.findAllOrderByLastContact();
    }

    @Override
    public Customer create(Customer customer) {
        customer.setId(null);
        return customerRepo.save(customer);
    }

    @Override
    public Customer findCustomer(Long id) {
        return customerRepo.findOne(id);
    }

    @Override
    public Customer update(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    public void delete(Long id) {
        customerRepo.delete(id);
    }

    @Override
    public Iterable<Navigation> findByCustomerId(Long id) {
        return navigationRepo.findByCustomerIdOrderByStamp(id);
    }
}
