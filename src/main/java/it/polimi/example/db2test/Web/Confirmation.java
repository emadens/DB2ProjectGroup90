package it.polimi.example.db2test.Web;

import it.polimi.example.db2test.EJB.Entities.User;
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

@WebServlet("/Confirmation")
public class Confirmation extends HttpServlet {

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

        User user = (User) request.getSession().getAttribute("user");

        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        if (user != null) {
            ctx.setVariable("loggedIn", 1);
            ctx.setVariable("username", user.getUsername());
            ctx.setVariable("p", request.getSession().getAttribute("p"));
            ctx.setVariable("selectedServices", request.getSession().getAttribute("selectedServices"));
            ctx.setVariable("vp", request.getSession().getAttribute("selectedValidityPeriod"));
            ctx.setVariable("selectedOP", request.getSession().getAttribute("selectedOP"));
            ctx.setVariable("startDate",request.getSession().getAttribute("startDate"));
            ctx.setVariable("packages", request.getAttribute("packages"));
        } else{
            ctx.setVariable("loggedIn", 0);
            ctx.setVariable("username", "Guest");
        }
        request.getSession().setAttribute("redirectConfirmation", true);

        String path = "/WEB-INF/confirmationPage.html";

        templateEngine.process(path, ctx, response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
