package com.mygdx.game;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.firebase.auth.FirebaseAuth;


public class AndroidLauncher extends AndroidApplication {

    String music;
    boolean ready_music;

    boolean ready_fire;

    public class AndroidMusic implements MusicInterface {
        private final MusicAPI api;


        public AndroidMusic()
        {
            api = new MusicAPI();
            ready_music = false;
            ready_fire = false;
            music = null;
        }

        public ArrayList<Circle> SendMap()
        {
            return api.list;
        }

        public void SetupRun() {
            api.Setup(getApplicationContext(), music);
            api.Start(getApplicationContext());

            ready_music = false;
        }

        public boolean Over()
        {
            return api.dispatcher.isStopped();
        }

        public void Log()
        {
            Log.d("List",
                    Integer.toString(
                            api.list.size()
                    )
            );
        }

        public boolean isReady() {return ready_music;}

        public String musicPath() {return music;}

        public void showPicker()
        {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 1);
        }
    }

    public class LibgdxFirebase implements FirebaseInterface{
        private FirebaseAuth mAuth;
        private FirebaseAPI api;

        public LibgdxFirebase()
        {
            mAuth = FirebaseAuth.getInstance();

            api = new FirebaseAPI(mAuth);
        }

        public void updateScore(int score)
        {
            api.updateScore(score);
        }

        public boolean isReady() {return ready_fire;}

        public void setReady(boolean ready) { ready_fire = ready;}

        public void FirebaseLogin()
        {
            Intent i = new Intent(getApplicationContext(), FireBaseLogin.class);
            startActivityForResult(i, 2);
        }

        public void ShowLeaderboards()
        {
            Intent i = new Intent(getApplicationContext(), ScoresActivity.class);
            startActivity(i);
        }

        public boolean isOnline(){
            return api.isOnline();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        AndroidMusic androidMusic = new AndroidMusic();

        LibgdxFirebase firebase = new LibgdxFirebase();

        initialize(new MyGdxGame(androidMusic, firebase), config);

    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri musicUri = data.getData();
                music = getRealPathFromUri(getApplicationContext(), musicUri);
                ready_music = true;
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                ready_fire = true;
            }
        }
    }

}
