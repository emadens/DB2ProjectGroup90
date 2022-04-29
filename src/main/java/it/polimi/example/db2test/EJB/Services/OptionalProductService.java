package it.polimi.example.db2test.EJB.Services;

import it.polimi.example.db2test.EJB.Entities.OptionalProduct;
import it.polimi.example.db2test.EJB.Entities.ValidityPeriod;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@LocalBean
public class OptionalProductService {
    @PersistenceContext(unitName = "UserEJB")
    private EntityManager em;

    public OptionalProduct createOptionalProduct(String name, float fee){
        OptionalProduct op = new OptionalProduct(name, fee);
        em.persist(op);
        return op;
    }

    // TODO: Verify if it works, the OP has an ID?
    public OptionalProduct findOptionalProduct(int id){
        return em.find(OptionalProduct.class, id);
    }
    public void removeOptionalProduct(int id){
        OptionalProduct op= findOptionalProduct(id);
        if(op!=null){
            em.remove(op);
        }
    }
    public List<OptionalProduct> findAllOptionalProducts(){
        TypedQuery<OptionalProduct> query = em.createQuery(
                "SELECT op FROM OptionalProduct op", OptionalProduct.class);
        return query.getResultList();
    }
}
