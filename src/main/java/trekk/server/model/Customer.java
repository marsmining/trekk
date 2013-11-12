package trekk.server.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Customer extends BaseEntity {

    @OneToMany(mappedBy="customer")
    private List<Navigation> navigations;
}
