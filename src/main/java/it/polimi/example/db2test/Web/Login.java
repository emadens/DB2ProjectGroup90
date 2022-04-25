package it.polimi.example.db2test.Web;


import it.polimi.example.db2test.EJB.Entities.User;
import it.polimi.example.db2test.EJB.Exceptions.CredentialsException;
import it.polimi.example.db2test.EJB.Services.UserService;
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
import javax.servlet.http.*;

import java.io.IOException;

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
        String username, password;

        try {
            username = StringEscapeUtils.escapeJava(request.getParameter("Username"));
            password = StringEscapeUtils.escapeJava(request.getParameter("Password"));
            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                throw new Exception("Missing or empty credential value");
            }
        } catch (Exception e) {
            // for debugging only e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
            return;
        }
        User user;
        try {
            // query db to authenticate for user
            user = uService.checkCredentials(username, password);
        } catch (CredentialsException | NonUniqueResultException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
            return;
        }

        // If the user exists, add info to the session and go to home page, otherwise
        // show login page with error message

        // deleted queryService - can give possible problems
        String path;
        if (user == null) {
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            ctx.setVariable("errorMsg", "Incorrect username or password");
            path = "/loginForm.html";
            templateEngine.process(path, ctx, response.getWriter());
        } else {
            request.getSession().setAttribute("user", user);
            path = getServletContext().getContextPath() + "/Home";
            response.sendRedirect(path);
        }
    }
    public void destroy() {
    }
}

