package ru.clevertec.checksystem.webuiservlet;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Questionnaire {

    private final Random random = new Random();
    private final Map<String, String[]> questionAnswerMap = new HashMap<>();
    private String lastQuestion;

    public String nextQuestion() {
        var randIndex = random.nextInt(questionAnswerMap.size());
        var index = 0;
        for (var entry : questionAnswerMap.entrySet()) {
            if (index == randIndex) {
                var newQuestion = entry.getKey();
                return questionAnswerMap.size() > 1 && newQuestion.equals(lastQuestion)
                        ? nextQuestion()
                        : (lastQuestion = entry.getKey());
            }
            index++;
        }
        throw new NoSuchElementException();
    }

    public Boolean verify(String question, String answer) {
        var answers = questionAnswerMap.get(question);
        return Arrays.binarySearch(answers, answer.toLowerCase(Locale.ROOT).trim()) >= 0;
    }

    public String lastQuestion() {
        return lastQuestion;
    }

    public void addQuestion(String question, String[] answers) {
        questionAnswerMap.put(question, answers);
    }

    public Set<String> getQuestions() {
        return questionAnswerMap.keySet();
    }

    public String[] questionAnswers(String question) {
        return questionAnswerMap.get(question);
    }
}
