package custom.selfapps.rav.calc.fragments;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

import custom.selfapps.rav.calc.MainActivity;
import custom.selfapps.rav.calc.R;
import custom.selfapps.rav.calc.adapters.CurrencySpinnerAdapter;
import custom.selfapps.rav.calc.adapters.ResultsListAdapter;
import custom.selfapps.rav.calc.currency.model.Currency;
import custom.selfapps.rav.calc.utils.Logs;

import static custom.selfapps.rav.calc.utils.Animator.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrencyConverterFragment extends Fragment implements View.OnClickListener {
    private View v;
    private EditText input_From, input_To , focused;
    private TextView recommendsExchange1,recommendsExchange2, recommendsExchange3;
   // private GridView list_results;
    private Spinner spinner_from ,spinner_to;
    private CurrencySpinnerAdapter spinnerAdapter_from, spinnerAdapter_to;
    private String[] currenciesData;
    private ResultsListAdapter listViewAdapter;
    private String selectedFromValue = "ILS";
    private String spinnerToValue = "USD";



    public CurrencyConverterFragment() {

    }


    public static CurrencyConverterFragment newInstance() {
        return new CurrencyConverterFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_currency_converter, container, false);

        final LinearLayout ll = (LinearLayout) v.findViewById(R.id.ll_show);
        final LinearLayout lKeys = (LinearLayout) v.findViewById(R.id.keys_layout);

        spinner_from = (Spinner) v.findViewById(R.id.spinner_currencies_from);
        spinner_to = (Spinner) v.findViewById(R.id.spinner_currencies_to);

        recommendsExchange1 = (TextView) v.findViewById(R.id.tw_recommended_currency1);
        recommendsExchange2 = (TextView) v.findViewById(R.id.tw_recommended_currency2);
        recommendsExchange3 = (TextView) v.findViewById(R.id.tw_recommended_currency3);
        // list_results = (GridView) v.findViewById(R.id.listView_results);

        initInputField();//EditText

        //NumKeys listeners
        v.findViewById(R.id.button_zero).setOnClickListener(this);
        v.findViewById(R.id.button_1).setOnClickListener(this);
        v.findViewById(R.id.button_2).setOnClickListener(this);
        v.findViewById(R.id.button_3).setOnClickListener(this);
        v.findViewById(R.id.button_4).setOnClickListener(this);
        v.findViewById(R.id.button_5).setOnClickListener(this);
        v.findViewById(R.id.button_6).setOnClickListener(this);
        v.findViewById(R.id.button_7).setOnClickListener(this);
        v.findViewById(R.id.button_8).setOnClickListener(this);
        v.findViewById(R.id.button_9).setOnClickListener(this);
        v.findViewById(R.id.button_return).setOnClickListener(this);
        v.findViewById(R.id.button_clear).setOnClickListener(this);
        v.findViewById(R.id.button_point).setOnClickListener(this);

        //Keyboard visibility listeners
        v.findViewById(R.id.button_hide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateShowHide(lKeys,View.GONE);
                animateShowHide(ll,View.VISIBLE);
            }
        });
        v.findViewById(R.id.button_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateShowHide(lKeys,View.VISIBLE);
                animateShowHide(ll,View.GONE);
            }
        });
        return v;
    }



    /**
     * Fill fragment spinner_from with data that were got in Activity
     * @param savedInstanceState - previous values instantiation
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //TODO Create another way to get actual information using SharedPreferences and Bundle
        while (((MainActivity)getActivity()).getCurrency() == null){}

        currenciesData = ((MainActivity)getActivity()).getCurrency().getCurrencyNames();
        initSpinners(savedInstanceState);
        initResultsList();

    }

    /**
     * Initialisation of the EditText input field
     * keyboard type - number
     */
    private void initInputField() {
        input_To = (EditText) v.findViewById(R.id.editText_input_to);
        input_From = (EditText) v.findViewById(R.id.editText_input_from);
        focused = input_From;


        input_From.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("") && input_From.equals(focused)) updateResults(input_To, spinner_to.getSelectedItem().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input_To.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("")&& input_To.equals(focused)) updateResults(input_From, spinner_from.getSelectedItem().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input_To.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus){
                    selectedFromValue = spinner_to.getSelectedItem().toString();
                    spinnerToValue = spinner_from.getSelectedItem().toString();
                    focused = input_To;
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(input_To.getWindowToken(), 0);
                }
            }
        });

        input_From.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus){
                    selectedFromValue = spinner_from.getSelectedItem().toString();
                    spinnerToValue = spinner_to.getSelectedItem().toString();
                    focused = input_From;
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(input_From.getWindowToken(), 0);
                }
            }
        });
    }

    /**
     * Initialisation of the GridView with converted currencies results
     * listViewAdapter - custom ArrayAdapter
     * list_results - GridView
     */
    private void initResultsList() {

        listViewAdapter = new ResultsListAdapter(getContext(),R.layout.single_item, getConvertedData(spinner_from.getSelectedItem().toString(), 1));
       // list_results.setAdapter(listViewAdapter);
    }

    /**
     * Initialisation of the Spinner with exchangeable variants
     * spinnerAdapter - custom ArrayAdapter
     * spinner_from - Spinner
     * onSelect another value will update result in the list_results
     *
     */
    private void initSpinners(Bundle savedInstanceState) {
        //Spinner From on the left side of the screen
        spinnerAdapter_from = new CurrencySpinnerAdapter(getContext(), getLayoutInflater(savedInstanceState), R.layout.row, currenciesData,spinnerToValue);
        spinnerAdapter_from.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_from.setAdapter(spinnerAdapter_from);
        spinner_from.setSelection(spinnerAdapter_from.getPosition(selectedFromValue));
        spinner_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String temp = selectedFromValue;
                selectedFromValue = currenciesData[position];

                if(spinnerToValue.equals(selectedFromValue)){
                    spinnerToValue = temp;
                    spinner_to.setSelection(spinnerAdapter_to.getPosition(spinnerToValue));
                }


                spinner_from.setSelection(spinnerAdapter_from.getPosition(selectedFromValue));
                updateResults(input_To, selectedFromValue);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                listViewAdapter.notifyDataSetChanged();
            }
        });

        //Spinner To on the right side of the screen
        spinnerAdapter_to = new CurrencySpinnerAdapter(getContext(), getLayoutInflater(savedInstanceState), R.layout.row, currenciesData, selectedFromValue);
        spinnerAdapter_to.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_to.setAdapter(spinnerAdapter_to);
        spinner_to.setSelection(spinnerAdapter_to.getPosition(spinnerToValue));
        spinner_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String temp = spinnerToValue;

                spinnerToValue = currenciesData[position];

                if(selectedFromValue.equals(spinnerToValue)){
                    selectedFromValue = temp;
                    spinner_from.setSelection(spinnerAdapter_from.getPosition(selectedFromValue));
                }


                spinner_to.setSelection(spinnerAdapter_to.getPosition(spinnerToValue));

                updateResults(input_To, spinnerToValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                listViewAdapter.notifyDataSetChanged();
            }
        });
    }



    /**
     * Update converted results in adapter
     * @param newCurrency - value of the selected currency in the Spinner
     */
    private void updateResults(EditText updatableEditText, String newCurrency) {

//        listViewAdapter.updateData(getConvertedData(newCurrency, Double.parseDouble(getScreenValue())));
//        listViewAdapter.notifyDataSetChanged();

        Currency currency = ((MainActivity)getActivity()).getCurrency();//TODO change get currency strategy
        double res = currency.getRelation(Double.parseDouble(getScreenValue()), selectedFromValue, spinnerToValue);
        updatableEditText.setText(String.format(Locale.getDefault(),"%.2f", res));
    }

    /**
     *  Calculate exchanging result with getting values
     * @param from - currency name from witch need to convert
     * @param value - how many money need to convert
     * @return Array of the converted values in a string representation
     */
    private String[] getConvertedData(String from, double value) {
        String [] res = new String[currenciesData.length-1];
        Currency currency = ((MainActivity)getActivity()).getCurrency();
        int shift = 0;
        for (int i = 0; i < currenciesData.length; i++) {
            if(from.equals(currenciesData[i])) shift++;
            else res[i-shift] = String.format("%.2f", currency.getRelation(value,from, currenciesData[i])) + " " + currenciesData[i];
        }//value + " " + from + " = "+
        return res;
    }

    @Override
    public void onClick(View v) {
        String tag = v.toString();
        Logs.send(getContext(), "tag " + tag);
        tag = tag.substring(tag.indexOf("app:id/") + 7, tag.length() - 1);
        Logs.send(getContext(), "Pressed " + tag);

        switch (v.getId()) {
            case R.id.button_1:
                screenAppend(1);
                break;
            case R.id.button_2:
                screenAppend(2);
                break;
            case R.id.button_3:
                screenAppend(3);
                break;
            case R.id.button_4:
                screenAppend(4);
                break;
            case R.id.button_5:
                screenAppend(5);
                break;
            case R.id.button_6:
                screenAppend(6);
                break;
            case R.id.button_7:
                screenAppend(7);
                break;
            case R.id.button_8:
                screenAppend(8);
                break;
            case R.id.button_9:
                screenAppend(9);
                break;
            case R.id.button_zero:
                screenAppend(0);
                break;
            case R.id.button_point:
                screenAddPoint(getScreenValue());
                break;
            case R.id.button_clear:
                clearScreen();
                break;
            case R.id.button_return:
                screenRemoveLast(getScreenValue());
                break;
        }
    }

    /**
     *
     * @return String value from Input field
     */
    private String getScreenValue() {
        String str = getFocusedView().getText().toString().trim();

        if(str.equals("NaN")) {
            str = "0";
        }
        else if(str.equals("")) str = "0";
        return str;
    }

    /**
     * Append input field value with point entered from custom keyboard for using of the double value
     * IMPORTANT!!!!! It doesn't work with standard keyboards
     */
    private void screenAddPoint(String value) {
        String number = value;
        if(number.equals("0")) number = "0.";
        else if(!number.contains(".")) number += ".";
        focused.setText(number);
    }

    /**
     * Append input field value with value from custom keyboard
     * IMPORTANT!!!!! It doesn't work with standard keyboards
     * @param i -resource id value of the key
     */
    private void screenAppend(int i) {
        String number =  getScreenValue();

        EditText focusedView = getFocusedView();
        if(number.equals("0") || number.equals("NaN")) focusedView.setText(i + "");
        else  focusedView.setText(number + i);
    }


    private void clearScreen() {
        focused.setText("0");
    }

    private void screenRemoveLast(String screenValue) {
        String number =  screenValue;

        if(number.equals("NaN")) clearScreen();
        if(number.equals("0")) return;
        if(number.length() == 1) number = "0";
        else number = number.substring(0,number.length()-1);

        if(number.endsWith(".")) screenRemoveLast(number);
        focused.setText(number);
    }



    private EditText getFocusedView() {
        return focused;
    }
}
