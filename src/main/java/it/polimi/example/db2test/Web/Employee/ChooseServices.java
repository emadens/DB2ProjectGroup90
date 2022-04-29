package it.polimi.example.db2test.Web.Employee;

import it.polimi.example.db2test.EJB.Entities.Service;
import it.polimi.example.db2test.EJB.Entities.Type;
import it.polimi.example.db2test.EJB.Entities.ValidityPeriod;
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

@WebServlet("/ChooseServices")
public class ChooseServices extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "EJB/com/example/db_test2/EJB/ServiceService.java")
    private ServiceService sService;

    @EJB(name = "EJB/com/example/db_test2/EJB/ValidityPeriodService.java")
    private ValidityPeriodService vpService;

    public ChooseServices(){}

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
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<ValidityPeriod> vps = new ArrayList<>();
        vps = vpService.findAllServices();
        List<Service> services = new ArrayList<>();
        List<Service> selectedServices = new ArrayList<>();
        services = sService.findAllServices();
        for(Service s: services){
            String param = request.getParameter("p-" + s.getService_id());
            if(param != null){
                selectedServices.add(sService.findService(Integer.parseInt(param)));
            }
        }

        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        request.getSession().setAttribute("selectedServices", selectedServices);
        ctx.setVariable("servicesDone", 1);
        ctx.setVariable("vps", vps);
        String path = "/WEB-INF/Employee/createPackageForm.html";
        templateEngine.process(path, ctx, response.getWriter());
    }
}
