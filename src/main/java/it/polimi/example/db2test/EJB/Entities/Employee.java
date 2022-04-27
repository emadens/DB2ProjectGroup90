package it.polimi.example.db2test.EJB.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "employee", schema = "telco_db")
@NamedQuery(name = "employee.checkCredentials", query = "SELECT e FROM Employee e WHERE e.name = ?1 and e.password = ?2")
public class Employee implements Serializable {
    @Id
    private String name;

    private String password;

    public Employee() {
    }

    public Employee(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
