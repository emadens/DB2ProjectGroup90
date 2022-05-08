package it.polimi.example.db2test.EJB.MVT;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tot_purchases_per_pack_vp", schema = "telco_db")
public class TotPurchasesPerPackageVP {
    @Id
    private int package_id;
    private String package_name;
    private int vp_id;
    private int vp_months;
    private int purchases;

    public int getPackage_id() {
        return package_id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public int getVp_id() {
        return vp_id;
    }

    public int getVp_months() {
        return vp_months;
    }

    public int getPurchases() {
        return purchases;
    }
}
