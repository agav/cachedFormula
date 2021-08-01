package formula;

import formula.tokens.ClosedParenthesis;
import formula.tokens.OpenParenthesis;
import formula.tokens.Operand;
import formula.tokens.Token;
import formula.tokens.Value;
import formula.tokens.Variable;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class InfixFormula {
    public final List<Token> tokens;

    public InfixFormula(List<Token> tokens) {
        this.tokens = tokens;
    }

    public PostfixFormula toPostfix() {

        Stack<Token> stack = new Stack<>();
        Token sentinel = new Token() {
        };
        stack.push(sentinel);

        List<Token> postfix = new LinkedList<>();

        for (Token token : tokens) {
            if (token instanceof Variable || token instanceof Value)
                postfix.add(token);
            else if (token instanceof OpenParenthesis)
                stack.push(token);
            else if (token instanceof ClosedParenthesis) {
                while (stack.peek() != sentinel && !(stack.peek() instanceof OpenParenthesis)) {
                    postfix.add(stack.pop());
                }
                stack.pop();
            } else if (token instanceof Operand) {

                if (precedence(token) > precedence(stack.peek()))
                    stack.push(token);
                else {
                    while (stack.peek() != sentinel && precedence(token) <= precedence(stack.peek())) {
                        postfix.add(stack.pop());
                    }
                    stack.push(token);
                }
            }
        }

        while (stack.peek() != sentinel) {
            postfix.add(stack.pop());
        }

        return new PostfixFormula(postfix);
    }

    private int precedence(Token token) {
        if (token instanceof Operand) {
            int operand = ((Operand) token).operand;
            if (operand == '+' || operand == '-') {
                return 1;
            } else if (operand == '*' || operand == '/') {
                return 2;
            } else {
                return 0;
            }
        }
        return 0;
    }
}
