package formula;

import formula.tokens.Operand;
import formula.tokens.Token;
import formula.tokens.Value;
import formula.tokens.Variable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class PostfixFormula {
    public final List<Token> tokens;

    public PostfixFormula(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Double calculate() {
        return calculate(Map.of());
    }

    public Double calculate(Map<String, Double> map) {

        Stack<Value> stack = new Stack<>();
        List<Token> tokens = new LinkedList<>(this.tokens);

        while (!tokens.isEmpty()) {
            Token token = tokens.get(0);
            tokens.remove(0);

            if (token instanceof Variable) {
                Value value = new Value(map.get(((Variable) token).variable));
                stack.push(value);
            } else if (token instanceof Value) {
                stack.push((Value) token);

            } else {
                Value valueRight = stack.pop();
                Value valueLeft = stack.pop();
                Value result = ((Operand) (token)).calculate(valueLeft, valueRight);
                stack.push(result);
            }
        }
        return stack.peek().value;
    }

}
