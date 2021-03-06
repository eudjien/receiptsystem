package ru.clevertec.checksystem.webuiservlet.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.clevertec.checksystem.webuiservlet.Questionnaire;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.clevertec.checksystem.webuiservlet.Constants.*;
import static ru.clevertec.checksystem.webuiservlet.Constants.Parameters.ANSWER_PARAMETER;

@Component
@WebServlet(
        name = ServletNames.AUTHENTICATION_SERVLET,
        urlPatterns = UrlPatterns.AUTHENTICATION_PATTERN
)
public class AuthenticationServlet extends HttpServlet {

    private Questionnaire questionnaire;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
        addQuestions(questionnaire);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(Attributes.ANSWER_INCORRECT_ATTRIBUTE, false);
        req.setAttribute(Attributes.QUESTION_ATTRIBUTE, questionnaire.nextQuestion());
        req.getRequestDispatcher(Pages.AUTHENTICATION_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var answer = req.getParameter(ANSWER_PARAMETER);

        if (questionnaire.verify(questionnaire.lastQuestion(), answer)) {
            req.setAttribute(Attributes.ANSWER_INCORRECT_ATTRIBUTE, false);
            req.getSession().setAttribute(Sessions.AUTHENTICATED, true);
            resp.sendRedirect(req.getContextPath() + UrlPatterns.ROOT_PATTERN);
        } else {
            req.setAttribute(Attributes.QUESTION_ATTRIBUTE, questionnaire.lastQuestion());
            req.setAttribute(Attributes.ANSWER_INCORRECT_ATTRIBUTE, true);
            req.getRequestDispatcher(Pages.AUTHENTICATION_PAGE).forward(req, resp);
        }
    }

    private static void addQuestions(Questionnaire questionnaire) {
        questionnaire.addQuestion("Ноль в нулевой степени", new String[]{"1", "один"});
        questionnaire.addQuestion("Время (секунды), за которое свет, пущенный с Земли, достигает Луны?", new String[]{"0.255", "0,255"});
        questionnaire.addQuestion("Количество лапок у муравья?", new String[]{"6", "шесть"});
        questionnaire.addQuestion("Количество лапок у паука?", new String[]{"8", "восемь"});
    }

    @Autowired
    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }
}
