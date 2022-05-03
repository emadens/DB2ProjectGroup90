package it.polimi.example.db2test.Web;

import it.polimi.example.db2test.EJB.Entities.OptionalProduct;
import it.polimi.example.db2test.EJB.Entities.Service;
import it.polimi.example.db2test.EJB.Entities.ValidityPeriod;
import it.polimi.example.db2test.EJB.Services.OptionalProductService;
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
import java.util.Date;
import java.util.List;

@WebServlet("/ChooseDateBS")
public class ChooseDateBS extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

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
            throws IOException, ServletException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Date startDate=new Date();
        System.out.println(startDate.getDate()+" "+startDate.getMonth()+" "+startDate.getYear());
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        request.getSession().setAttribute("startDate", startDate);
        ctx.setVariable("recap", true);
        ctx.setVariable("p", request.getSession().getAttribute("p"));
        ctx.setVariable("selectedServices", (List< Service>)request.getSession().getAttribute("selectedServices"));
        ctx.setVariable("vp", (ValidityPeriod)request.getSession().getAttribute("selectedValidityPeriod"));
        ctx.setVariable("selectedOP", (List<OptionalProduct>)request.getSession().getAttribute("selectedOP"));
        ctx.setVariable("startDate",startDate);
        ctx.setVariable("packages", request.getAttribute("packages"));
        String path = "/WEB-INF/confirmationPage.html";
        templateEngine.process(path, ctx, response.getWriter());
    }
}
