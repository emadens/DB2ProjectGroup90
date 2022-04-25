package it.polimi.example.db2test.EJB.Services;
import it.polimi.example.db2test.EJB.Entities.Alert;
import it.polimi.example.db2test.EJB.Entities.AlertsId;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@LocalBean
public class AlertService {
    @PersistenceContext(unitName = "UserEJB")
    private EntityManager em;

    public AlertService(EntityManager em) {
        this.em = em;
    }

    public void createValidityPeriod(AlertsId id){
        Alert a=new Alert(id);
        //TODO
        em.persist(a);
    }

    public Alert findAlert(int id){
        return em.find(Alert.class, id);
    }
    public void removeValidityPeriod(int id){
        Alert a= findAlert(id);
        if(a!=null){
            em.remove(a);
        }
    }
    public List<Alert> findAllServices(){
        TypedQuery<Alert> query = em.createQuery(
                "SELECT vp FROM ValidityPeriod vp", Alert.class);
        return query.getResultList();
    }
}