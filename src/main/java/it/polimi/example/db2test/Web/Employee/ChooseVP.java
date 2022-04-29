package it.polimi.example.db2test.Web.Employee;

import it.polimi.example.db2test.EJB.Entities.OptionalProduct;
import it.polimi.example.db2test.EJB.Entities.Service;
import it.polimi.example.db2test.EJB.Entities.ValidityPeriod;
import it.polimi.example.db2test.EJB.Services.OptionalProductService;
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

@WebServlet("/ChooseVP")
public class ChooseVP extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "EJB/com/example/db_test2/EJB/ValidityPeriodService.java")
    private ValidityPeriodService vpService;
    @EJB(name = "EJB/com/example/db_test2/EJB/OptionalProductService.java")
    private OptionalProductService opService;

    public ChooseVP(){}

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
        List<OptionalProduct> ops = new ArrayList<>();
        ops = opService.findAllOptionalProducts();
        List<ValidityPeriod> validityPeriods = new ArrayList<>();
        List<ValidityPeriod> selectedVP = new ArrayList<>();
        validityPeriods = vpService.findAllServices();
        for(ValidityPeriod vp: validityPeriods){
            String param = request.getParameter("vp-" + vp.getID_validity_period());
            if(param != null){
                selectedVP.add(vpService.findValidityPeriod(Integer.parseInt(param)));
            }
        }

        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        request.getSession().setAttribute("selectedVP", selectedVP);
        ctx.setVariable("servicesDone", true);
        ctx.setVariable("VPDone", true);
        ctx.setVariable("ops", ops);
        String path = "/WEB-INF/Employee/createPackageForm.html";
        templateEngine.process(path, ctx, response.getWriter());
    }

}
