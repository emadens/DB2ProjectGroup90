package it.polimi.example.db2test.EJB.Services;
import it.polimi.example.db2test.EJB.Entities.ValidityPeriod;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
@Stateless
@LocalBean
public class ValidityPeriodService {
    @PersistenceContext(unitName = "UserEJB")
    private EntityManager em;

    public ValidityPeriodService(EntityManager em) {
        this.em = em;
    }

    public void createValidityPeriod(int id, int months, float fee){
        ValidityPeriod vp=new ValidityPeriod(id);
        vp.setMonths(months);
        vp.setFee(fee);
        em.persist(vp);
    }

    public ValidityPeriod findValidityPeriod(int id){
        return em.find(ValidityPeriod.class, id);
    }
    public void removeValidityPeriod(int id){
        ValidityPeriod vp= findValidityPeriod(id);
        if(vp!=null){
            em.remove(vp);
        }
    }
    public List<ValidityPeriod> findAllServices(){
        TypedQuery<ValidityPeriod> query = em.createQuery(
                "SELECT vp FROM ValidityPeriod vp", ValidityPeriod.class);
        return query.getResultList();
    }
}
