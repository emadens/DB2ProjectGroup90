package it.polimi.example.db2test.EJB.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;


@Entity
@Table(name = "validity_period", schema = "telco_db")
public class ValidityPeriod implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID_validity_period;
    private int months;
    private float fee;

    @OneToMany(mappedBy="validityPeriod")
    private Collection<Order> orders;

    @ManyToMany
    @JoinTable(name="pvp", schema = "telco_db", joinColumns = @JoinColumn (name = "validity_period_id"), inverseJoinColumns = @JoinColumn (name = "package_ID"))
    private Collection<Package> packages;


    public ValidityPeriod() {}

    public ValidityPeriod(int months, float fee){
        this.months = months;
        this.fee = fee;
    }

    public int getMonths() {return months;}

    public void setMonths(int months) {this.months = months;}

    public float getFee() {return fee;}

    public void setFee(float fee) {this.fee = fee;}

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }



}
