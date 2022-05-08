package it.polimi.example.db2test.EJB.Services;

import it.polimi.example.db2test.EJB.Entities.OptionalProduct;
import it.polimi.example.db2test.EJB.MVT.AvgNumberOfOP;
import it.polimi.example.db2test.EJB.MVT.TotPurchasesPerPackage;
import it.polimi.example.db2test.EJB.MVT.TotPurchasesPerPackageVP;
import it.polimi.example.db2test.EJB.MVT.TotSalesValuePerPackage;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@LocalBean
public class MVTService {
    @PersistenceContext(unitName = "UserEJB")
    private EntityManager em;
    public List<AvgNumberOfOP> avgNumberOfOPS(){
        TypedQuery<AvgNumberOfOP> query = em.createQuery(
                "SELECT a FROM AvgNumberOfOP a", AvgNumberOfOP.class);
        return query.getResultList();
    }

    public List<TotPurchasesPerPackage> totPurchasesPerPackages(){
        TypedQuery<TotPurchasesPerPackage> query = em.createQuery(
                "SELECT t FROM TotPurchasesPerPackage t", TotPurchasesPerPackage.class);
        return query.getResultList();
    }

    public List<TotPurchasesPerPackageVP> totPurchasesPerPackageVPS(){
        TypedQuery<TotPurchasesPerPackageVP> query = em.createQuery(
                "SELECT t FROM TotPurchasesPerPackageVP t", TotPurchasesPerPackageVP.class);
        return query.getResultList();
    }

    public List<TotSalesValuePerPackage> totSalesValuePerPackages(){
        TypedQuery<TotSalesValuePerPackage> query = em.createQuery(
                "SELECT t FROM TotSalesValuePerPackage t", TotSalesValuePerPackage.class);
        return query.getResultList();
    }

}
