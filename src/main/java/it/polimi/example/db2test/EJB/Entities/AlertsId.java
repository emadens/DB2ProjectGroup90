package it.polimi.example.db2test.EJB.Entities;



import java.io.Serializable;
import java.sql.Timestamp;

public class AlertsId implements Serializable {
    String user;
    Timestamp timestamp;

    public AlertsId(String user, Timestamp timestamp) {
        this.user = user;
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
