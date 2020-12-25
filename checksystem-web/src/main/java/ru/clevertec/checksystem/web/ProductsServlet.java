package ru.clevertec.checksystem.web;

import ru.clevertec.checksystem.core.DataSeed;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/products/*")
public class ProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        var products = DataSeed.Products();

        writer.println("[");
        products.forEach(a -> {
            writer.println("    {");
            writer.println("        id: " + a.getId() + ",");
            writer.println("        name: " + a.getName() + ",");
            writer.println("        price: " + a.getPrice() + ",");
            writer.println("    }" + ",");
        });
        writer.println("]");

        writer.close();

        super.doGet(req, resp);
    }
}
