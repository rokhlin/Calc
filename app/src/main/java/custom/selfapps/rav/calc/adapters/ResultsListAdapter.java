package custom.selfapps.rav.calc.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class ResultsListAdapter extends ArrayAdapter<String> {
    private String [] data;
    private Context context;
    private int textViewResourceId;


    public ResultsListAdapter(Context context, int resource, String[] objects ) {
        super(context, resource, objects);
        data = objects;
        this.context = context;
        textViewResourceId = resource;
    }

    /**
     * Updating data with new values
     * @param objects - converted values
     */
    public void updateData(String[] objects){
        data = objects;
    }

    /**
     * Load Custom container with actual values
     * @param position of the item
     * @param convertView - item container (TextView)
     * @param parent - ViewGroup
     * @return - item container (TextView)
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        if (convertView == null)
            convertView = View.inflate(context, textViewResourceId, null);
        TextView tv = (TextView) convertView;
        tv.setText(data[position]);
        return tv;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return data[position];
    }
}
