package formula.tokens;

public class Value implements formula.tokens.Token {
    public final Double value;

    public Value(Double value) {

        this.value = value;
    }
}
