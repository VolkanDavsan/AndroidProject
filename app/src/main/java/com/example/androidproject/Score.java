package com.example.androidproject;

public class Score {
    String  score, date;

    public String getScore() {
        return score;
    }

    public Score(String score, String date) {
        this.score = score;
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
