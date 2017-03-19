package custom.selfapps.rav.calc.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import custom.selfapps.rav.calc.calculator.basicOperations.Division;
import custom.selfapps.rav.calc.calculator.basicOperations.Multiply;
import custom.selfapps.rav.calc.calculator.basicOperations.Subtract;
import custom.selfapps.rav.calc.calculator.basicOperations.Sum;
import custom.selfapps.rav.calc.R;
import custom.selfapps.rav.calc.calculator.BasicCalc;
import custom.selfapps.rav.calc.calculator.CalculatorOperations;
import custom.selfapps.rav.calc.utils.Logs;

import static custom.selfapps.rav.calc.utils.Animator.animateShowHide;

public class BasicCalcMainFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "BasicCalcMainFragment";
    public static final boolean logging = true;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MAIN_FUNCTION = "basic_calc";
    private static final int ARG_FRAGMENT_VALUE = 0;
    private BasicCalc calc = new BasicCalc();

    private TextView input;
    private TextView statistics;

    public BasicCalcMainFragment() {
        // Required empty public constructor
    }

    public static BasicCalcMainFragment newInstance() {
        return new BasicCalcMainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.basic_calc_main_fragment, container, false);

        input = (TextView) v.findViewById(R.id.textView_input);
        statistics =(TextView) v.findViewById(R.id.textView_statistics);
        input.setText("0");

        final LinearLayout ll = (LinearLayout) v.findViewById(R.id.ll_show);
        final LinearLayout lKeys = (LinearLayout) v.findViewById(R.id.keys_layout);


        //Keyboard visibility listeners
        v.findViewById(R.id.button_hide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateShowHide(lKeys,View.GONE);
                animateShowHide(input,View.GONE);
                animateShowHide(ll,View.VISIBLE);
            }
        });
        v.findViewById(R.id.button_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateShowHide(lKeys,View.VISIBLE);
                animateShowHide(input,View.VISIBLE);
                animateShowHide(ll,View.GONE);
            }
        });

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

        //clear listeners
        v.findViewById(R.id.button_clear).setOnClickListener(this);
        v.findViewById(R.id.button_return).setOnClickListener(this);

        //Number modification listeners
        v.findViewById(R.id.button_positive_negative).setOnClickListener(this);
        v.findViewById(R.id.button_point).setOnClickListener(this);
        v.findViewById(R.id.button_percent).setOnClickListener(this);
        v.findViewById(R.id.button_square_root).setOnClickListener(this);
        v.findViewById(R.id.button_power).setOnClickListener(this);

        //Operation listeners
        v.findViewById(R.id.button_equal).setOnClickListener(this);
        v.findViewById(R.id.button_sum).setOnClickListener(this);
        v.findViewById(R.id.button_subtract).setOnClickListener(this);
        v.findViewById(R.id.button_divide).setOnClickListener(this);
        v.findViewById(R.id.button_multiply).setOnClickListener(this);

        // fields for restoring preferences
        String screenText = null;
        String statisticsText = null;
        boolean waitingForNextOperator = false;
        CalculatorOperations calculatorOperations = null;
        double op1 = 0;

        //getting values from SharedPreferences
        if(savedInstanceState == null){ //TODO save and restore from bundle savedInstanceState

            SharedPreferences mPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            screenText = mPref.getString(getString(R.string.preferences_screen_value),"0");
            statisticsText = mPref.getString(getString(R.string.preferences_statistics_value),"");
            waitingForNextOperator = mPref.getBoolean(getString(R.string.preferences_calc_waitingForNextOperator_value), false);
            op1 = Double.parseDouble(mPref.getString(getString(R.string.preferences_calc_op1_value),"0"));

            String operations = mPref.getString(getString(R.string.preferences_calc_operation), null);
            if(operations != null){
                try {
                    Class<?> clazz = Class.forName(operations);
                    calculatorOperations = (CalculatorOperations) clazz.newInstance();
                } catch (Exception e) {
                   // e.printStackTrace();
                }
            }
            // set restored values
            input.setText(screenText);
            statistics.setText(statisticsText);
            calc.setOp1(op1);
            calc.setWaitingForNextOperator(waitingForNextOperator);
            calc.setOperations(calculatorOperations);
        }



        return v;
    }

    @Override
    public void onClick(View v) {
        if(logging){
            String tag = v.toString();
            tag = tag.substring(tag.indexOf("app:id/")+7,tag.length()-1);
            Log.d(TAG,"Pressed " + tag);
        }

        switch (v.getId()){
            case R.id.button_1: screenAppend(1);
                break;
            case R.id.button_2: screenAppend(2);
                break;
            case R.id.button_3: screenAppend(3);
                break;
            case R.id.button_4: screenAppend(4);
                break;
            case R.id.button_5: screenAppend(5);
                break;
            case R.id.button_6: screenAppend(6);
                break;
            case R.id.button_7: screenAppend(7);
                break;
            case R.id.button_8: screenAppend(8);
                break;
            case R.id.button_9: screenAppend(9);
                break;
            case R.id.button_zero: screenAppend(0);
                break;
            case R.id.button_point: screenAddPoint();
                break;
            case R.id.button_percent: operateWithPercent();
                break;
            case R.id.button_clear: clearScreen();
                break;
            case R.id.button_return:  screenRemoveLast();
                break;
            case R.id.button_sum:  calculateResult(new Sum(),"+");
                break;
            case R.id.button_subtract:  calculateResult(new Subtract(),"-");
                break;
            case R.id.button_multiply:  calculateResult(new Multiply(),"*");
                break;
            case R.id.button_divide:  calculateResult(new Division(),"/");
                break;
            case R.id.button_equal:   calculateResult();
                break;
            case R.id.button_square_root:   getSquareRoot();
                break;
            case R.id.button_power:   calculatePower();
                break;
            case R.id.button_positive_negative:   toggleValueSignificant();
                break;
        }
    }

    private void clearScreen() {
        calc.clearFields();
        input.setText("0");
    }

    private void calculatePower() {
        String value = getScreenValue();
        if(value.equals("0")) return;
        if(value.equals("NaN")) clearScreen();
        double doubleValue = Double.parseDouble(value);
        input.setText(Math.pow(doubleValue,2)+"");
    }

    private void toggleValueSignificant() {
        String value = getScreenValue();
        if(value.equals("0")) return;
        input.setText(value.contains("-") ? value.substring(1,value.length()):"-"+value);
    }

    private void operateWithPercent() {
        if (calc.isWaitingForNextOperator()) {
            calc.setOp2((calc.getOp1() / 100) * Double.parseDouble(getScreenValue()));
            calculateResult();
        }
        input.setText("0");
        calc.clearFields();
    }

    private void calculateResult(CalculatorOperations operation, String operationStr) {
        try{
        if (calc.isWaitingForNextOperator()) {
            String op2 = getScreenValue();
            calc.setOp2(Double.parseDouble(op2));
            String res = calc.operate();
            calc.clearFieldsAndNext(res);
            calc.setOp1(Double.parseDouble(res));
            calc.setOperations(operation);
            input.setText("0");
            statistics.append(op2 +" = "+ res + "\n"
                    + res + " " + operationStr +" ");
        }
        else selectOperation(operation,operationStr);
        }catch (NumberFormatException e){
        if(logging) Log.d(TAG,"Error: " + e.getMessage());
    }
    }

    private void calculateResult() {
        if (calc.isWaitingForNextOperator()){
            String op2 = getScreenValue();
            calc.setOp2(Double.parseDouble(op2));
            String res = calc.operate();
            statistics.append(op2 +" = "+ res + "\n");
        }
        input.setText("0");
        calc.clearFields();
    }

    private void selectOperation(CalculatorOperations operation, String operationStr) {
        String op1 = getScreenValue();

            calc.setOp1(Double.parseDouble(op1));
            calc.setOperations(operation);
            statistics.append(op1 + " " +operationStr +" ");


        input.setText("0");
    }

    private void screenRemoveLast() {
        String number =  getScreenValue();

        if(number.equals("NaN")) clearScreen();
        if(number.equals("0")) return;
        if(number.length() == 2 && String.valueOf(number.charAt(0)).equals("-")) number = "0";
        if(number.length() == 1) number = "0";
        else number = number.substring(0,number.length()-1);

        input.setText(number);
    }

    private void screenAddPoint() {
        String number =  getScreenValue();
        if(number.equals("0")) number = "0.";
        else if(!number.contains(".")) number += ".";

        input.setText(number);
    }

    private void screenAppend(int i) {
        String number =  getScreenValue();

        if(number.equals("0") || number.equals("NaN")) input.setText(i + "");
        else  input.setText(number + i);
    }

    public void getSquareRoot() {
        double value = Double.parseDouble(getScreenValue());
        input.setText(Double.toString(Math.sqrt(value)));
    }

    private String getScreenValue() {
        String str = input.getText().toString();
        if(str.equals("NaN")) {
            str = "0";
            input.setText(str);
        }
       return str;
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences mPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPref.edit();

        editor.putString(getString(R.string.preferences_screen_value),getScreenValue());
        editor.putString(getString(R.string.preferences_statistics_value), statistics.getText().toString());
        editor.putString(getString(R.string.preferences_calc_op1_value), String.valueOf(calc.getOp1()));
        editor.putString(getString(R.string.preferences_calc_operation), calc.getOperations() == null? "null" : calc.getOperations().getClass().getName());
        editor.putBoolean(getString(R.string.preferences_calc_waitingForNextOperator_value), calc.isWaitingForNextOperator());

        editor.apply();

    }
}
