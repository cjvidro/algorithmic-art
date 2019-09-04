/**
 * @author Charles Vidro
 * Assignment 2.
 * 
 * This class converts an infix expression to postfix exppression
 */

public class InfixToPostfix {
	/* Convert an infix expression to a postfix expression
	 * infix expression : operator is between operands. Ex. 3 + 5
	 * postfix Expression: operator is after the operands. Ex. 3 5 +
	 * 
	 * The infixExp expression includes the following
	 *      operands:  integer or decimal numbers 
	 *      and operators: + , - , * , /
	 *      and parenthesis: ( , )
	 *      
	 *      For easy parsing the expression, there is a space between operands and operators, parenthesis. 
	 *  	Ex: "1 * ( 3 + 5 )"
	 *      Notice there is no space before the first operand and after the last operand/parentheses. 
	 *  
	 * The postExp includes the following 
	 *      operands:  integer or decimal numbers
	 *      and operators: + , - , * , /
	 *
	 *      For easy parsing the expression, there should have a space between operands and operators.
	 *      Ex: "1 3 5 + *"
	 *      Notice there is space before the first operand and last operator.
	 *      Notice that postExp does not have parenthesis.
	 */
	public static String convert(String infixExp, int num) {
		InfixToPostfix self = new InfixToPostfix();
		String[] input = infixExp.split(" ");
		DLLStack<String> stack = new DLLStack<>();
		String result = "";
		
		for (String s : input) {
			if (s.equals("(")) {
				stack.push(s);
			} else if (s.equals(")")) {
				while (!stack.top().equals("(")) {
					result += stack.pop() + " ";
				}
				// remove "(" from stack
				stack.pop();
			} else if (self.isOperator(s)) {
				while (!stack.isEmpty() && self.hasPrecedence(s, stack.top())) {
					result += stack.pop() + " ";
				}
				stack.push(s);
			} else {
				if (self.isNumeric(s)) {
                    result += s + " ";
                } else if (!s.equals(" ")) {
                    result += num + " ";
                }
			}
		}
		
		while (!stack.isEmpty()) {
			result += stack.pop() + " ";
		}
		
		// remove extra space
		return result.substring(0, result.length() - 1);
	}	

	private Boolean isOperator(String s) {
		return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
	}
	
	/**
	 * Determines if the element in the stack has precedence
	 * @param input the element that is input
	 * @param inStack the element in the stack
	 * @return true if the stack has precedence
	 */
	private Boolean hasPrecedence(String input, String inStack) {
		if (inStack.equals("*") || inStack.equals("/")) {
			return true;
		} else if (input.equals("*") || input.equals("/") || inStack.equals("(")) {
			return false;
		}
		
		// both are + or -
		return true;
	}

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
