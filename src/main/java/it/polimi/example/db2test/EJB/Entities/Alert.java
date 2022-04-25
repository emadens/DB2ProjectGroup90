package it.polimi.example.db2test.EJB.Entities;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;



@Entity
@Table(name = "alerts", schema = "telco_db")
@NamedQueries({ @NamedQuery(name = "user.findByName", query = "SELECT u FROM User u WHERE u.username = :username"),
        @NamedQuery(name = "user.findAll", query = "SELECT u FROM User u")})
public class Alert implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    AlertsId alertsId;

    @Id Timestamp timestamp;

    @ManyToOne
    @Id
    @JoinColumn(name="username")
    private User username;

    private String email;
    private float amount;

    public Alert(AlertsId alertsId) {
        this.alertsId = alertsId;
    }

    public Alert() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
