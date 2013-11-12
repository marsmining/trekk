package trekk.server.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import trekk.common.Views;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Objects;

@Entity
public class Customer extends BaseEntity {

    public static enum Gender {
        m, w
    }

    @Column(nullable=false)
    private String firstName;

    @Column(nullable=false)
    private String lastName;

    @Column(nullable=false)
    private Date birthday;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Gender gender;

    @Column(nullable=false)
    private Date lastContact;

    @Column(nullable=false)
    private Long lifetimeValue;

    @OneToMany(mappedBy="customer")
    private List<Navigation> navigations;

    @JsonView(Views.Public.class)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonView(Views.Public.class)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonView(Views.Public.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @JsonView(Views.Public.class)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @JsonView(Views.Public.class)
    public Date getLastContact() {
        return lastContact;
    }

    public void setLastContact(Date lastContact) {
        this.lastContact = lastContact;
    }

    @JsonView(Views.Public.class)
    public Long getLifetimeValue() {
        return lifetimeValue;
    }

    public void setLifetimeValue(Long lifetimeValue) {
        this.lifetimeValue = lifetimeValue;
    }

    @JsonView(Views.None.class)
    public List<Navigation> getNavigations() {
        return navigations;
    }

    public void setNavigations(List<Navigation> navigations) {
        this.navigations = navigations;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("super", super.toString())
            .add("firstName", firstName)
            .add("lastName", lastName)
            .add("birthday", birthday)
            .add("gender", gender)
            .add("lastContact", lastContact)
            .add("lifetimeValue", lifetimeValue)
            .toString();
    }
}
