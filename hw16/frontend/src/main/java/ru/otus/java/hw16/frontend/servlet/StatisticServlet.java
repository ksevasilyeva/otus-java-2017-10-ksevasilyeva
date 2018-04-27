package ru.otus.java.hw16.frontend.servlet;

import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(displayName = "StatisticServlet",
    name = "StatisticServlet",
    urlPatterns = "/stat")
public class StatisticServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Object userAttr = req.getSession().getAttribute("user");
        if (userAttr != null && userAttr.equals("authorised")) {
            resp.setStatus(HttpStatus.OK_200);
//            Map<String, Object> pageVariables = createPageVariablesMap();

//            resp.getWriter().println(TemplateProcessor.instance().getPage("ws-stat.html", pageVariables));

            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
            RequestDispatcher disp = req.getRequestDispatcher("/stat.html");
            disp.forward(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

}