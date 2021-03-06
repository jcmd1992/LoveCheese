package ie.JD.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.Random;

import ie.JD.R;
import ie.JD.activities.Base;

import ie.JD.activities.CheeseFavourites;
import ie.JD.activities.Edit;
import ie.JD.adapters.CheeseFilter;
import ie.JD.adapters.CheeseListAdapter;
import ie.JD.models.Cheese;

public class CheeseFragment extends ListFragment implements View.OnClickListener,
        AbsListView.MultiChoiceModeListener
{
    public Base activity;
    public static CheeseListAdapter listAdapter;
    public ListView listView;
    public CheeseFilter cheeseFilter;

    public CheeseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Bundle activityInfo = new Bundle(); // Creates a new Bundle object
        activityInfo.putString("cheeseId", (String) v.getTag());
        Intent goEdit = new Intent(getActivity(), Edit.class); // Creates a new Intent
        /* Add the bundle to the intent here */
        goEdit.putExtras(activityInfo);
        getActivity().startActivity(goEdit); // Launch the Intent
    }

    public static CheeseFragment newInstance() {
        CheeseFragment myfragment = new CheeseFragment();
        return myfragment;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.activity = (Base) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        listAdapter = new CheeseListAdapter(activity, this, activity.app.cheeseList);
        cheeseFilter = new CheeseFilter(activity.app.cheeseList,"all",listAdapter);

        if (getActivity() instanceof CheeseFavourites) {
            cheeseFilter.setFilter("favourites"); // Set the filter text field from 'all' to 'favourites'
            cheeseFilter.filter(null); // Filter the data, but don't use any prefix
            listAdapter.notifyDataSetChanged(); // Update the adapter
        }
        setListAdapter (listAdapter);

        //    setRandomCheese();
        checkEmptyList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);

        listView = v.findViewById(android.R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);

        return v;
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onClick(View view)
    {
        if (view.getTag() instanceof Cheese)
        {
            onCheeseDelete ((Cheese) view.getTag());
        }
    }

    public void onCheeseDelete(final Cheese cheese)
    {
        String stringName = cheese.cheeseName;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure you want to Delete the \'Cheese\' " + stringName + "?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {

                activity.app.cheeseList.remove(cheese); // remove from our list
                listAdapter.cheeseList.remove(cheese); // update adapters data
                DatabaseReference ToDelete = FirebaseDatabase.getInstance().getReference().child("Cheese");
                DatabaseReference cheeseToDelete = ToDelete.child(cheese.cheeseId);
                //    setRandomCheese();
                listAdapter.notifyDataSetChanged(); // refresh adapter
                cheeseToDelete.removeValue();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /* ************ MultiChoiceModeListener methods (begin) *********** */
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu)
    {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.delete_list_context, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.menu_item_delete_cheese:
                deleteCheese(actionMode);
                return true;
            default:
                return false;
        }
    }

    private void deleteCheese(ActionMode actionMode)
    {
        for (int i = listAdapter.getCount() - 1; i >= 0; i--)
        {
            if (listView.isItemChecked(i))
            {
                activity.app.cheeseList.remove(listAdapter.getItem(i));
            }
        }
        //     setRandomCheese();
        listAdapter.notifyDataSetChanged(); // refresh adapter
        checkEmptyList();
        actionMode.finish();
    }

    public void checkEmptyList()
    {
        TextView recentList = getActivity().findViewById(R.id.emptyList);

        if(activity.app.cheeseList.isEmpty())
            recentList.setText(getString(R.string.emptyMessageLbl2));
        else
            recentList.setText("");
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode)
    {}


    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked)
    {}
    /* ************ MultiChoiceModeListener methods (end) *********** */
}