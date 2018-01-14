package com.mygdx.game;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScoresActivity extends Activity {

    ListView listView;
    List<UserScore> userScores=new ArrayList<>();
    ScoresAdapter scoresAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        listView=findViewById(R.id.listViewScores);
        scoresAdapter=new ScoresAdapter();
        listView.setAdapter(scoresAdapter);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("scores");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datadaSnapshot) {
                userScores.clear();
                for (DataSnapshot d: datadaSnapshot.getChildren()){
                    UserScore userScore=d.child("user_score").getValue(UserScore.class);
                    userScores.add(userScore);
                    scoresAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    class ScoresAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return userScores.size();
        }

        @Override
        public Object getItem(int position) {
            return userScores.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v= getLayoutInflater().inflate(R.layout.row_score,null);

            TextView textViewEmail = v.findViewById(R.id.textViewEmail);
            TextView textViewScore = v.findViewById(R.id.textViewScore);
            textViewEmail.setText(userScores.get(position).getUserName());
            textViewScore.setText(""+userScores.get(position).getUserScore()+" Points");
            return v;
        }
    }
}
