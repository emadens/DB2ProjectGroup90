package it.polimi.example.db2test.Web.Employee;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/SalesReport")
public class SalesReport extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder stringBuilder= new StringBuilder();
        String tot_ppp = "select package_name, totale_purchases from tot_purchase_per_package";
        String tot_ppvp= "SELECT package_name, vp_months, purchases FROM telco_db.tot_purchases_per_pack_vp";
        String totSalesV= "SELECT package_name, tot, tot_with_op FROM telco_db.tot_salesvalue_per_package";
        String avgOPxP= "SELECT pack_name, avg_num_op FROM telco_db.avg_num_of_op";
        String insolventUsers="select username from user where solvent=0";
        String suspendedOrders="select timestamp, username, tot from telco_db.order where confirmed=0";
        String alerts="select * from alerts";
        String bestSeller="select opName from bestseller";
        List<String> tot_ppp_r = new ArrayList<>();
        List<String> tot_ppvp_r = new ArrayList<>();
        List<String> totSales_r = new ArrayList<>();
        List<String> avg_r = new ArrayList<>();
        List<String> insolvent_r = new ArrayList<>();
        List<String> suspended_r = new ArrayList<>();
        List<String> alerts_r = new ArrayList<>();
        List<String> bestSeller_r = new ArrayList<>();
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/telco_db", "root", "illBaroz");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(tot_ppp);
            while (rs.next()) {
                String package_name=rs.getString("package_name");
                int tot=rs.getInt("totale_purchases");
                tot_ppp_r.add(package_name+" "+tot+"\n");
            }
            System.out.println(stringBuilder.toString());
            ctx.setVariable("tot_ppp", tot_ppp_r);
            rs = stmt.executeQuery(tot_ppvp);
            while (rs.next()) {

                String package_name=rs.getString("package_name");
                int months=rs.getInt("vp_months");
                int tot=rs.getInt("purchases");
                tot_ppvp_r.add(package_name+" "+months+""+tot+"\n");
            }
            System.out.println(stringBuilder.toString());
            ctx.setVariable("tot_ppvp", tot_ppvp_r);
            rs = stmt.executeQuery(totSalesV);
            while (rs.next()) {

                String package_name=rs.getString("package_name");
                float tot=rs.getFloat("tot");
                float tot_op=rs.getFloat("tot_with_op");
                totSales_r.add(package_name+" "+tot+" "+tot_op);
            }
            ctx.setVariable("totSales",totSales_r);
            rs = stmt.executeQuery(avgOPxP);
            while (rs.next()) {

                String package_name=rs.getString("pack_name");
                float avg=rs.getInt("avg_num_op");
                avg_r.add(package_name+" "+avg+"\n");
            }
            ctx.setVariable("avgOPxP", avg_r);
            rs = stmt.executeQuery(insolventUsers);
            while (rs.next()) {

                String username=rs.getString("username");
                insolvent_r.add(username);
            }
            ctx.setVariable("insolventUsers", insolvent_r);
            //TOdo alerts
            rs = stmt.executeQuery(suspendedOrders);
            while (rs.next()) {
                Timestamp timestamp=rs.getTimestamp("timestamp");
                String username=rs.getString("username");
                Float tot=rs.getFloat("tot");
                suspended_r.add(timestamp+" "+username+" "+tot);
            }
            ctx.setVariable("suspendedOrders", suspended_r);
            rs = stmt.executeQuery(bestSeller);
            while (rs.next()) {
                String opName=rs.getString("opName");
                bestSeller_r.add(opName);
            }
            ctx.setVariable("bestSeller", bestSeller_r);
        }catch(SQLException | ClassNotFoundException throwables){
                throwables.printStackTrace();
        }

        String path = "/WEB-INF/Employee/salesReport.html";
        templateEngine.process(path, ctx, response.getWriter());

    }
}
