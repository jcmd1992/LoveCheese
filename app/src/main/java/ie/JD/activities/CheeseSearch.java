package ie.JD.activities;

import android.os.Bundle;

import ie.JD.R;
import ie.JD.fragments.CheeseSearchFragment;

public class CheeseSearch extends Base  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cheesesearch);
    }

    @Override
    protected void onResume() {
        super.onResume();

        cheeseFragment = CheeseSearchFragment.newInstance(); //get a new Fragment instance
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, cheeseFragment)
                .commit(); // add it to the current activity
    }
}

