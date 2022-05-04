package it.polimi.example.db2test.Web;

import it.polimi.example.db2test.EJB.Entities.*;
import it.polimi.example.db2test.EJB.Entities.Package;
import it.polimi.example.db2test.EJB.Services.OptionalProductService;
import it.polimi.example.db2test.EJB.Services.OrderService;
import it.polimi.example.db2test.EJB.Services.PackageService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@WebServlet("/Purchase")
public class Purchase extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "EJB/com/example/db_test2/PackageService.java")
    private PackageService pService;

    @EJB(name = "EJB/com/example/db_test2/EJB/OrderService.java")
    private OrderService oService;

    @EJB(name = "EJB/com/example/db_test2/OptionalProductService.java")
    private OptionalProductService opService;

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = "/WEB-INF/buyService.html";
        ServletContext servletContext = getServletContext();
        Vector<Package> packages = (Vector<Package>) pService.findAllPackages();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        ctx.setVariable("packages", packages);
        request.getSession().setAttribute("packages", packages);
        ctx.setVariable("packUP", true);
        templateEngine.process(path, ctx, response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("p", request.getSession().getAttribute("p"));
        ctx.setVariable("selectedServices", (List<Service>)request.getSession().getAttribute("selectedServices"));
        ctx.setVariable("vp", (ValidityPeriod)request.getSession().getAttribute("selectedValidityPeriod"));
        ctx.setVariable("selectedOP", (List<OptionalProduct>)request.getSession().getAttribute("selectedOP"));
        ctx.setVariable("startDate",request.getSession().getAttribute("startDate"));



    }
}
