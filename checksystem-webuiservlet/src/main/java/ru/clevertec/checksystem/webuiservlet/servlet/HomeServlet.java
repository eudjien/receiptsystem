package ru.clevertec.checksystem.webuiservlet.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.clevertec.checksystem.core.repository.ReceiptRepository;
import ru.clevertec.checksystem.webuiservlet.ReceiptDataSource;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.clevertec.checksystem.webuiservlet.Constants.*;

@Component
@WebServlet(
        name = ServletNames.HOME_SERVLET,
        urlPatterns = UrlPatterns.HOME_PATTERN
)
public class HomeServlet extends ApplicationServlet {

    private ReceiptRepository receiptRepository;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var source = req.getParameter(Parameters.SOURCE_PARAMETER) != null
                ? req.getParameter(Parameters.SOURCE_PARAMETER)
                : Sources.DATABASE;

        var receipts = new ReceiptDataSource(receiptRepository, req.getSession(), Sessions.RECEIPTS_SESSION)
                .findAll(source);

        req.setAttribute(Attributes.RECEIPTS_ATTRIBUTE, receipts);
        req.setAttribute(Attributes.SOURCE_ATTRIBUTE, source);

        req.getRequestDispatcher(Pages.HOME_PAGE).forward(req, resp);
    }

    @Autowired
    public void setReceiptRepository(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }
}
