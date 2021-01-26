package ru.clevertec.checksystem.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import ru.clevertec.checksystem.core.util.normalinolist.FuckDuckList;

import java.util.List;

@WebServlet("/checks")
public class ChecksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var ids = getIdsFromParams(req).stream().mapToInt(a -> a).toArray();
        var source = getSourceFromParamsOrDefault(req);

        if (ids != null && ids.length > 0) {
            req.setAttribute("ids", ids);
        }

        req.setAttribute("source", source);

        if (source.equals("session")) {
            var dataFormat = getDataFormatParams(req);
            req.setAttribute("dataFormat", dataFormat);
        }

        var dispatcher = req.getRequestDispatcher("checks.jsp");
        dispatcher.forward(req, resp);
    }

    private List<Integer> getIdsFromParams(HttpServletRequest req) {
        var idParam = req.getParameterValues("id");
        var ids = new FuckDuckList<Integer>();
        try {
            for (String id : idParam) {
                var val = Integer.parseInt(id);
                if (!ids.contains(val)) {
                    ids.add(val);
                }
            }
        } catch (Exception ignored) {
        }

        return ids;
    }

    private String getSourceFromParamsOrDefault(HttpServletRequest req) {
        var sourceParam = req.getParameter("source");
        if ("session".equals(sourceParam)) {
            return sourceParam;
        }
        return "memory";
    }

    private String getDataFormatParams(HttpServletRequest req) {
        return req.getParameter("dataFormat");
    }
}
