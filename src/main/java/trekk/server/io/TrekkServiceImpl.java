package trekk.server.io;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;
import javax.inject.Named;

import trekk.server.model.Customer;
import trekk.server.repo.CustomerRepo;

@Named
public class TrekkServiceImpl implements TrekkService {

    private final CustomerRepo customerRepo;

    @Inject
    public TrekkServiceImpl(final CustomerRepo customerRepo) {
        this.customerRepo = checkNotNull(customerRepo);
    }

    @Override
    public Iterable<Customer> findAll() {
        return customerRepo.findAll();
    }
}
