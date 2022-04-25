package it.polimi.example.db2test.EJB.Services;

import it.polimi.example.db2test.EJB.Entities.User;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
@LocalBean
public class UserService {
    @PersistenceContext(unitName = "UserEJB")
    private EntityManager em;

    public List<User> findAllUsers() {
        return em.createNamedQuery("user.findAll", User.class)
                .setHint("jakarta.persistence.cache.storeMode", "REFRESH").getResultList();
    }

    User findByName(String username) {
        try {
            return em.createNamedQuery("user.findByName", User.class).setParameter(1, username)
                    .getSingleResult();
        }
        catch(PersistenceException e) {
            return null;
        }

    }

    public void createUser(String username, String password,String email) {
        User u;
        if(username!=null && password!=null && email!=null) {
            u = new User();
            u.setUsername(username);
            u.setPassword(password);
            u.setEmail(email);
            em.persist(u);
        }
    }

    public boolean verifyUser(String username, String password) {
        User u = findByName(username);
        return (u!= null) && u.getPassword().equals(password);
    }
}