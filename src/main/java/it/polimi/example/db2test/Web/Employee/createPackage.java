package it.polimi.example.db2test.Web.Employee;

import it.polimi.example.db2test.EJB.Entities.OptionalProduct;
import it.polimi.example.db2test.EJB.Entities.Service;
import it.polimi.example.db2test.EJB.Entities.ValidityPeriod;
import it.polimi.example.db2test.EJB.Services.OptionalProductService;
import it.polimi.example.db2test.EJB.Services.PackageService;
import it.polimi.example.db2test.EJB.Services.ServiceService;
import it.polimi.example.db2test.EJB.Services.ValidityPeriodService;
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

@WebServlet("/CreatePackage")
public class createPackage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;


    @EJB(name = "EJB/com/example/db_test2/EJB/ServiceService.java")
    private ServiceService sService;


    @EJB(name = "EJB/com/example/db_test2/EJB/PackageService.java")
    private PackageService pService;

    public createPackage(){}

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String path = "/WEB-INF/Employee/createPackageForm.html";
        ServletContext servletContext = getServletContext();
        List<Service> services = new ArrayList<>();
        services =  sService.findAllServices();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("services", services);
        ctx.setVariable("serviceUP", true);
        templateEngine.process(path, ctx, response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Service> selectedServices = new ArrayList<>();
        List<ValidityPeriod> validityPeriods = new ArrayList<>();
        List<OptionalProduct> optionalProducts = new ArrayList<>();
        String name = null;
        selectedServices = (List<Service>) request.getSession().getAttribute("selectedServices");
        validityPeriods = (List<ValidityPeriod>) request.getSession().getAttribute("selectedVP");
        optionalProducts = (List<OptionalProduct>) request.getSession().getAttribute("selectedOP");

         name = request.getParameter("name");

        pService.createPackage(name, selectedServices, validityPeriods, optionalProducts);
        request.getSession().removeAttribute("selectedServices");
        request.getSession().removeAttribute("selectedVP");
        request.getSession().removeAttribute("selectedOP");
        String path = getServletContext().getContextPath() + "/CreatePackage";
        response.sendRedirect(path);
    }
}
