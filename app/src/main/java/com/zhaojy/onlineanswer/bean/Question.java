package com.zhaojy.onlineanswer.bean;

import java.util.List;

public class Question {
    private int id;
    private String question;
    private List<Options> options;
    private String correctOption;
    private int questionSortId;
    private int questionDifficultId;
    /**
     * 我的选择
     */
    private String myOption;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Options> getOptions() {
        return options;
    }

    public void setOptions(List<Options> options) {
        this.options = options;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }

    public int getQuestionSortId() {
        return questionSortId;
    }

    public int getQuestionDifficultId() {
        return questionDifficultId;
    }

    public void setQuestionDifficultId(int questionDifficultId) {
        this.questionDifficultId = questionDifficultId;
    }

    public void setQuestionSortId(int questionSortId) {
        this.questionSortId = questionSortId;
    }

    public String getMyOption() {
        return myOption;
    }

    public void setMyOption(String myOption) {
        this.myOption = myOption;
    }

    public class Options {
        private String optionName;
        private String optionContent;

        public String getOptionName() {
            return optionName;
        }

        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }

        public String getOptionContent() {
            return optionContent;
        }

        public void setOptionContent(String optionContent) {
            this.optionContent = optionContent;
        }

    }

}
