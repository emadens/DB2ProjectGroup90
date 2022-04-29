package it.polimi.example.db2test.Web.Employee;

import it.polimi.example.db2test.EJB.Entities.Service;
import it.polimi.example.db2test.EJB.Entities.Type;
import it.polimi.example.db2test.EJB.Entities.User;
import it.polimi.example.db2test.EJB.Exceptions.CredentialsException;
import it.polimi.example.db2test.EJB.Services.ServiceService;
import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CreateService")
public class createService extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "EJB/com/example/db_test2/EJB/ServiceService.java")
    private ServiceService sService;

    public createService(){}

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

        String path = "/WEB-INF/Employee/createServiceForm.html";
        ServletContext servletContext = getServletContext();
        Type[] typeValues = Type.values();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("typeValues", typeValues);
        templateEngine.process(path, ctx, response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Type type;
        int sms = -1, minutes = -1;
        float giga = -1, extraFeeGiga=-1, extraFeeMinutes=-1, extraFeeSms=-1;
        boolean err = false;

        type = (Type) request.getSession().getAttribute("type");

        switch (type) {
            case MOBILE_PHONE:

                sms = Integer.parseInt(request.getParameter("sms"));
                minutes = Integer.parseInt(request.getParameter("minutes"));
                extraFeeSms = Float.parseFloat(request.getParameter("extraFeeSms"));
                extraFeeMinutes = Float.parseFloat(request.getParameter("extraFeeMinutes"));
                if(sms<0 || minutes <0 || extraFeeMinutes < 0 || extraFeeSms < 0)
                    err = true;
                break;

            case FIXED_PHONE:
                break;

            case FIXED_INTERNET:

            case MOBILE_INTERNET:
                giga = Float.parseFloat(request.getParameter("giga"));
                extraFeeGiga = Float.parseFloat(request.getParameter("extraFeeGiga"));
                if(giga<0 || extraFeeGiga <0)
                    err = true;
                break;
        }

        String path;
        if (err) {

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            Type[] typeValues = Type.values();
            ctx.setVariable("errorMsg", "Please insert positive values for the parameters");
            ctx.setVariable("typeValues", typeValues);
            path = "/WEB-INF/Employee/createServiceForm.html";
            templateEngine.process(path, ctx, response.getWriter());
        } else {
            Service service = sService.createService(type, giga, sms, minutes, extraFeeGiga, extraFeeSms, extraFeeMinutes);
            path = getServletContext().getContextPath() + "/CreateService";
            response.sendRedirect(path);
        }
    }
    public void destroy() {
    }

}
