package com.mygdx.game;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseLogin extends Activity {


    FirebaseAPI api;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextPasswordRepeat;
    Button buttonLogin;
    CheckBox checkBoxRegister;
    TextView textEmail;
    TextView textPassword;
    TextView textConfirm;

    private FirebaseAuth mAuth;
    private String TAG = "TestApp";
    private String LOGINT = "LOGIN";
    private String REGISTERT = "CREATE MY ACCOUNT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base_login);

        textEmail = findViewById(R.id.TextEmail);
        editTextEmail = findViewById(R.id.editTextEmail);
        textPassword = findViewById(R.id.TextPassword);
        editTextPassword        = findViewById(R.id.editTextPassword      );
        textConfirm             = findViewById(R.id.TextConfirm           );
        editTextPasswordRepeat  = findViewById(R.id.editTextPasswordRepeat);
        checkBoxRegister        = findViewById(R.id.checkBoxRegister      );
        buttonLogin             = findViewById(R.id.buttonLogin           );

        editTextPasswordRepeat.setVisibility(View.GONE);
        textConfirm.setVisibility(View.GONE);

        checkBoxRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxRegister.isChecked()){
                    editTextPasswordRepeat.setVisibility(View.VISIBLE);
                    textConfirm.setVisibility(View.VISIBLE);
                    buttonLogin.setText(REGISTERT);
                }else{
                    editTextPasswordRepeat.setVisibility(View.GONE);
                    textConfirm.setVisibility(View.GONE);
                    buttonLogin.setText(LOGINT);
                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attempLogin();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        api = new FirebaseAPI(mAuth);
    }

    void attempLogin(){
        if (checkBoxRegister.isChecked()){

            if (editTextPassword.getText().toString().compareTo(editTextPasswordRepeat.getText().toString())!=0){
                Toast.makeText(this, "Passwords do not match!",Toast.LENGTH_LONG).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                api.addUserSetScore();

                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(FireBaseLogin.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        }else{
            mAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                Toast.makeText(FireBaseLogin.this, "Authentication Success.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(FireBaseLogin.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        }
    }
}
