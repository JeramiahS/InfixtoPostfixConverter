import java.util.Stack;

public class Application {

    public static void main(String[] args) {
        System.out.println("Postfix form: " + InfixToPostfixConverter.convert(args[0]));
    }

    static final class InfixToPostfixConverter {

        /**
         * Takes an expression in infix form and converts it to postfix form
         *
         * @param expression    An expression in infix form
         * @return              The postfix conversion of the expression
         */
        static String convert(String expression) {
            final Stack<Character> STACK = new Stack<>();
            final StringBuilder STRING_BUILDER = new StringBuilder();

            for (int i = 0; i < expression.length(); ++i) {
                char currentChar = expression.charAt(i);

                //If currentChar is a space, skip to the next character
                if(currentChar == ' ') {
                    currentChar = expression.charAt(++i);
                }

                if (Character.isLetterOrDigit(currentChar)) {
                    STRING_BUILDER.append(currentChar);
                }
                else if (currentChar == '(') {
                    STACK.push(currentChar);
                }
                //If a closing bracket is encountered, push from the stack until an opening bracket is found
                else if (currentChar == ')') {
                    while (!STACK.isEmpty() && STACK.peek() != '(') {
                        STRING_BUILDER.append(STACK.pop());
                    }
                    //Remove '(' from stack since it will not be in the final output
                    STACK.pop();
                }
                //When an operator is encountered, pop from the stack until another operator is encountered.
                //Compare the precedence values of the operator at the top of the stack to the current char.
                //Pop from the stack until an operator with lower precedence is found, then push current char into stack.
                else {
                    while (!STACK.isEmpty() && precedenceValue(STACK.peek()) >= precedenceValue(currentChar)) {
                        STRING_BUILDER.append(STACK.pop());
                    }
                    STACK.push(currentChar);
                }
            }
            //Flush the stack once the end of the input expression is reached
            while (!STACK.isEmpty()) {
                STRING_BUILDER.append(STACK.pop());
            }
            return STRING_BUILDER.toString();
        }

        /**
         * Checks and returns the precedence value of a mathematical operator or other character. Characters that are
         * not operators have a value of -1.
         *
         * @param character A char
         * @return          An integer value representing the precedence value of a mathematical operator.
         *                  If the char is not an operator, -1 is returned.
         */
        private static int precedenceValue(char character) {
            if (character == '^') {
                return 3;
            } else if (character == '*' || character == '/') {
                return 2;
            } else if (character == '+' || character == '-') {
                return 1;
            } else {
                return -1;
            }
        }
    }

}