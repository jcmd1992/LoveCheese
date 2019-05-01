package ie.JD.main;


import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.util.Log;

import ie.JD.models.Cheese;

public class CheeseApp extends Application
{
    public List<Cheese> cheeseList = new ArrayList<>();

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("Cheese", "Cheese App Started");
    }
}