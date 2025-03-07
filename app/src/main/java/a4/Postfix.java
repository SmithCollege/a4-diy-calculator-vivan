package a4;

import java.util.ArrayDeque;

public class Postfix {

    public static ArrayDeque<Object> stack = new ArrayDeque();

    public static Double postfix(ArrayDeque<Object> tokens) {
        while(!tokens.isEmpty()){
            Object token = tokens.poll();

            if( token instanceof Double ){ 

                stack.push(token);

            } else if( token instanceof Character ){

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

    public static Double combine(Character operator, Double num1, Double num2){

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
            
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }

    public static void main(String[] args) {
        // String[] expressions = {
        //     "6 1 +", "0 7 +", "2 3 2 + +", "2 2 2 1 + + +", "1 1 1 1 1 1 1 + + + + + +", "2 2 + 2 1 + +"
        // };

        // for (int i = 0; i < expressions.length; i++) {
        //     System.out.println(Postfix.postfix(Tokenizer.readTokens(expressions[i])));
            
        // }


    }
    
}