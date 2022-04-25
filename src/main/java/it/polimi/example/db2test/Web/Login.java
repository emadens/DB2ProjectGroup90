package it.polimi.example.db2test.Web;


import it.polimi.example.db2test.EJB.Entities.User;
import it.polimi.example.db2test.EJB.Services.UserService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;


@WebServlet("/Login")
public class Login extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "EJB/com/example/db_test2/UserService.java")
    private UserService uService;

    public Login() {
        super();
    }

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
        String path = "/WEB-INF/loginForm.html";
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        templateEngine.process(path, ctx, response.getWriter());
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username, password, email;
        username = request.getParameter("Username");
        password= request.getParameter("Password");
        boolean value = uService.verifyUser(username, password);
        String path = value? "index.html" : "/WEB-INF/LoginForm.html";
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        templateEngine.process(path, ctx, response.getWriter());
    }
}

