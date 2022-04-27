package it.polimi.example.db2test.EJB.Services;
import it.polimi.example.db2test.EJB.Entities.Employee;
import it.polimi.example.db2test.EJB.Entities.User;
import it.polimi.example.db2test.EJB.Exceptions.CredentialsException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
@LocalBean
public class EmployeeService {
    @PersistenceContext(unitName = "UserEJB")
    private EntityManager em;

    public Employee checkCredentials(String name, String password) throws CredentialsException, NonUniqueResultException {
        List<Employee> eList = null;
        try {
            eList = em.createNamedQuery("employee.checkCredentials", Employee.class).setParameter(1, name).setParameter(2, password)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentals");
        }
        if (eList.isEmpty())
            return null;
        else if (eList.size() == 1)
            return eList.get(0);
        throw new NonUniqueResultException("More than one user registered with same credentials");

    }
}
