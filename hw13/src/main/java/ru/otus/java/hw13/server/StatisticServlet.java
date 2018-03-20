package ru.otus.java.hw13.server;

import org.eclipse.jetty.http.HttpStatus;
import ru.otus.java.hw10.data.DataSet;
import ru.otus.java.hw11.cache.CacheEngine;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StatisticServlet extends HttpServlet {

    private final CacheEngine<DataSet> cache;

    public StatisticServlet(CacheEngine<DataSet> cacheEngine) {
        this.cache = cacheEngine;
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