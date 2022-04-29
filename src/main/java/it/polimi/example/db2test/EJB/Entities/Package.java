package it.polimi.example.db2test.EJB.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;


@Entity
@Table(name = "package", schema = "telco_db")
public class Package implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id_package;
    private String name;

    @ManyToMany
    @JoinTable(name="package_service", schema = "telco_db", joinColumns = @JoinColumn (name = "Package_ID"), inverseJoinColumns = @JoinColumn (name = "Service_id"))
    private Collection<Service> services;


    @ManyToMany
    @JoinTable(name="pvp", schema = "telco_db", joinColumns = @JoinColumn (name = "package_ID"), inverseJoinColumns = @JoinColumn (name = "validity_period_id"))
    private Collection<ValidityPeriod> validityPeriods;

    @ManyToMany
    @JoinTable(name="pop", schema = "telco_db", joinColumns = @JoinColumn (name = "Package_ID"), inverseJoinColumns = @JoinColumn (name = "OP_name"))
    private Collection<OptionalProduct> optionalProducts;

    @OneToMany(mappedBy="_package")
    private Collection<Order> orders;

    public Package(){}

    public Package(String name) {
        this.name = name;
    }

    public Package(String name, Collection<Service> services, Collection<ValidityPeriod> validityPeriods, Collection<OptionalProduct> optionalProducts){
        this.name=name;
        this.services=services;
        this.validityPeriods=validityPeriods;
        this.optionalProducts=optionalProducts;
    }
    public int getId_package() {
        return Id_package;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }

    public Collection<Service> getServices() {
        return services;
    }

    public Collection<ValidityPeriod> getValidityPeriods() {
        return validityPeriods;
    }

    public Collection<OptionalProduct> getOptionalProducts() {
        return optionalProducts;
    }
}
