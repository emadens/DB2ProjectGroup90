package it.polimi.example.db2test.Web.Employee;

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

@WebServlet("/CreateOP")
public class createOptionalProduct extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "EJB/com/example/db_test2/EJB/OptionalProductService.java")
    private OptionalProductService opService;

    public createOptionalProduct(){}

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

        String path = "/WEB-INF/Employee/createOptionalProduct.html";
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        templateEngine.process(path, ctx, response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name;
        float fee;
        boolean err = false;

        name = request.getParameter("name");
        fee = Float.parseFloat(request.getParameter("fee"));
        if(fee < 0)
            err = true;

        String path;
        if (err) {
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            ctx.setVariable("errorMsg", "Please insert valid values for the parameters");
            path = "/WEB-INF/Employee/createOptionalProduct.html";
            templateEngine.process(path, ctx, response.getWriter());
        } else {
            opService.createOptionalProduct(name, fee);
            path = getServletContext().getContextPath() + "/CreateOP";
            response.sendRedirect(path);
        }
    }

}
