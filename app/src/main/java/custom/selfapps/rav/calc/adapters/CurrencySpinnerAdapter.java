package custom.selfapps.rav.calc.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import custom.selfapps.rav.calc.R;


public class CurrencySpinnerAdapter extends ArrayAdapter<String>  {
    private String[] data;
    private String ignoreCurrency;
    private Context context;
    private LayoutInflater inflater;

    public CurrencySpinnerAdapter(Context context, LayoutInflater layoutInflater, int textViewResourceId,
                                  String[] objects, String ignoreCurrency) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.data = objects;
        this.ignoreCurrency = ignoreCurrency;
        inflater = layoutInflater;
    }



    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.row, parent, false);
        TextView label = (TextView) row.findViewById(R.id.textView_currency_name);
        TextView description = (TextView) row.findViewById(R.id.textView_currency_description);

        //if(data[position].equals(ignoreCurrency)) row.setVisibility(View.GONE);
        label.setText(data[position]);
        description.setText(getDescription(data[position]));
        return row;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_closed_state, parent, false);
        TextView label = (TextView) row.findViewById(R.id.textView_currency_name);
        label.setText(data[position]);
        return row;
    }


    /**
     *
     * @param shortName
     * @return description text from Strings.xml in depend of localization settings
     */
    private String getDescription(String shortName){
        Resources res = context.getResources();
        String pkg = context.getPackageName();
        int id = res.getIdentifier(shortName, "string", pkg);
        String result = null;
        try {
            result = res.getString(id);
        } catch (Resources.NotFoundException e) {
            result = "";
        }
        return result;
    }

    public void setIgnoreCurrency(String ignoreCurrency) {
        this.ignoreCurrency = ignoreCurrency;
    }
}