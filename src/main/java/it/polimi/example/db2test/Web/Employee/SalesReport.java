package it.polimi.example.db2test.Web.Employee;

import it.polimi.example.db2test.EJB.Entities.Alert;
import it.polimi.example.db2test.EJB.Entities.OptionalProduct;
import it.polimi.example.db2test.EJB.Entities.Order;
import it.polimi.example.db2test.EJB.Entities.User;
import it.polimi.example.db2test.EJB.MVT.AvgNumberOfOP;
import it.polimi.example.db2test.EJB.MVT.TotPurchasesPerPackage;
import it.polimi.example.db2test.EJB.MVT.TotPurchasesPerPackageVP;
import it.polimi.example.db2test.EJB.MVT.TotSalesValuePerPackage;
import it.polimi.example.db2test.EJB.Services.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/SalesReport")
public class SalesReport extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "EJB/com/example/db_test2/MVTService.java")
    private MVTService vService;

    @EJB(name = "EJB/com/example/db_test2/UserService.java")
    private UserService uService;

    @EJB(name = "EJB/com/example/db_test2/OptionalProductService.java")
    private OptionalProductService opService;
    @EJB(name = "EJB/com/example/db_test2/OrderService.java")
    private OrderService oService;
    @EJB(name = "EJB/com/example/db_test2/AlertService.java")
    private AlertService aService;

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<TotPurchasesPerPackage> tot_ppp = vService.totPurchasesPerPackages();
        List<TotPurchasesPerPackageVP> tot_ppvp = vService.totPurchasesPerPackageVPS();
        List<TotSalesValuePerPackage> totSales = vService.totSalesValuePerPackages();
        List<AvgNumberOfOP> avg = vService.avgNumberOfOPS();
        List<User> insolvent = uService.findInsolvent();
        List<Order> suspended = oService.findSuspended();
        List<Alert> alerts = aService.findAllAlerts();
        OptionalProduct bestSeller=opService.findBestSeller();
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("tot_ppp", tot_ppp);
        ctx.setVariable("tot_ppvp", tot_ppvp);
        ctx.setVariable("totSales", totSales);
        ctx.setVariable("avgOPxP", avg);
        ctx.setVariable("insolventUsers", insolvent);
        ctx.setVariable("suspendedOrders", suspended);
        ctx.setVariable("alerts", alerts);
        ctx.setVariable("bestseller", bestSeller);
        String path = "/WEB-INF/Employee/salesReport.html";
        templateEngine.process(path, ctx, response.getWriter());

    }
}
