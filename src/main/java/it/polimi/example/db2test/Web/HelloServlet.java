package it.polimi.example.db2test.Web;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String DB_URL = "jdbc:mysql://localhost:3306/telco_db"; //Replace with your own configuration
        final String USER = "root"; //Replace with your own configuration
        final String PASS = "balema02"; //Replace with your own configuration
        String result = "Connection worked";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            result = "Connection failed"; e.printStackTrace();
        }
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println(result);
        out.close();
    }

    public void destroy() {
    }
}

