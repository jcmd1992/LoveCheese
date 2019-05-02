package ie.JD.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ie.JD.R;
import ie.JD.models.Cheese;

public class Add extends Base {

    private String 		cheeseName, cheeseOrigin;
    private double 		cheesePrice, ratingValue;
    private EditText name, origin, price;
    private RatingBar ratingBar;
    DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        name = findViewById(R.id.addNameET);
        origin =  findViewById(R.id.addOriginET);
        price =  findViewById(R.id.editPriceET);
        ratingBar =  findViewById(R.id.addRatingBar);

        FirebaseApp.initializeApp(this);

        //getting the reference of issue node
        myDatabase = FirebaseDatabase.getInstance().getReference("Cheese");

    }


    public void addCheese(View v) {

        cheeseName = name.getText().toString();
        cheeseOrigin = origin.getText().toString();
        ratingValue = ratingBar.getRating();
        String cheesePriceString = price.getText().toString();
        cheesePrice = Double.parseDouble(cheesePriceString);
        boolean favourite = false;
        if ((cheeseName.length() > 0) && (origin.length() > 0))
        {
            if (ratingValue>=4){favourite =true;}
            Cheese c = new Cheese(cheeseName, cheeseOrigin, ratingValue, cheesePrice,favourite);

            myDatabase.child(c.cheeseId).setValue(c);
            app.cheeseList.add(c);

            startActivity(new Intent(this, Home.class));
        } else
            Toast.makeText(
                    this,
                    "You must Enter Something for "
                            + "\'Name\', \'Origin\' and \'Price\'",
                    Toast.LENGTH_SHORT).show();
    }
}

