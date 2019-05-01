package ie.JD.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ie.JD.R;
import ie.JD.models.Cheese;

public class Edit extends Base {
    public Context context;
    public boolean isFavourite;
    public Cheese aCheese;
    public ImageView editFavourite;
    DatabaseReference myDatabase;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        context = this;
        activityInfo = getIntent().getExtras();
        aCheese = getCheeseObject(activityInfo.getString("cheeseId"));

        Log.v("LoveCheese", "EDIT : " + aCheese);

        ((TextView)findViewById(R.id.editTitleTV)).setText(aCheese.cheeseName);

        ((EditText)findViewById(R.id.editNameET)).setText(aCheese.cheeseName);
        ((TextView)findViewById(R.id.editOriginET)).setText(aCheese.origin);
        ((EditText)findViewById(R.id.editPriceET)).setText(""+aCheese.price);
        ((RatingBar) findViewById(R.id.editRatingBar)).setRating((float)aCheese.rating);

        FirebaseApp.initializeApp(this);

        //getting the reference of issue node
        myDatabase = FirebaseDatabase.getInstance().getReference("Cheese");

        editFavourite = findViewById(R.id.editFavourite);

        if (aCheese.favourite == true) {
            editFavourite.setImageResource(R.drawable.favourites_72_on);
            isFavourite = true;
        } else {
            editFavourite.setImageResource(R.drawable.favourites_72);
            isFavourite = false;
        }
    }

    private Cheese getCheeseObject(String id) {

        for (Cheese c : cheeseList)
            if (c.cheeseId.equalsIgnoreCase(id))
                return c;

        return null;
    }

    public void saveCheese(View v) {

        String cheeseName = ((EditText) findViewById(R.id.editNameET)).getText().toString();
        String cheeseOrigin = ((EditText) findViewById(R.id.editOriginET)).getText().toString();
        String cheesePriceStr = ((EditText) findViewById(R.id.editPriceET)).getText().toString();
        double ratingValue =((RatingBar) findViewById(R.id.editRatingBar)).getRating();
        double cheesePrice;

        try {
            cheesePrice = Double.parseDouble(cheesePriceStr);
        } catch (NumberFormatException e) {
            cheesePrice = 0.0;
        }

        if ((cheeseName.length() > 0) && (cheeseOrigin.length() > 0) && (cheesePriceStr.length() > 0)) {
            aCheese.cheeseName = cheeseName;
            aCheese.origin = cheeseOrigin;
            aCheese.price = cheesePrice;
            aCheese.rating = ratingValue;

            startActivity(new Intent(this,Home.class));

        } else
            Toast.makeText(this, "You must Enter Something for Name and Origin",Toast.LENGTH_SHORT).show();
    }

    public void toggle(View view) {

        if (isFavourite) {
            aCheese.favourite = false;
            Toast.makeText(this,"Removed From Favourites",Toast.LENGTH_SHORT).show();
            isFavourite = false;
            editFavourite.setImageResource(R.drawable.favourites_72);
        } else {
            aCheese.favourite = true;
            Toast.makeText(this,"Added to Favourites !!",Toast.LENGTH_SHORT).show();
            isFavourite = true;
            editFavourite.setImageResource(R.drawable.favourites_72_on);
        }
    }
}
