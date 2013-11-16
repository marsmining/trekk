package trekk.server.io;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import trekk.server.model.Customer;
import trekk.server.model.Navigation;
import trekk.server.repo.CustomerRepo;
import trekk.server.repo.NavigationRepo;

@Named
@Transactional
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
        Customer found = customerRepo.findOne(customer.getId());
        if (found == null) throw new IllegalArgumentException("id not found");
        found.setFirstName(customer.getFirstName());
        found.setLastName(customer.getLastName());
        found.setGender(customer.getGender());
        found.setBirthday(customer.getBirthday());
        found.setLifetimeValue(customer.getLifetimeValue());
        Customer created = customerRepo.save(found);
        created.getNavigations().size();
        return created;
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
