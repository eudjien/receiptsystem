package ru.clevertec.checksystem.webmvcjdbc;


import ru.clevertec.checksystem.core.util.ThrowUtils;

public class Authentication {

    private final boolean authenticated;
    private final String question;
    private final String answer;

    protected Authentication(boolean authenticated, String question, String answer) {
        this.authenticated = authenticated;
        this.question = question;
        this.answer = answer;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public static Authentication Authenticated(String question, String answer) {
        ThrowUtils.Argument.nullOrBlank("question", question);
        ThrowUtils.Argument.nullOrBlank("answer", answer);
        return new Authentication(true, question, answer);
    }

    public static Authentication Anonymous() {
        return new Authentication(false, null, null);
    }
}
