package ie.JD.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ie.JD.R;
import ie.JD.models.Cheese;

public class CheeseListAdapter extends ArrayAdapter<Cheese>
{
    private Context context;
    private View.OnClickListener deleteListener;
    public List<Cheese> cheeseList;

    public CheeseListAdapter(Context context, View.OnClickListener deleteListener, List<Cheese> cheeseList)
    {
        super(context, R.layout.cheeserow, cheeseList);

        this.context = context;
        this.deleteListener = deleteListener;
        this.cheeseList = cheeseList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheeseItem item = new CheeseItem(context, parent,
                deleteListener, cheeseList.get(position));
        return item.view;
    }

    @Override
    public int getCount() {
        return cheeseList.size();
    }

}
