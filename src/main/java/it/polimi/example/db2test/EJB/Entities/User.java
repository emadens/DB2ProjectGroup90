package it.polimi.example.db2test.EJB.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "user", schema = "telco_db")
@NamedQueries({ @NamedQuery(name = "user.findByName", query = "SELECT u FROM User u WHERE u.username = ?1"),
        @NamedQuery(name = "user.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "user.checkCredentials", query = "SELECT u FROM User u WHERE u.username = ?1 and u.password = ?2")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String username;
    private String password;
    private String email;
    private boolean solvent = true;
    private int failedPayments = 0;
    @OneToMany(mappedBy= "user")
    private Collection<Order> orders;


    @OneToMany(mappedBy ="User")
    private Collection<Alert> alerts;
    public User(){
    }



    public User(String _username, String _password, String _email){
        this.username = _username;
        this.password = _password;
        this.email = _email;
    }

    public String getUsername() {return this.username;}

    public void setUsername(String newUsername){this.username = newUsername;}

    public String getPassword() {return this.password;}

    public void setPassword(String newPassword){this.password = newPassword;}

    public String getEmail(){return this.email;}

    public void setEmail(String newEmail){this.email = newEmail;}


    public String toString(){
        return this.getUsername() +" " + this.getPassword() + " " + this.getEmail();
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public boolean isSolvent() {
        return solvent;
    }

    public void setSolvent(boolean solvent) {
        this.solvent = solvent;
    }

    public Collection<Alert> getAlerts() {
        return alerts;
    }

    public int getFailedPayments() {
        return failedPayments;
    }

    public void setFailedPayments(int failedPayments) {
        this.failedPayments = failedPayments;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }

    public void setAlerts(Collection<Alert> alerts) {
        this.alerts = alerts;
    }
}
