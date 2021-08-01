package formula;

import formula.tokens.ClosedParenthesis;
import formula.tokens.OpenParenthesis;
import formula.tokens.Operand;
import formula.tokens.Token;
import formula.tokens.Value;
import formula.tokens.Variable;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    public static final Pattern VARIABLE_PATTERN = Pattern.compile("^([A-Za-z][A-Z_a-z0-9]*).*");
    public static final Pattern VALUE_PATTERN = Pattern.compile("^((?:0|[1-9][0-9]*)\\.?[0-9]*).*");

    public PostfixFormula parse(String formula) {
        InfixFormula infixFormula = doParse(new LinkedList<>(), formula);
        return infixFormula.toPostfix();
    }

    private InfixFormula doParse(List<Token> accumulator, String formula) {

        if (formula.isEmpty()) {
            return new InfixFormula(accumulator);
        }

        if (formula.startsWith("(")) {
            accumulator.add(new OpenParenthesis());
            return doParse(accumulator, formula.substring(1));
        }
        if (formula.startsWith(")")) {
            accumulator.add(new ClosedParenthesis());
            return doParse(accumulator, formula.substring(1));
        }

        if (Set.of('+', '-', '*', '/').contains(formula.charAt(0))) {
            accumulator.add(new Operand(formula.charAt(0)));
            return doParse(accumulator, formula.substring(1));
        }

        Matcher matcher = VARIABLE_PATTERN.matcher(formula);
        if (matcher.matches()) {
            String variable = matcher.group(1);
            accumulator.add(new Variable(variable));
            return doParse(accumulator, formula.substring(variable.length()));
        }

        matcher = VALUE_PATTERN.matcher(formula);
        if (matcher.matches()) {
            String value = matcher.group(1);
            accumulator.add(new Value(Double.parseDouble(value)));
            return doParse(accumulator, formula.substring(value.length()));
        }

        throw new IllegalArgumentException("Failed to parse formula at pos: " + formula);
    }


}
