package trekk.server.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import trekk.server.model.Navigation;

public interface NavigationRepo extends CrudRepository<Navigation, Long> {

    @Query("from Navigation n where n.customer.id = :id order by stamp")
    Iterable<Navigation> findByCustomerIdOrderByStamp(@Param("id") Long id);
}
