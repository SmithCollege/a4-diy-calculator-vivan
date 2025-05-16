package a4;

import java.util.ArrayDeque;

public class Infix {

    public static ArrayDeque<Object> stack = new ArrayDeque(); // operators
    public static ArrayDeque<Object> outputQueue = new ArrayDeque(); // numbers

    /**
     * A method which converts a string of tokens into postfix notation
     * @param tokens list of tokens to be processed
     * @return result of the calculation
     */
    public static Double infixToPostfix(ArrayDeque<Object> tokens) {

        while (!tokens.isEmpty()) { //while tokens left to process

            Object token = tokens.poll();

            if (token instanceof Double) { //if its a number, add it to the output queue

                outputQueue.add(token);

            } else if (token instanceof Character) { //if its a character

                Character op = (Character) token;

                if (op == '(') { // if its a left parentheses, push it onto the operator stack

                    stack.push(op);

                } else if (op == ')') { // if its right parentheses

                    while (!stack.isEmpty() && (Character) stack.peek() != '(') { // go through the operator stack until we find a matching left parentheses
                        outputQueue.add(stack.pop()); //while we have not found it, keep adding operators
                    }

                    if (stack.isEmpty()) { // didnt find left parentheses in operator stack
                        throw new IllegalArgumentException("Mismatched parentheses");
                    }

                    stack.pop(); //remove the left parentheses from the stack

                } else if (isOperator(op)) { // if its a valid operator

                    boolean leftAssociative = (op != '^'); 
                    boolean rightAssociative = (op == '^');

                    while (!stack.isEmpty() && isOperator((Character) stack.peek())) { //while there are operators in the stack

                        Character topOp = (Character) stack.peek(); 

                        if ((leftAssociative && precedence(topOp) >= precedence(op)) ||
                                (rightAssociative && precedence(topOp) > precedence(op))) { //check if the precedence is high enough based on left/right associativity
                            outputQueue.add(stack.pop()); //if its high enough, remove from stack and place in queue
                        } else {
                            break;
                        }

                    }

                    stack.push(op); //add operator to stack after handling other operators

                } else {
                    throw new IllegalArgumentException("Invalid token");
                }

            } else {
                throw new IllegalArgumentException("Invalid token: not character or double");
            }

        }

        while (!stack.isEmpty()) { //add remaining operators to the queue
            Character op = (Character) stack.pop();
            if (op == '(' || op == ')') { //check for any random mismatched parenthese
                throw new IllegalArgumentException("Mismatched parentheses");
            }

            outputQueue.add(op); //add to the queue
        }

        System.out.println(outputQueue);
        
        return Postfix.postfix(outputQueue);
    }

    /**
     * Helper method which determines the precedence of every operator
     * @param operator operator to determine precedence of
     * @return a number indicating the precedence (high number = high precedence)
     */
    private static int precedence(Character operator) {
        switch (operator) {
            case '^':
                return 3;
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    /**
     * A helper method that determines if a character is a valid operator
     * @param c character that may or may not be an operator
     * @return true if it is an operator, false otherwise
     */
    private static boolean isOperator(Character c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    /**
     * Main method for testing and debugging purposes only
     * @param args
     */
    public static void main(String[] args) {

        String[] expressions = { "6+1", "2+3+4" };
        System.out.println(Infix.infixToPostfix(Tokenizer.readTokens(expressions[0])));
        System.out.println(Infix.infixToPostfix(Tokenizer.readTokens(expressions[1])));
    }

}
