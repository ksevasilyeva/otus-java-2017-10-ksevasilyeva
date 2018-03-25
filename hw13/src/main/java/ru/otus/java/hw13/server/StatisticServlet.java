package ru.otus.java.hw13.server;

import org.eclipse.jetty.http.HttpStatus;
import ru.otus.java.hw10.data.DataSet;
import ru.otus.java.hw11.cache.CacheEngine;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/stat")
public class StatisticServlet extends HttpServlet {

    private CacheEngine<DataSet> cache;
    private ServletContext servletContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        servletContext = config.getServletContext();
        cache = (CacheEngine) servletContext.getAttribute("cache");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Object userAttr = req.getSession().getAttribute("user");
        if (userAttr != null && userAttr.equals("authorised")) {
            resp.setStatus(HttpStatus.OK_200);
            Map<String, Object> pageVariables = createPageVariablesMap();

            resp.getWriter().println(TemplateProcessor.instance().getPage("stat.html", pageVariables));

            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private Map<String, Object> createPageVariablesMap() {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("cacheSize", cache.getCurrentSize());
        pageVariables.put("cacheHit", cache.getHitCount());
        pageVariables.put("cacheMiss", cache.getMissCount());

        return pageVariables;
    }
}