package com.gujeducation.gujaratedu.Model;

/**
 * Created by Tikarye Ashish on 17/09/2019.
 */

public class Quiz {
    String questionType, question, ans_A, ans_B, ans_C, ans_D, ans_correct, ans_correct_option;
    int quizDetailsId, quizId;


    public Quiz(String questionType, String question, String ans_A, String ans_B, String ans_C, String ans_D, String ans_correct, String ans_correct_option, int quizDetailsId, int quizId) {
        this.questionType = questionType;
        this.question = question;
        this.ans_A = ans_A;
        this.ans_B = ans_B;
        this.ans_C = ans_C;
        this.ans_D = ans_D;
        this.ans_correct = ans_correct;
        this.ans_correct_option = ans_correct_option;
        this.quizDetailsId = quizDetailsId;
        this.quizId = quizId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAns_A() {
        return ans_A;
    }

    public void setAns_A(String ans_A) {
        this.ans_A = ans_A;
    }

    public String getAns_B() {
        return ans_B;
    }

    public void setAns_B(String ans_B) {
        this.ans_B = ans_B;
    }

    public String getAns_C() {
        return ans_C;
    }

    public void setAns_C(String ans_C) {
        this.ans_C = ans_C;
    }

    public String getAns_D() {
        return ans_D;
    }

    public void setAns_D(String ans_D) {
        this.ans_D = ans_D;
    }

    public String getAns_correct() {
        return ans_correct;
    }

    public void setAns_correct(String ans_correct) {
        this.ans_correct = ans_correct;
    }

    public String getAns_correct_option() {
        return ans_correct_option;
    }

    public void setAns_correct_option(String ans_correct_option) {
        this.ans_correct_option = ans_correct_option;
    }

    public int getQuizDetailsId() {
        return quizDetailsId;
    }

    public void setQuizDetailsId(int quizDetailsId) {
        this.quizDetailsId = quizDetailsId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }
}
