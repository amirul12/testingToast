package app.itech.com.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.util.HashMap;
import java.util.List;

public class MyArrayAdapter extends ArrayAdapter {

    HashMap<String, Integer> map = new HashMap<>();


    public MyArrayAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);

         for (int i = 0; i< objects.size(); i ++){
             map.put(objects.get(i), i);
         }


    }

    @Override
    public long getItemId(int position) {
        String item = (String) getItem(position);
        return map.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
