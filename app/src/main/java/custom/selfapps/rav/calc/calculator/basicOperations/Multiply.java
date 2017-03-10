package custom.selfapps.rav.calc.calculator.basicOperations;

import custom.selfapps.rav.calc.calculator.CalculatorOperations;

/**
 * Created by user on 14.02.2017.
 */

public class Multiply implements CalculatorOperations {
    @Override
    public double calculate(double op1, double op2) {
        if(op1 == 0 || op2 == 0) throw new ArithmeticException("Multiplying by zero error!!!");
        return op1 * op2;
    }
}