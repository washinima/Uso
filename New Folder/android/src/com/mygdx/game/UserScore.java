package com.mygdx.game;

import android.os.Bundle;
import android.app.Activity;

public class UserScore {

    public UserScore(String userName, int userScore) {
        this.userName = userName;
        this.userScore = userScore;
    }

    public UserScore() {

    }

    String userName;
    int userScore;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }
}
