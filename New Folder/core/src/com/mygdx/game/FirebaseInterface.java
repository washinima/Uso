package com.mygdx.game;

/**
 * Created by Marcio on 14/01/2018.
 */

public interface FirebaseInterface
{
    void FirebaseLogin();
    void ShowLeaderboards();
    void updateScore(int score);
    boolean isReady();
    void setReady(boolean ready);
}
