package it.polimi.example.db2test.EJB.Services;
import it.polimi.example.db2test.EJB.Entities.*;
import it.polimi.example.db2test.EJB.Entities.Package;
import it.polimi.example.db2test.EJB.Exceptions.OrderIDException;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;
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

    public void createOrder(Timestamp timestamp, User user, Package _package, ValidityPeriod validityPeriod, boolean confirmed, float tot, Calendar start_date, Collection<OptionalProduct> optionalProducts){
        Order o = new Order(timestamp,  user,  _package,  validityPeriod,   confirmed,  tot,  start_date, optionalProducts);
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

    public List<Order> findRejectedOrders(String username){
        List<Order> rejOrders = null;
        try{
            rejOrders = em.createQuery("SELECT o FROM Order o WHERE o.confirmed = false and o.user.username=?1", Order.class).setParameter(1, username).setHint("jakarta.persistence.cache.storeMode", "REFRESH").getResultList();
        }
        catch (PersistenceException e){
            e.printStackTrace();
        }
        return rejOrders;
    }
    public List<Order> findAllOrders(){
        TypedQuery<Order> query = em.createQuery(
                "SELECT o FROM Order o", Order.class);
        return query.getResultList();
    }

    public void confirmPayment(int orderID) throws OrderIDException {
        if(orderID == -1)
            throw new OrderIDException("Order id not correctly read");
        else {
            Order o = em.find(Order.class, orderID);
            o.setConfirmed(true);
        }
    }


    public List<Order> findSuspended(){
        TypedQuery<Order> query = em.createQuery(
                "SELECT o FROM Order o WHERE o.confirmed=FALSE ", Order.class);
        return query.getResultList();
    }
}