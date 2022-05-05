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
import java.util.Calendar;
import java.util.TimeZone;
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
        User user = (User) request.getSession().getAttribute("user");

        if(user!=null)
            ctx.setVariable("username", user.getUsername());
        else
            ctx.setVariable("username", "Guest");
        ctx.setVariable("packages", packages);
        ctx.setVariable("pSel", packages.get(0));
        request.getSession().setAttribute("packages", packages);
        ctx.setVariable("packUP", true);
        templateEngine.process(path, ctx, response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // TODO: to test with Calendar type instead of Date type (deprecated) already modified properly

        // Read from and to works, to check what happens into the database.

        String[] dates = request.getParameter("startDate").split("-");

        Calendar calendarDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendarDate.set(Calendar.YEAR, Integer.parseInt(dates[0]));
        calendarDate.set(Calendar.MONTH, Integer.parseInt(dates[1])-1);
        calendarDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dates[2]));

        request.getSession().setAttribute("startDate", calendarDate);

        String path = getServletContext().getContextPath() + "/Confirmation";
        response.sendRedirect(path);
    }
}
