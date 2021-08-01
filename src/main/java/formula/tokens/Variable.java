package formula.tokens;

public class Variable implements formula.tokens.Token {
    public final String variable;

    public Variable(String variable) {
        this.variable = variable;
    }
}
