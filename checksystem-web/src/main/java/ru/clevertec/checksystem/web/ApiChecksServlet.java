package ru.clevertec.checksystem.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.checksystem.core.DataSeed;
import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.checksystem.core.io.printer.CheckPrinter;
import ru.clevertec.checksystem.core.io.printer.strategy.HtmlCheckPrintStrategy;
import ru.clevertec.checksystem.core.io.printer.strategy.TextCheckPrintStrategy;
import ru.clevertec.checksystem.core.io.reader.factory.CheckReaderCreator;
import ru.clevertec.checksystem.core.io.writer.CheckWriter;
import ru.clevertec.checksystem.core.io.writer.JsonCheckWriter;
import ru.clevertec.checksystem.core.io.writer.XmlCheckWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/api/checks/*")
public class ApiChecksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");

        var checkIds = getIdsFromParams(req);

        var checks = checkIds.isEmpty()
                ? getDataSource(req, resp) : filterChecksById(checkIds, getDataSource(req, resp));

        if (checks.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        var format = getFormatFromParams(req);
        var type = getTypeFromParams(req);
        var download = getIsDownloadFromParams(req);

        switch (type) {
            case "print" -> {
                var split = format.split("-");
                var wrapToJson = split.length > 1 && split[1].equals("json");

                var checkPrinter = new CheckPrinter(checks);

                switch (split[0]) {
                    case "html" -> {
                        checkPrinter.setStrategy(new HtmlCheckPrintStrategy());
                        resp.setContentType("text/html;charset=utf-8");
                        if (download) {
                            resp.setHeader("Content-disposition", "attachment; filename=check" + ".html");
                        }
                    }
                    case "text" -> {
                        checkPrinter.setStrategy(new TextCheckPrintStrategy());
                        resp.setContentType("application/json;charset=utf-8");
                        if (download) {
                            resp.setHeader("Content-disposition", "attachment; filename=check" + ".txt");
                        }
                    }
                    default -> {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                }
                try {

                    if (wrapToJson) {
                        var data = checkPrinter.print();
                        var mapper = new ObjectMapper();
                        resp.getOutputStream().write(mapper.writeValueAsBytes(data.stream()
                                .map(a -> new CheckPrintDto(a.getId(), new String(a.getData(),
                                        StandardCharsets.UTF_8))).collect(Collectors.toList())));
                    } else {
                        var data = checkPrinter.printRaw();
                        resp.getOutputStream().write(data);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case "serialize" -> {
                CheckWriter checkWriter;
                switch (format) {
                    case "json" -> {
                        checkWriter = new JsonCheckWriter();
                        resp.setContentType("application/json;charset=utf-8");
                        if (download) {
                            resp.setHeader("Content-disposition", "attachment; filename=check" + ".json");
                        }
                    }
                    case "xml" -> {
                        checkWriter = new XmlCheckWriter();
                        resp.setContentType("application/xml;charset=utf-8");
                        if (download) {
                            resp.setHeader("Content-disposition", "attachment; filename=check" + ".xml");
                        }
                    }
                    default -> {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                }
                try {
                    resp.getOutputStream().write(checkWriter.write(checks));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<Check> getDataSource(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var source = getSourceFromParamsOrDefault(req);

        if (source.equals("session")) {
            var data = getDataFromSession(req);

            if (data == null || data.getData() == null || data.getData().length == 0) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Data not found");
            }

            if (data.getFormat() == null || data.getFormat().isBlank()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Data format not found");
            }

            try {
                var reader = new CheckReaderCreator().create(data.getFormat());
                return reader.readMany(data.getData());
            } catch (Exception e) {
                e.printStackTrace();
                //resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unsupported format or incorrect data");
            }
        }

        return DataSeed.Checks();
    }

    private List<Check> filterChecksById(List<Integer> ids, List<Check> dataSource) {
        try {
            return dataSource.stream().filter(a -> ids.contains(a.getId())).collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private List<Integer> getIdsFromParams(HttpServletRequest req) {
        var idParam = req.getParameterValues("id");
        var ids = new ArrayList<Integer>();
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

    private String getFormatFromParams(HttpServletRequest req) {
        var format = "text";
        var formatParam = req.getParameter("format");
        if (formatParam != null) {
            var availableTypes = new String[]{"html", "text", "json", "html-json", "text-json"};
            var index = Arrays.binarySearch(availableTypes, formatParam);
            if (index != -1) {
                format = formatParam;
            }
        }
        return format;
    }

    private boolean getIsDownloadFromParams(HttpServletRequest req) {
        var dlParam = req.getParameter("download");
        return dlParam != null && (dlParam.equals("") || dlParam.equals("true"));
    }

    private String getTypeFromParams(HttpServletRequest req) {
        var typeParam = req.getParameter("type");
        var availableTypes = new String[]{"print", "serialize"};
        if (typeParam != null) {
            var index = Arrays.binarySearch(availableTypes, typeParam);
            if (index != -1) {
                return typeParam;
            }
        }
        return availableTypes[0];
    }

    private String getSourceFromParamsOrDefault(HttpServletRequest req) {
        var sourceParam = req.getParameter("source");
        if ("session".equals(sourceParam)) {
            return sourceParam;
        }
        return "memory";
    }

    private SessionData getDataFromSession(HttpServletRequest req) {
        return (SessionData) req.getSession().getAttribute("data");
    }
}
