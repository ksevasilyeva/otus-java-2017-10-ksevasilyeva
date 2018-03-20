package ru.otus.java.hw13.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.hw13.data.AccountDataSet;
import ru.otus.java.hw13.db.AccountDBService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class LoginServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);
    private AccountDBService accountDBService;

    public LoginServlet(AccountDBService accountDBService) {
        this.accountDBService = accountDBService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        if (validateUser(req.getParameter("login"), req.getParameter("password"))) {
            req.getSession().setAttribute("user", "authorised");
            resp.sendRedirect("/stat");
        } else {
            resp.getWriter().println(TemplateProcessor.instance().getPage("loginFailed.html", new HashMap<>()));
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    private boolean validateUser(String login, String pass) {
        try {
            AccountDataSet account = accountDBService.load(login);
            if (account != null && account.getPassword().equals(pass)) {
                return true;
            }
        } catch (SQLException e) {
            LOG.error("Unable to get account details", e);
        }
        return false;
    }
}
