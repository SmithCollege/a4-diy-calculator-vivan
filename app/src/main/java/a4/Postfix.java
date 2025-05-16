package a4;

import java.util.ArrayDeque;

public class Postfix {

    public static ArrayDeque<Object> stack = new ArrayDeque(); //our stack

    /**
     * This method process input tokens in post fix notation and returns the result of the calculation
     * @param tokens list of tokens to process
     * @return result of the calculation
     */
    public static Double postfix(ArrayDeque<Object> tokens) {
        while (!tokens.isEmpty()) {
            Object token = tokens.poll();

            if (token instanceof Double) {

                stack.push(token);

            } else if (token instanceof Character) {

                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Not enough elements to operate on.");
                }

                Double num1 = (Double) stack.pop();
                Double num2 = (Double) stack.pop();

                Character operator = (Character) token;

                Double result = combine(operator, num1, num2);
                stack.push(result);

            } else {
                throw new IllegalArgumentException();
            }
        }
        System.out.println(stack);
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Issue with Stack Size");
        }

        return (Double) stack.pop();

    }

    /**
     * A helper method which performs the calculations using the operator and 2 numbers
     * @param operator how to combine the numbers
     * @param num1 the first number
     * @param num2 the second number
     * @return the result of the operation of the two numbers
     */
    public static Double combine(Character operator, Double num1, Double num2) {

        switch ((Character) operator) {
            case '+':
                return (Double) num2 + (Double) num1;
            case '-':
                return (Double) num2 - (Double) num1;
            case '*':
                return (Double) num2 * (Double) num1;
            case '/':
                if (num1 == 0.0) {
                    throw new IllegalArgumentException("Division by zero.");
                }
                return (Double) num2 / (Double) num1;
            case '^':
                return (Double) Math.pow(num2, num1);
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }

    public static void main(String[] args) {

    }

}