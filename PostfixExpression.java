/**
 * @author Charles Vidro
 * Assignment 2.
 * 
 * This class evaluates a postfix expression
 */

public class PostfixExpression {

    /**
     * Evaluate a postfix expression.
     * Postfix expression notation has operands first, following by the operations.
     * For example:
     *    13 5 *           is same as 13 * 5
     *    4 20 5 + * 6 -   is same as 4 * (20 + 5) - 6
     *
     * In this homework, expression in the argument only contains
     *     integer, +, -, *, / and a space between every number and operation.
     * You may assume the result will be integer as well.
     *
     * @param exp The postfix expression
     * @return the result of the expression
     */

    public static int evaluate(String exp) {
        /* TCJ
         * The runtime of this is dependent on the number, n, elements in the input expression.
         */
        String[] input = exp.split(" ");
        DLLStack<Integer> stack = new DLLStack<>();
        PostfixExpression self = new PostfixExpression();

        for (int i = 0; i < input.length; i++) {
            if (!self.isOperator(input[i])) {
                // input is a number
                stack.push(Integer.parseInt(input[i]));
            } else {
                // input is an operator
                stack.push(self.operate(input[i], stack.pop(), stack.pop()));
            }
        }

        if (stack.size() != 1) {
            throw new RuntimeException("More then one element left in stack");
        }

        return stack.pop();
    }

    private Boolean isOperator(String s) {
        return s.equals("*") || s.equals("/") || s.equals("+") || s.equals("-");
    }

    private int operate(String operation, Integer arg2, Integer arg1) {
        // make sure operator is valid
        if (!isOperator(operation)) {
            throw new IllegalArgumentException("Operator is not valid");
        }

        Integer result = null;

        if(operation.equals("*")) {
            result = arg1 * arg2;
        } else if (operation.equals("/") ) {
            result = arg1 / arg2;
        } else if (operation.equals("+")) {
            result = arg1 + arg2;
        } else if (operation.equals("-")) {
            result = arg1-arg2;
        }

        return result;
    }
}