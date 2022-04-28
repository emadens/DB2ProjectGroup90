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

        type = Type.valueOf(request.getParameter("typeValue"));
        switch (type) {
            case MOBILE_PHONE:
                try {
                    sms = Integer.parseInt(request.getParameter("sms"));
                    minutes = Integer.parseInt(request.getParameter("minutes"));
                    extraFeeSms = Float.parseFloat(request.getParameter("extraFeeSms"));
                    extraFeeMinutes = Float.parseFloat(request.getParameter("extraFeeMinutes"));
                    if(sms==-1 || minutes == -1 || extraFeeSms == -1 || extraFeeMinutes == -1) {
                        throw new Exception("Missing or empty parameters value");
                    }
                    break;
                }
                catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters value");
                    return;
                }

            case FIXED_PHONE:
                break;

            case FIXED_INTERNET:

            case MOBILE_INTERNET:
                try {
                    giga = Float.parseFloat(request.getParameter("giga"));
                    extraFeeGiga = Float.parseFloat(request.getParameter("extraFeeGiga"));
                    if(giga==-1 || extraFeeGiga == -1 ) {
                        throw new Exception("Missing or empty parameters value");
                    }
                    break;
                }
                catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters value");
                    return;
                }
        }

        Service service = sService.createService(type, giga, sms, minutes, extraFeeGiga, extraFeeSms, extraFeeMinutes);


    }
    public void destroy() {
    }

}
