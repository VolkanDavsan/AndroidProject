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

    public int getStatusImg(){
        if(Integer.parseInt(this.getScore()) >3){
          return R.drawable.great;
        } else if(Integer.parseInt(this.getScore())>1){
            return R.drawable.okay;
        }
        else{
           return R.drawable.tryagain;
        }
    }
}
