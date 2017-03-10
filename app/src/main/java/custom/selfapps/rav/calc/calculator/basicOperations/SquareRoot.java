package custom.selfapps.rav.calc.calculator.basicOperations;

import custom.selfapps.rav.calc.calculator.CalculatorOperations;


public class SquareRoot implements CalculatorOperations {
    @Override
    public double calculate(double op1, double op2) {
        return Math.sqrt(op1);
    }
}