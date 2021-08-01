package formula.tokens;

import formula.tokens.Value;

public class Operand implements formula.tokens.Token {
    public final Character operand;

    public Operand(Character operand) {
        this.operand = operand;
    }

    public Value calculate(Value valueLeft, Value valueRight) {
        switch (operand) {
            case '-':
                return new Value(valueLeft.value - valueRight.value);
            case '+':
                return new Value(valueLeft.value + valueRight.value);
            case '/':
                return new Value(valueLeft.value / valueRight.value);
            case '*':
                return new Value(valueLeft.value * valueRight.value);
        }
        throw new UnsupportedOperationException("Operation not supported  " + operand);
    }
}
