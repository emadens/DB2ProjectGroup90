package it.polimi.example.db2test.EJB.Services;
import it.polimi.example.db2test.EJB.Entities.Package;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

import java.util.List;

@Stateless
@LocalBean
public class PackageService {
    @PersistenceContext(unitName = "UserEJB")
    private EntityManager em;

    public PackageService(EntityManager em) {
        this.em = em;
    }

    public void createPackage(int id, String name){
        Package p=new Package(id, name);
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
    public List<Package> findAllServices(){
        TypedQuery<Package> query = em.createQuery(
                "SELECT p FROM Package p", Package.class);
        return query.getResultList();
    }
}