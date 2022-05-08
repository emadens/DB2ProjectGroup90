package it.polimi.example.db2test.Web;


import it.polimi.example.db2test.EJB.Entities.*;
import it.polimi.example.db2test.EJB.Entities.Package;
import it.polimi.example.db2test.EJB.Services.OrderService;
import it.polimi.example.db2test.EJB.Services.PackageService;
import it.polimi.example.db2test.EJB.Services.ServiceService;
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

@WebServlet("/Home")
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "EJB/com/example/db_test2/PackageService.java")
    private PackageService pService;
    @EJB(name = "EJB/com/example/db_test2/OrderService.java")
    private OrderService oService;

    public Home() {
        super();
    }

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
        String path = "/WEB-INF/homePage.html";
        User user = (User) request.getSession().getAttribute("user");
        List<Order> rejOrders = new ArrayList<>();
        List<Package> packages = new ArrayList<>();

        packages =  pService.findAllPackages();


        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        ctx.setVariable("packages", packages);
        if(user!=null) {
            rejOrders = oService.findRejectedOrders(user.getUsername());
            if(rejOrders!=null) ctx.setVariable("rejOrders", rejOrders);
            ctx.setVariable("loggedIn", 1);
            ctx.setVariable("username", user.getUsername());
        }else{
            ctx.setVariable("username", "Guest");
            ctx.setVariable("loggedIn", 0);
        }

        templateEngine.process(path, ctx, response.getWriter());
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
    }

}
