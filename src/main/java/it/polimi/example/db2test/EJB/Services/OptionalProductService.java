package it.polimi.example.db2test.EJB.Services;

import it.polimi.example.db2test.EJB.Entities.OptionalProduct;
import it.polimi.example.db2test.EJB.Entities.ValidityPeriod;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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

    public OptionalProduct findOptionalProduct(String name){
        return em.find(OptionalProduct.class, name);
    }
    public void removeOptionalProduct(String name){
        OptionalProduct op= findOptionalProduct(name);
        if(op!=null){
            em.remove(op);
        }
    }
    public List<OptionalProduct> findAllOptionalProducts(){
        TypedQuery<OptionalProduct> query = em.createQuery(
                "SELECT op FROM OptionalProduct op", OptionalProduct.class);
        return query.getResultList();
    }

    public OptionalProduct findBestSeller(){
        String bestSellerQuery="select opName from bestseller";
        String bestSeller = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/telco_db","root", "illBaroz");
            Statement stmt=con.createStatement();
            ResultSet rs= stmt.executeQuery(bestSellerQuery);
            bestSeller=rs.getString("opName");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return findOptionalProduct(bestSeller);
    }
}
