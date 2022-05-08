package it.polimi.example.db2test.EJB.MVT;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tot_salesvalue_per_package", schema = "telco_db")
public class TotSalesValuePerPackage {
    @Id
    private int package_id;
    private String package_name;
    private float tot;
    private float tot_with_op;

    public int getPackage_id() {
        return package_id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public float getTot() {
        return tot;
    }

    public float getTot_with_op() {
        return tot_with_op;
    }
}
