package trekk.server.repo;

import org.springframework.data.repository.CrudRepository;

import trekk.server.model.Navigation;

public interface NavigationRepo extends CrudRepository<Navigation, Long> {

    Iterable<Navigation> findByCustomerId(Long id);

}
