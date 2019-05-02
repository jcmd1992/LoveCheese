package ie.JD.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import ie.JD.R;
import ie.JD.fragments.CheeseFragment;

public class CheeseFavourites extends Base {

    private TextView emptyList, cheeseName, rating, price ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheese_favourite);

        emptyList = findViewById(R.id.emptyList);
        cheeseName = findViewById(R.id.favouriteCheeseName);
        rating = findViewById(R.id.favouriteCheeseRating);
        price = findViewById(R.id.favouriteCheesePrice);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(app.cheeseList.isEmpty())
            emptyList.setText(getString(R.string.emptyMessageLbl2));
        else
            emptyList.setText("");

        CheeseFragment myFragment = CheeseFragment.newInstance(); //get a new Fragment instance
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, myFragment)
                .commit(); // add it to the current activity
    }
}


