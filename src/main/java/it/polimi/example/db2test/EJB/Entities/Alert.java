package it.polimi.example.db2test.EJB.Entities;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;



@Entity
@Table(name = "alerts", schema = "telco_db")
@IdClass(AlertsId.class)
public class Alert implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id Timestamp timestamp;

    @ManyToOne
    @Id
    @JoinColumn(name="username")
    private User user;

    private String email;
    private float amount;

    public Alert(User user, Timestamp timestamp) {
        this.user = user;
        this.timestamp=timestamp;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public User getUser() {
        return user;
    }
}
