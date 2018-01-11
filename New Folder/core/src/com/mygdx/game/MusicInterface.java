package com.mygdx.game;

import java.util.ArrayList;

/**
 * Created by Whisp on 08/01/2018.
 */


public interface MusicInterface {
    boolean isReady();
    void Log();
    void SetupRun();
    void showPicker();
    String musicPath();

    ArrayList<Hittable> SendMap();
}
