package it.polimi.example.db2test.EJB.MVT;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "avg_num_of_op", schema = "telco_db")
public class AvgNumberOfOP {
    @Id
    private int package_id;
    private String package_name;
    private float avg_num_op;

    public int getPackage_id() {
        return package_id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public float getAvg_num_op() {
        return avg_num_op;
    }
}
