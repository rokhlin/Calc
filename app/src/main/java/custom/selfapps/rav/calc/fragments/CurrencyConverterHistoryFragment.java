package custom.selfapps.rav.calc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import custom.selfapps.rav.calc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrencyConverterHistoryFragment extends Fragment {


    public CurrencyConverterHistoryFragment() {
        // Required empty public constructor
    }
    public static CurrencyConverterHistoryFragment newInstance() {
        return new CurrencyConverterHistoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_currency_converter_history, container, false);
    }

}
