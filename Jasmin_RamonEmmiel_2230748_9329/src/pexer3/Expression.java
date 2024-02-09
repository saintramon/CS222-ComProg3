package pexer3;

import java.io.Serializable;

public class Expression implements Serializable {
    private String operand1;
    private String operator;
    private String operand2;

    public Expression(String operand1, String operator, String operand2){
        this.operand1 = operand1;
        this.operator = operator;
        this.operand2 = operand2;
    }

    public String getOperand1() {
        return operand1;
    }

    public String getOperator() {
        return operator;
    }

    public String getOperand2() {
        return operand2;
    }

    @Override
    public String toString() {
        return operand1 + " " + operator + " " + operand2;
    }
}
