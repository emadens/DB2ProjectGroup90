package it.polimi.example.db2test.Web;

import it.polimi.example.db2test.EJB.Entities.OptionalProduct;
import it.polimi.example.db2test.EJB.Entities.Package;
import it.polimi.example.db2test.EJB.Entities.User;
import it.polimi.example.db2test.EJB.Entities.ValidityPeriod;
import it.polimi.example.db2test.EJB.Services.OptionalProductService;
import it.polimi.example.db2test.EJB.Services.OrderService;
import it.polimi.example.db2test.EJB.Services.PackageService;
import it.polimi.example.db2test.EJB.Services.UserService;
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
import java.sql.Timestamp;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@WebServlet("/Confirmation")
public class Confirmation extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "EJB/com/example/db_test2/PackageService.java")
    private PackageService pService;

    @EJB(name = "EJB/com/example/db_test2/EJB/OrderService.java")
    private OrderService oService;

    @EJB(name = "EJB/com/example/db_test2/UserService.java")
    private UserService uService;

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
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy"); // for reading from Calendar

        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        if (user != null) {
            ctx.setVariable("loggedIn", 1);
            ctx.setVariable("username", user.getUsername());
            ctx.setVariable("p", request.getSession().getAttribute("p"));
            ctx.setVariable("selectedServices", request.getSession().getAttribute("selectedServices"));
            ctx.setVariable("vp", request.getSession().getAttribute("selectedValidityPeriod"));
            ctx.setVariable("selectedOP", request.getSession().getAttribute("selectedOP"));
            Calendar calendar = (Calendar) request.getSession().getAttribute("startDate");
            ctx.setVariable("startDate", sdf.format(calendar.getTime()));
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
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        User user= (User) request.getSession().getAttribute("user");
        Package pack= (Package) request.getSession().getAttribute("p");
        ValidityPeriod vp=(ValidityPeriod) request.getSession().getAttribute("selectedValidityPeriod");
        List<OptionalProduct> optionalProducts= (List<OptionalProduct>) request.getSession().getAttribute("selectedOP");
        boolean confirmation=true;
        Calendar startDate=(Calendar) request.getSession().getAttribute("startDate");
        float tot=vp.getFee()*vp.getMonths()+optionalProducts.stream().map(OptionalProduct::getFee).reduce((float) 0, Float::sum);
        //TODO: da un column doesn't match errore: da capire se è nei trrigger o è un problema di orm
        oService.createOrder(timestamp,user,pack,vp,confirmation,tot,startDate,optionalProducts);
    }
}
