package trekk.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Navigation extends BaseEntity {

    @Column(nullable=false)
    private String pages;

    @ManyToOne(optional=true)
    @JoinColumn(name="customer_id", nullable=true, unique=false)
    private Customer customer;
}
