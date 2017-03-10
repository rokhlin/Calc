package custom.selfapps.rav.calc.calculator;

import java.util.HashMap;


public class BasicCalc {

    private double op1;
    private double op2;
    private boolean waitingForNextOperator = false;
    private CalculatorOperations operations;



    public boolean isWaitingForNextOperator() {
        return waitingForNextOperator;
    }

    public void setWaitingForNextOperator(boolean waitingForNextOperator) {
        this.waitingForNextOperator = waitingForNextOperator;
    }
    public String operate(){
         Double result;
         try {
             result = operations.calculate(op1,op2);
             waitingForNextOperator = false;
         } catch (ArithmeticException e) {
             return e.getMessage();
         }

            return ((result - result.intValue()) != 0.0  ? result : result.intValue()) + ""; // TODO check returning value
    }

    public double getOp1() {
        return op1;
    }

    public void setOp1(double op1) {
        this.op1 = op1;
        waitingForNextOperator = true;
    }

    public double getOp2() {
        return op2;
    }

    public void setOp2(double op2) {
        this.op2 = op2;
    }

    public CalculatorOperations getOperations() {
        return operations;
    }

    public void setOperations(CalculatorOperations operations) {
        this.operations = operations;
    }

    public void clearFields(){
        clearFieldsAndNext("0");
        waitingForNextOperator = false;
    }

    public void clearFieldsAndNext(String res){
        if(res != null) op1 = Double.parseDouble(res);
        op2 = 0;
        operations = null;
        waitingForNextOperator = true;
    }
}
