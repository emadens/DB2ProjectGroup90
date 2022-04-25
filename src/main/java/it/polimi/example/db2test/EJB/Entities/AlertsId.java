package it.polimi.example.db2test.EJB.Entities;



import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Timestamp;

public class AlertsId implements Serializable {
    String username;
    Timestamp timestamp;

    public AlertsId(String username, Timestamp timestamp) {
        this.username = username;
        this.timestamp = timestamp;
    }
}
