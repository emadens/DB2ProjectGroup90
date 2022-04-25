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
    @JoinColumn(name = "Package_ID")
    private Collection<Service> services;

    @ManyToMany
    @JoinColumn(name = "package_ID")
    private Collection<ValidityPeriod> validityPeriods;

    @ManyToMany
    @JoinColumn(name = "Package_ID")
    private Collection<OptionalProduct> optionalProducts;

    @OneToMany(mappedBy="_package")
    private Collection<Order> orders;

    public Package(){}

    public Package(int id_package, String name) {
        Id_package = id_package;
        this.name = name;
    }

    public int getId_package() {
        return Id_package;
    }

    public void setId_package(int id_package) {
        Id_package = id_package;
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

}
