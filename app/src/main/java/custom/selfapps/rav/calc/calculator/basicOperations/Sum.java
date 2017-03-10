package custom.selfapps.rav.calc.calculator.basicOperations;

import custom.selfapps.rav.calc.calculator.CalculatorOperations;


public class Sum implements CalculatorOperations {
    @Override
    public double calculate(double op1, double op2) {
        return op1 + op2;
    }
}
