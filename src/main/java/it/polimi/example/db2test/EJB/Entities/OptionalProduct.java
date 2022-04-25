package it.polimi.example.db2test.EJB.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "order", schema = "telco_db")
public class OptionalProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String name;
    private float fee;

    @ManyToMany
    @JoinTable(name="oop", schema = "telco_db", joinColumns = @JoinColumn (name = "OP_name"), inverseJoinColumns = @JoinColumn (name = "Order_ID"))
    private Collection<Order> orders;

    @ManyToMany
    @JoinTable(name="pop", schema = "telco_db", joinColumns = @JoinColumn (name = "OP_name"), inverseJoinColumns = @JoinColumn (name = "Package_ID"))
    private Collection<Package> packages;


    public OptionalProduct(){}

    public OptionalProduct(String name, float fee) {
        this.name = name;
        this.fee = fee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }
}
