package it.polimi.example.db2test.EJB.Services;
import it.polimi.example.db2test.EJB.Entities.Order;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

import java.sql.Timestamp;
import java.util.List;

@Stateless
@LocalBean
public class OrderService {
    @PersistenceContext(unitName = "UserEJB")
    private EntityManager em;

    /*public OrderService(EntityManager em) {
        this.em = em;
    }*/

    public void createOrder(int id){
        //TODO
        Order o=new Order(id);
        em.persist(o);
    }

    public Order findOrder(int id){
        return em.find(Order.class, id);
    }
    public void removeOrder(int id){
        Order o= findOrder(id);
        if(o!=null){
            em.remove(o);
        }
    }
    public List<Order> findAllServices(){
        TypedQuery<Order> query = em.createQuery(
                "SELECT o FROM Order o", Order.class);
        return query.getResultList();
    }
}