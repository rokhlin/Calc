package custom.selfapps.rav.calc.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import custom.selfapps.rav.calc.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BasicCalcHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasicCalcHistoryFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MAIN_FUNCTION = "basic_calc";
    private static final int ARG_FRAGMENT_VALUE = 1;


    private String mFunction;
    private int mValue;


    public BasicCalcHistoryFragment() {
        // Required empty public constructor
    }

    public static BasicCalcHistoryFragment newInstance(String param1, int param2) {
        BasicCalcHistoryFragment fragment = new BasicCalcHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MAIN_FUNCTION, param1);
        args.putInt(String.valueOf(ARG_FRAGMENT_VALUE), param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFunction = getArguments().getString(ARG_MAIN_FUNCTION);
            mValue = getArguments().getInt(String.valueOf(ARG_FRAGMENT_VALUE));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basic_calc_history, container, false);
    }

}
