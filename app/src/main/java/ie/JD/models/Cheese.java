package ie.JD.models;

import android.widget.EditText;

import java.io.Serializable;
import java.util.UUID;

public class Cheese implements Serializable
{
    public String cheeseId;
    public String cheeseName;
    public String origin;
    public double rating;
    public double price;
    public boolean favourite;


    public Cheese() {}

    public Cheese(String name, String origin, double rating, double price, boolean fav)
    {
        this.cheeseId = UUID.randomUUID().toString();
        this.cheeseName = name;
        this.origin = origin;
        this.rating = rating;
        this.price = price;
        this.favourite = fav;
    }

    public static void Cheese(Cheese cheese) {
    }

    @Override
    public String toString() {
        return cheeseId + " " + cheeseName + ", " + origin + ", " + rating
                + ", " + price + ", fav =" + favourite;
    }
}