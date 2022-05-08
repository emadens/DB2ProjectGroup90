package it.polimi.example.db2test.EJB.MVT;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tot_purchase_per_package", schema = "telco_db")
public class TotPurchasesPerPackage {
    @Id
    private int package_id;
    private String package_name;
    private int total_purchases;

    public int getPackage_id() {
        return package_id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public int getTotal_purchases() {
        return total_purchases;
    }
}
