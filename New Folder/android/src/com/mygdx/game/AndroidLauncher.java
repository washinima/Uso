package com.mygdx.game;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.media.*;
import android.provider.MediaStore;
import android.util.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.FileHandler;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.audio.AudioRecorder;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.firebase.auth.FirebaseAuth;

import be.tarsos.dsp.io.android.AndroidFFMPEGLocator;


public class AndroidLauncher extends AndroidApplication {

    String music;
    boolean ready;

    public class AndroidMusic implements MusicInterface {
        private final MusicAPI api;

        public AndroidMusic()
        {
            api = new MusicAPI();
            ready = false;
            music = null;
        }

        public ArrayList<Hittable> SendMap()
        {
            return api.list;
        }

        public void SetupRun() {
            api.Setup(getApplicationContext(), music);
            api.Start();

            ready = false;
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

        public boolean isReady() {return ready;}

        public String musicPath() {return music;}

        public void showPicker()
        {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 1);
        }
    }

    public class LibgdxFirebase implements FirebaseInterface
    {
        private FirebaseAuth mAuth;

        public LibgdxFirebase()
        {
            mAuth = FirebaseAuth.getInstance();
        }

        //public void
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        AndroidMusic androidMusic = new AndroidMusic();

        initialize(new MyGdxGame(androidMusic), config);

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
                ready = true;
            }
        }
    }

}
