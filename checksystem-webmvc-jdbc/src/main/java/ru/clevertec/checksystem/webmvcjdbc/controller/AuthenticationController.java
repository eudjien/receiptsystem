package ru.clevertec.checksystem.webmvcjdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.clevertec.checksystem.webmvcjdbc.Authentication;
import ru.clevertec.checksystem.webmvcjdbc.Questionnaire;
import ru.clevertec.checksystem.webmvcjdbc.constant.Attributes;
import ru.clevertec.checksystem.webmvcjdbc.constant.Parameters;
import ru.clevertec.checksystem.webmvcjdbc.constant.Sessions;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

    private final Questionnaire questionnaire;

    @Autowired
    public AuthenticationController(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
        addQuestions(this.questionnaire);
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute(Attributes.ANSWER_INCORRECT_ATTRIBUTE, false);
        model.addAttribute(Attributes.QUESTION_ATTRIBUTE, questionnaire.nextQuestion());
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam(name = Parameters.ANSWER_PARAMETER) String answer, Model model, HttpSession session) {
        if (questionnaire.verify(questionnaire.lastQuestion(), answer)) {
            session.setAttribute(Sessions.AUTHENTICATION_SESSION, Authentication.Authenticated(questionnaire.lastQuestion(), answer));
            return new ModelAndView("redirect:/");
        } else {
            model.addAttribute(Attributes.QUESTION_ATTRIBUTE, questionnaire.lastQuestion());
            model.addAttribute(Attributes.ANSWER_INCORRECT_ATTRIBUTE, true);
            return new ModelAndView("login");
        }
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.setAttribute(Sessions.AUTHENTICATION_SESSION, Authentication.Anonymous());
        return new ModelAndView("redirect:/");
    }

    private static void addQuestions(Questionnaire questionnaire) {
        questionnaire.addQuestion("Ноль в нулевой степени", new String[]{"1", "один"});
        questionnaire.addQuestion("Время (секунды), за которое свет, пущенный с Земли, достигает Луны?", new String[]{"0.255", "0,255"});
        questionnaire.addQuestion("Количество лапок у муравья?", new String[]{"6", "шесть"});
        questionnaire.addQuestion("Количество лапок у паука?", new String[]{"8", "восемь"});
        questionnaire.addQuestion("Чего хочет дима?", new String[]{"servlet", "колбасы"});
    }
}
