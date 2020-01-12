import org.junit.Test;
import static org.junit.Assert.*;

public class PostfixExpressionTest {
    @Test
    public void testEvaluate1() {
        assertEquals(5, PostfixExpression.evaluate("3 2 +"));
    }

    @Test
    public void testEvaluate2() {
        assertEquals(6, PostfixExpression.evaluate("3 2 *"));
    }

    @Test
    public void testEvaluate3() {
        assertEquals(8, PostfixExpression.evaluate("15 7 -"));
    }

    @Test
    public void testEvaluate4() {
        assertEquals(2, PostfixExpression.evaluate("20 10 /"));
    }

    @Test
    public void testEvaluate5() {
        assertEquals(7, PostfixExpression.evaluate("6 2 1 - +"));
    }

    @Test
    public void testEvaluate6() {
        assertEquals(48, PostfixExpression.evaluate("6 2 1 - + 4 - 3 / 6 8 * *"));
    }

    @Test
    public void testEvaluate7() {
        assertEquals(3, PostfixExpression.evaluate("4 8 + 6 5 - * 3 2 - 2 2 + * /"));
    }

    @Test
    public void testEvaluate8() {
        assertEquals(1, PostfixExpression.evaluate("1 1 1 4 - / +"));

    }
}