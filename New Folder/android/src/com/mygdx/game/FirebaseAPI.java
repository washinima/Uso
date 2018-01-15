package com.mygdx.game;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.Inet4Address;

/**
 * Created by Whisp on 14/01/2018.
 */

public class FirebaseAPI
{
    DatabaseReference database;

    int scoreAntigo;

    FirebaseAuth mAuth;

    public FirebaseAPI(FirebaseAuth mAuth)
    {
        scoreAntigo = 0;
        this.mAuth = mAuth;
        database = FirebaseDatabase.getInstance().getReference("cm-uso");
    }

    public void addUserSetScore()
    {
        int score = 0;
        database.child(
                Integer.toString(
                    mAuth.getCurrentUser().getEmail().hashCode()
                )
        ).setValue(score);
    }

    public boolean isOnline()
    {
        if( mAuth.getCurrentUser() != null )
        {
            return true;
        }
        return false;
    }


    public void updateScore(int score)
    {
        String hash = Integer.toString(
                mAuth.getCurrentUser().getEmail().hashCode()
        );


        database.child(hash).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                scoreAntigo = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        int novoScore = scoreAntigo + score;
        database.child(hash).setValue(novoScore);
    }
}
