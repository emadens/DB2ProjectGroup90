package it.polimi.example.db2test.EJB.Services;

import it.polimi.example.db2test.EJB.Entities.User;
import it.polimi.example.db2test.EJB.Exceptions.CredentialsException;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
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

    public User checkCredentials(String username, String password) throws CredentialsException, NonUniqueResultException {
        List<User> uList = null;
        try {
            uList = em.createNamedQuery("user.checkCredentials", User.class).setParameter(1, username).setParameter(2, password)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentals");
        }
        if (uList.isEmpty())
            return null;
        else if (uList.size() == 1)
            return uList.get(0);
        throw new NonUniqueResultException("More than one user registered with same credentials");

    }
}