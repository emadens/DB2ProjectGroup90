package it.polimi.example.db2test.Web;

import it.polimi.example.db2test.EJB.Entities.Package;
import it.polimi.example.db2test.EJB.Entities.User;
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
import java.util.Vector;

@WebServlet("/ChoosePackage")
public class ChoosePackageBS extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "EJB/com/example/db_test2/PackageService.java")
    private PackageService pService;
    public ChoosePackageBS(){}
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

        Package p =null;
        p=pService.findPackage(Integer.parseInt(request.getParameter("packageSelected")));
        User user = (User) request.getSession().getAttribute("user");

        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        request.getSession().setAttribute("p", p);
        if(user!=null)
            ctx.setVariable("username", user.getUsername());
        else
            ctx.setVariable("username", "Guest");
        ctx.setVariable("pSel", p);
        ctx.setVariable("packages", request.getSession().getAttribute("packages"));
        ctx.setVariable("services", p.getServices());
        ctx.setVariable("packSel", true);
        String path = "/WEB-INF/buyService.html";
        templateEngine.process(path, ctx, response.getWriter());
    }
}
