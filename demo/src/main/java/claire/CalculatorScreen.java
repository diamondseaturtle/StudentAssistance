package claire;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.io.*;
import java.util.*;
import java.util.HashMap;



public class CalculatorScreen{
    private JLayeredPane panel = new JLayeredPane();
    private JLabel close = null;
    private JTextField textFields = null;
    private JLabel[][] buttons = null;
    
    private HashMap<String, Integer> valueLookup = new HashMap<String, Integer>(); {{
        valueLookup.put("-", 0);
        valueLookup.put("+", 0);
        valueLookup.put("/", 1);
        valueLookup.put("*", 1);
        valueLookup.put("^", 2);
    }}

    private enum Stage 
    {
        Sign,
        Number,
        Operand,
        Decimal,
        Parentheses,
        Failure
    }

    private static void stringParser(String eq, ArrayList<String> tokens, HashMap<String, Integer> valCheck)
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

    private static double doOperation(double num1, double num2, String operator)
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

    private double calculate(String eq, HashMap<String, Integer> valCheck){
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
                    parentResult = calculate(parent, valCheck);
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
    
    public CalculatorScreen(){
        
        panel.setBounds(0, 0, 1280, 720);
        
        // X button. Closes the window
        close = new JLabel("", new ImageIcon("res/Close.png"), JLabel.LEFT);
        close.setBounds(1280-45, 0, 45, 45);
        panel.add(close, 2);
        close.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        final String[][] buttonVals =  {
                    {"7","4","1","C"},
                    {"8","5","2","0"},
                    {"9","6","3","="},
                    {"(","+","*","^"},
                    {")","-","/","."}
                };
        final ImageIcon squareIcon = new ImageIcon("res/SquareIcon.png");
        
        final int buttonSize = 50; // button length in pixels
        final int buttonSpacing = 5; // set the pixel spacing between the buttons
        
        final int calculatorWidth = (buttonSize * buttonVals.length) + (buttonSpacing * buttonVals[0].length);
        final int calculatorHeight = (buttonSize * buttonVals[0].length) + (buttonSpacing * buttonVals[0].length);
        
        buttons = new JLabel[buttonVals.length][buttonVals[0].length];
        
            
            for (int j = 0; j < buttonVals[0].length; j++) {
                for (int i = 0; i < buttonVals.length; i++) {
                buttons[i][j] = new JLabel(buttonVals[i][j], squareIcon, JLabel.CENTER);
               
                // Get dimensions of all buttons laid out
                
                buttons[i][j].setBounds(
                    (1280/2 - calculatorWidth/2) // Center the X origin point
                    +(i*buttonSize) // Offset by buttons 
                    +(i*buttonSpacing),  // Offset by spacing
                                        
                    (720/2 - calculatorHeight/2) // Center the Y origin point
                    +((j+1)*buttonSize) // Offset by buttons. The +1 is to account for the textfield on top
                    +((j+1)*buttonSpacing), // Offset by spacing. The +1 is to account for the textfield on top
                    
                    buttonSize, buttonSize); // button dimensions
                
                buttons[i][j].setFont(Window.font);
                buttons[i][j].setForeground(Window.colorText);
                buttons[i][j].setHorizontalTextPosition(JLabel.CENTER);
                buttons[i][j].setVerticalTextPosition(JLabel.CENTER);
                
                buttons[i][j].addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel label = (JLabel)e.getSource(); // Get JLabel
                        String string = label.getText();
                        if(string.equals("=")){
                            try {
                                textFields.setText( // 3. Calculated result is put back into the text field
                                    String.valueOf(calculate( // 2. String is put into calculator
                                    textFields.getText(), valueLookup // 1. String equation from the text field is obtained
                                    )));     
                            } catch (IllegalArgumentException er)
                            {
                                textFields.setText(er.getMessage());
                            }
                        }
                        else if(string.equals("C")){
                            textFields.setText(""); // Clear text
                        }
                        else{
                            textFields.setText(textFields.getText() + string); // Add character to textfield
                        }
                    }
                });
        
                panel.add(buttons[i][j]);
            }
            }
        

            textFields = new JTextField();
            textFields.setColumns(1);
            textFields.setFont(Window.font);
            textFields.setBackground(Window.colorBox);
            textFields.setForeground(Window.colorText);
            textFields.setEditable(false);
            textFields.setBorder(BorderFactory.createLineBorder(Window.colorBox));
            textFields.setBounds((1280/2 - calculatorWidth/2), 
                                 (720/2 - calculatorHeight/2)- 55 - buttonSpacing, 
                                 calculatorWidth, 
                                 55);
           
        panel.add(textFields);
        
        Window.frame.add(panel);
        Window.frame.setVisible(true);
    }
}
