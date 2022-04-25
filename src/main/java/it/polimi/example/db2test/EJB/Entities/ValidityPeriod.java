package it.polimi.example.db2test.EJB.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;


@Entity
@Table(name = "validity_period", schema = "telco_db")
public class ValidityPeriod implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int ID_validity_period;
    private int Months;
    private float Fee;

    @OneToMany(mappedBy="validityPeriod")
    private Collection<Order> orders;

    @ManyToMany
    @JoinTable(name="pvp", schema = "telco_db", joinColumns = @JoinColumn (name = "validity_period_id"), inverseJoinColumns = @JoinColumn (name = "package_ID"))
    private Collection<Package> packages;


    public ValidityPeriod() {}

    public ValidityPeriod(int Months, float Fee) {
        this.Months = Months;
        this.Fee = Fee;
    }

    public int getMonths() {return Months;}

    public void setMonths(int months) {Months = months;}

    public float getFee() {return Fee;}

    public void setFee(float fee) {Fee = fee;}

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }



}
