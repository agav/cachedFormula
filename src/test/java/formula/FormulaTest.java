package formula;

import org.junit.jupiter.api.Test;


import java.util.Map;

import static java.util.Map.*;
import static org.assertj.core.api.Assertions.assertThat;

public class FormulaTest {

    public static final Tokenizer TOKENIZER = new Tokenizer();

    @Test
    public void variaousTests() {

        PostfixFormula formula = TOKENIZER.parse("5+6");
        assertThat(formula.calculate()).isEqualTo(11.0);


        formula = TOKENIZER.parse("5*6+8/4");
        assertThat(formula.calculate()).isEqualTo(32.0);

        formula = TOKENIZER.parse("a*a-a*a");
        assertThat(formula.calculate(of("a", 1.0))).isEqualTo(0.0);


        formula = TOKENIZER.parse("variable1/variable2-(131-variable2*10)");
        assertThat(formula.calculate(of(
                "variable1", 26.0,
                "variable2", 13.0
        ))).isEqualTo(1.0);




    }

}