package claire;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.util.HashMap;


/*
*   Calculator functionality by Claire
*/
public class Calc {

    enum Stage
    {
        Sign,
        Number,
        Operand,
        Decimal,
        Parentheses,
        Failure
    }

    public static void stringParser(String eq, ArrayList<String> tokens, HashMap<String, Integer> valCheck)
    {   
        Stage flag = Stage.Parentheses;
        String addStr = "";
        for (int i = 0; i < eq.length();)
        {
            if (flag == Stage.Parentheses)
            {
                if (eq.charAt(i) == '(')
                {
                    int counter = 1;
                    addStr += eq.substring(i, i + 1);
                    i++;
                    while(i < eq.length() && counter > 0)
                    {
                        addStr += eq.substring(i, i + 1);
                        if (eq.charAt(i) == '(')
                        {
                            counter++;
                        }
                        else if (eq.charAt(i) == ')')
                        {
                            counter--;
                        }
                        i++;
                    }
                    if (counter != 0)
                    {
                        flag = Stage.Failure;
                    }
                    else
                    {
                        tokens.add(addStr);
                        addStr = "";
                        flag = Stage.Operand;
                    }
                }
                else
                {
                    flag = Stage.Sign;
                }
            }
            else if (flag == Stage.Sign)
            {
                if (eq.substring(i, i + 1).equals("-"))
                {
                    addStr += eq.substring(i, i + 1);
                    i++;
                }
                flag = Stage.Number;
            }
            else if (flag == Stage.Number)
            {
                boolean found = false;
                while (i < eq.length())
                {
                    if (Character.isDigit(eq.charAt(i)) || eq.substring(i, i + 1).equals("."))
                    {
                        addStr += eq.substring(i, i + 1);
                        found = true;
                        if (eq.substring(i, i + 1).equals("."))
                        {
                            flag = Stage.Decimal;
                            i++;
                            break;
                        }
                        else
                        {
                            i++; 
                        }
                    }
                    else
                    {
                        break;
                    }   
                }
                if (found && flag != Stage.Decimal)
                {
                    tokens.add(addStr);
                    addStr = "";
                    flag = Stage.Operand;
                }
                else if (!found)
                {
                    flag = Stage.Failure;
                }
            }
            else if (flag == Stage.Decimal)
            {
                boolean found = false;
                while (i < eq.length() && Character.isDigit(eq.charAt(i)))
                {
                    addStr += eq.substring(i, i + 1);
                    found = true;
                    i++;
                }
                if (found)
                {
                    tokens.add(addStr);
                    addStr = "";
                    flag = Stage.Operand;
                }
                else
                {
                    flag = Stage.Failure;
                }
            }
            else if (flag == Stage.Operand)
            {
                if (valCheck.containsKey(eq.substring(i, i + 1)))
                {
                    tokens.add(eq.substring(i, i + 1));
                    flag = Stage.Parentheses;
                    i++;
                }
                else
                {
                    flag = Stage.Failure;
                }
            }
            else 
            {
                break;
            }
          
        }
        if (flag == Stage.Failure) 
        {
            throw new IllegalArgumentException("Invalid arguments");
        }        
    }

    public static double doOperation(double num1, double num2, String operator)
    {
        double result = 0;
        if (operator.equals("-"))
        {
            result = num1 - num2;
        }
        else if (operator.equals("+"))
        {
            result = num1 + num2;
        }
        else if (operator.equals("*"))
        {
            result = num1 * num2;
        }
        else if (operator.equals("/"))
        {
            result = num1 / num2;
        }
        else if (operator.equals("^"))
        {
            result = Math.pow(num1, num2);
        }
        return result;
    }

    public static double operation(String eq, HashMap<String, Integer> valCheck)
    {
        ArrayList<String> tokens = new ArrayList<String>();
        Stack<Double> operands = new Stack<Double>();
        Stack<String> operators = new Stack<String>();
        stringParser(eq, tokens, valCheck);
        for (int i = 0; i < tokens.size();)
        {
            double parentResult = 0;
            if (i % 2 == 0)
            {
                if (tokens.get(i).charAt(0) == '(')
                {
                    String parent = tokens.get(i);
                    parent = parent.substring(1, parent.length() - 1);
                    parentResult = operation(parent, valCheck);
                }
                else
                {
                    parentResult = Double.parseDouble(tokens.get(i));
                }
                operands.push(parentResult);
                i++;
            }
            else
            {
                if (operators.isEmpty() || valCheck.get(tokens.get(i)) > valCheck.get(operators.peek()))
                {
                    operators.push(tokens.get(i));
                    i++;
                }
                else if (valCheck.get(tokens.get(i)) <= valCheck.get(operators.peek()))
                {
                    double num2 = operands.pop();
                    double num1 = operands.pop();
                    double result = doOperation(num1, num2, operators.pop());
                    operands.push(result);
                }
            }
        }
        while (!operators.isEmpty())
        {
            double num2 = operands.pop();
            double num1 = operands.pop();
            double result = doOperation(num1, num2, operators.pop());
            operands.push(result);
        }
        if (!operands.isEmpty())
        {
            return operands.pop();
        }
        throw new IllegalArgumentException("Empty stack");
    }
    public static void main(String[] args)
    {
        HashMap<String, Integer> valueLookup = new HashMap<String, Integer>();
        valueLookup.put("-", 0);
        valueLookup.put("+", 0);
        valueLookup.put("/", 1);
        valueLookup.put("*", 1);
        valueLookup.put("^", 2);
        Scanner s = new Scanner(System.in);
        System.out.println("Enter an equation");
        String eq = s.nextLine();
        double result = 0;

        try
        {
            result = operation(eq, valueLookup);
            System.out.print(result);
        } 
        catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
    }
}