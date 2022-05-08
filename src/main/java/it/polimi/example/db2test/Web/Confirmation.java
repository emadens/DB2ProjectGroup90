package it.polimi.example.db2test.Web;

import it.polimi.example.db2test.EJB.Entities.*;
import it.polimi.example.db2test.EJB.Entities.Package;
import it.polimi.example.db2test.EJB.Exceptions.OrderIDException;
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
import java.sql.Timestamp;
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
            System.out.println("idOrder: " + request.getParameter("idOrder"));
            ctx.setVariable("loggedIn", 1);
            ctx.setVariable("username", user.getUsername());
            if(request.getParameter("idOrder")!=null){
                int idOrder = Integer.parseInt(request.getParameter("idOrder"));
                Order o = oService.findOrder(idOrder);
                Package p = o.get_package();
                List<Service> services = (List<Service>) p.getServices();
                ValidityPeriod vp = o.getValidityPeriod();
                List<OptionalProduct> ops = (List<OptionalProduct>) o.getOptionalProducts();
                Calendar calendar = o.getStart_date();
                ctx.setVariable("p", p);
                ctx.setVariable("selectedServices",services);
                ctx.setVariable("vp", vp);
                ctx.setVariable("selectedOP", ops);
                ctx.setVariable("startDate", sdf.format(calendar.getTime()));
                ctx.setVariable("existent", o.getId_Order());
            }
            else {
                ctx.setVariable("existent", -1);
                ctx.setVariable("p", request.getSession().getAttribute("p"));
                ctx.setVariable("selectedServices", request.getSession().getAttribute("selectedServices"));
                ctx.setVariable("vp", request.getSession().getAttribute("selectedValidityPeriod"));
                ctx.setVariable("selectedOP", request.getSession().getAttribute("selectedOP"));
                Calendar calendar = (Calendar) request.getSession().getAttribute("startDate");
                ctx.setVariable("startDate", sdf.format(calendar.getTime()));
            }
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

        int orderID = -1;
        boolean confirmation = request.getParameter("billing").equals("1");
        boolean existent = !request.getParameter("existent").equals("-1");
        if(existent) orderID = Integer.parseInt(request.getParameter("existent"));
        Order o = (Order) request.getSession().getAttribute("order");
        System.out.println("Existent: " + request.getParameter("existent"));
        System.out.println("Confirmation:" + confirmation);
        if(!existent){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            User user= (User) request.getSession().getAttribute("user");
            Package pack= (Package) request.getSession().getAttribute("p");
            ValidityPeriod vp=(ValidityPeriod) request.getSession().getAttribute("selectedValidityPeriod");
            List<OptionalProduct> optionalProducts= (List<OptionalProduct>) request.getSession().getAttribute("selectedOP");
            Calendar startDate=(Calendar) request.getSession().getAttribute("startDate");
            float tot=vp.getFee()*vp.getMonths()+optionalProducts.stream().map(OptionalProduct::getFee).map(x->x*vp.getMonths()).reduce((float) 0, Float::sum);
            oService.createOrder(timestamp,user,pack,vp,confirmation,tot,startDate,optionalProducts);
        }
        else if(confirmation) {
            try {
                oService.confirmPayment(orderID);
            } catch (OrderIDException e) {
                throw new RuntimeException(e);
            }
        }

        String path = getServletContext().getContextPath() + "/Home";
        response.sendRedirect(path);
    }
}
