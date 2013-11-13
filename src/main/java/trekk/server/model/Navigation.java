package trekk.server.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Navigation extends BaseEntity {

    @Column(nullable=false)
    private String page;

    @Column(nullable=false)
    private Date stamp;

    @ManyToOne(optional=true)
    @JoinColumn(name="customer_id", nullable=true, unique=false)
    private Customer customer;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    @JsonIgnore
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
