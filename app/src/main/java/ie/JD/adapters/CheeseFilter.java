package ie.JD.adapters;

import android.util.Log;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import ie.JD.models.Cheese;

public class CheeseFilter extends Filter{
    public List<Cheese> originalCheeseList;
    public String filterText;
    public CheeseListAdapter adapter;

    public CheeseFilter(List<Cheese> originalCheeseList, String filterText,
                        CheeseListAdapter adapter) {
        super();
        this.originalCheeseList = originalCheeseList;
        this.filterText = filterText;
        this.adapter = adapter;
    }

    public void setFilter(String filterText) {
        this.filterText = filterText;
    }

    @Override
    protected Filter.FilterResults performFiltering(CharSequence prefix) {
        Filter.FilterResults results = new Filter.FilterResults();

        List<Cheese> newCheeses;
        String CheeseName;

        if (prefix == null || prefix.length() == 0) {
            newCheeses = new ArrayList<>();
            if (filterText.equals("all")) {
                results.values = originalCheeseList;
                results.count = originalCheeseList.size();
            } else {
                if (filterText.equals("favourites")) {
                    for (Cheese c : originalCheeseList)
                        if (c.favourite)
                            newCheeses.add(c);
                }
                results.values = newCheeses;
                results.count = newCheeses.size();
            }
        } else {
            String prefixString = prefix.toString().toLowerCase();
            newCheeses = new ArrayList<>();

            for (Cheese c : originalCheeseList) {
                CheeseName = c.cheeseName.toLowerCase();
                if (CheeseName.contains(prefixString)) {
                    if (filterText.equals("all")) {
                        newCheeses.add(c);
                    } else if (c.favourite) {
                        newCheeses.add(c);
                    }}}
            results.values = newCheeses;
            results.count = newCheeses.size();
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence prefix, Filter.FilterResults results) {

        adapter.cheeseList = (ArrayList<Cheese>) results.values;

        if (results.count >= 0)
            adapter.notifyDataSetChanged();
        else {
            adapter.notifyDataSetInvalidated();
            adapter.cheeseList = originalCheeseList;
        }
        Log.v("Cheese", "publishResults : " + adapter.cheeseList);
    }
}
