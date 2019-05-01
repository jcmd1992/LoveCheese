package ie.JD.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import ie.JD.R;
import ie.JD.models.Cheese;

public class CheeseItem {
    View view;

    public CheeseItem(Context context, ViewGroup parent,
                      View.OnClickListener deleteListener, Cheese cheese)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.cheeserow, parent, false);
        view.setTag(cheese.cheeseId);

        updateControls(cheese);

        ImageView imgDelete = view.findViewById(R.id.rowDeleteImg);
        imgDelete.setTag(cheese);
        imgDelete.setOnClickListener(deleteListener);
    }

    private void updateControls(Cheese cheese) {
        ((TextView) view.findViewById(R.id.rowCheeseName)).setText(cheese.cheeseName);

        ((TextView) view.findViewById(R.id.rowCheeseOrigin)).setText(cheese.origin);
        ((TextView) view.findViewById(R.id.rowRating)).setText(cheese.rating + " *");
        ((TextView) view.findViewById(R.id.rowPrice)).setText("€" +
                new DecimalFormat("0.00").format(cheese.price));

        ImageView imgIcon = view.findViewById(R.id.rowFavouriteImg);

        if (cheese.favourite == true)
            imgIcon.setImageResource(R.drawable.favourites_72_on);
        else
            imgIcon.setImageResource(R.drawable.favourites_72);


    }
}