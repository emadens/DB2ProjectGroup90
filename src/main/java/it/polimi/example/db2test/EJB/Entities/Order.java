package it.polimi.example.db2test.EJB.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "order", schema = "telco_db")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int idOrder;
    private Timestamp timestamp;
    @ManyToOne
    @JoinColumn(name = "Username")
    private User user;

    @ManyToOne
    @JoinColumn(name = "Package_ID")
    private Package _package;

    @ManyToOne
    @JoinColumn(name = "VP_ID")
    private ValidityPeriod validityPeriod;

    @ManyToMany
    @JoinTable(name="oop", schema = "telco_db", joinColumns = @JoinColumn (name = "Order_ID"), inverseJoinColumns = @JoinColumn (name = "OP_name"))
    private Collection<OptionalProduct> optionalProducts;

    private boolean confirmed;
    private float tot;
    private Date start_date;

    public Order(){}

    public Order(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Package get_package() {
        return _package;
    }

    public void set_package(Package _package) {
        this._package = _package;
    }

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(ValidityPeriod validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public float getTot() {
        return tot;
    }

    public void setTot(float tot) {
        this.tot = tot;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Collection<OptionalProduct> getOptionalProducts() {
        return optionalProducts;
    }
}
