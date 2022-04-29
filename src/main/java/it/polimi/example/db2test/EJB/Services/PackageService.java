package it.polimi.example.db2test.EJB.Services;
import it.polimi.example.db2test.EJB.Entities.OptionalProduct;
import it.polimi.example.db2test.EJB.Entities.Package;
import it.polimi.example.db2test.EJB.Entities.Service;
import it.polimi.example.db2test.EJB.Entities.ValidityPeriod;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

import java.util.List;

@Stateless
@LocalBean
public class PackageService {
    @PersistenceContext(unitName = "UserEJB")
    private EntityManager em;

    public void createPackage(String name, Collection<Service> services, Collection<ValidityPeriod> validityPeriods, Collection<OptionalProduct> optionalProducts){
        Package p=new Package(name, services, validityPeriods, optionalProducts);
        em.persist(p);
    }

    public Package findValidityPeriod(int id){
        return em.find(Package.class, id);
    }
    public void removeValidityPeriod(int id){
        Package p= findValidityPeriod(id);
        if(p!=null){
            em.remove(p);
        }
    }
    public List<Package> findAllPackages(){
        TypedQuery<Package> query = em.createQuery(
                "SELECT p FROM Package p", Package.class);
        return query.getResultList();
    }
}