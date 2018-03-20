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
import java.util.Map;

public class SignUpServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(SignUpServlet.class);

    private AccountDBService accountDBService;

    public SignUpServlet(AccountDBService accountDBService) {
        this.accountDBService = accountDBService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        AccountDataSet account = new AccountDataSet(req.getParameter("login"), req.getParameter("password"));

        Map<String, Object> pageVariables = new HashMap<>();
        try {
            accountDBService.save(account);
            pageVariables.put("signUpStatus", "Success");
        } catch (SQLException e) {
            LOG.error("Unable to sign up", e);
            pageVariables.put("signUpStatus", "Failed");
        }

        resp.getWriter().println(TemplateProcessor.instance().getPage("signup.html", pageVariables));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
