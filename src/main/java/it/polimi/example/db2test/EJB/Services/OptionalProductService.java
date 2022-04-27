package it.polimi.example.db2test.EJB.Services;

import it.polimi.example.db2test.EJB.Entities.OptionalProduct;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
