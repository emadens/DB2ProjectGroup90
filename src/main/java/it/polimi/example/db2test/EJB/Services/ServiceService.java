package it.polimi.example.db2test.EJB.Services;
import it.polimi.example.db2test.EJB.Entities.Service;
import it.polimi.example.db2test.EJB.Entities.Type;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

import java.util.List;

@Stateless
@LocalBean
public class ServiceService {
    @PersistenceContext(unitName = "UserEJB")
    private EntityManager em;

    public Service createService(Type type, float giga, int sms, int minutes, float extraFeeGiga, float extraFeeSMS, float extraFeeMinutes){
        Service s = null;
        if (type==Type.FIXED_PHONE){
            s=new Service();
            s.setType(type);
            em.persist(s);
        }
        if (type==Type.FIXED_INTERNET){
            s=new Service();
            s.setType(type);
            s.setGiga(giga);
            s.setExtraFeeGiga(extraFeeGiga);
            em.persist(s);
        }
        if (type==Type.MOBILE_INTERNET){
            s=new Service();
            s.setType(type);
            s.setGiga(giga);
            s.setExtraFeeGiga(extraFeeGiga);
            em.persist(s);
        }
        if (type==Type.MOBILE_PHONE){
            s=new Service();
            s.setType(type);
            s.setMinutes(minutes);
            s.setSms(sms);
            s.setExtraFeeMinutes(extraFeeMinutes);
            s.setExtraFeeSMS(extraFeeSMS);
            em.persist(s);
        }
        return s;
    }

    public Service findService(int id){
        return em.find(Service.class, id);
    }
    public void removeService(int id_service){
        Service s= findService(id_service);
        if(s!=null){
            em.remove(s);
        }
    }
    public List<Service> findAllServices(){
        TypedQuery<Service> query = em.createQuery(
                "SELECT s FROM Service s", Service.class);
        return query.getResultList();
    }

}
