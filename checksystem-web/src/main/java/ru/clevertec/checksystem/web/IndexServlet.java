package ru.clevertec.checksystem.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "")
@MultipartConfig
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var source = req.getParameter("source");

        if (source.equals("file")) {
            var filePart = req.getPart("file");
            var fileStream = filePart.getInputStream();
            var dataFormat = getFileNameExtension(filePart.getSubmittedFileName());
            var data = fileStream.readAllBytes();
            req.getSession().setAttribute("data", new SessionData(dataFormat, data));
            resp.sendRedirect(req.getContextPath() + "/checks?source=session");

        } else { // default (memory)
            resp.sendRedirect(req.getContextPath() + "/checks?source=memory");
        }
    }

    private String getFileNameExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
}
