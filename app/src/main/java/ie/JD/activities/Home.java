package ie.JD.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import ie.JD.R;
import ie.JD.fragments.CheeseFragment;
import ie.JD.main.CheeseApp;
import ie.JD.models.Cheese;

public class Home extends Base {


    DatabaseReference myDatabase;
    Base app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseApp.initializeApp(this);

        myDatabase = FirebaseDatabase.getInstance().getReference("Cheese");

        myDatabase.addValueEventListener(new ValueEventListener() {  @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


            app.cheeseList.clear();
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                Cheese cheeselist = (ds.getValue(Cheese.class));
                //adding cheese to the list
                Cheese.Cheese(cheeselist);
                app.cheeseList.add(cheeselist);
            }
        }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void add(View v) {
        startActivity(new Intent(this, Add.class));
    }

    @Override
    protected void onResume() {
        super.onResume();

        cheeseFragment = CheeseFragment.newInstance(); //get a new Fragment instance
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, cheeseFragment)
                .commit(); // add it to the current activity

    }
}
