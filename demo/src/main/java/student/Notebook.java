//package student;
//import java.util.*;
//import java.io.*;
//
//public class Notebook
//{
//    public static void main(String[] args) throws FileNotFoundException{
//        Scanner console = new Scanner(System.in);
//        
//        boolean confirm = false;
//        File tempFile = null;
//        String input = "";
//        
//        System.out.print(
//            "\nInput a command:" +
//            "\n1. New - Write a new file" +
//            "\n2. Open - Access an existing file" +
//            "\n"
//        );
//        
//        input = console.nextLine();
//        while (!confirm){
//            if (input.equals("1")) {
//                confirm = true;
//                
//            }
//            else if (input.equals("2")) {
//                confirm = true;
//                accessExistingFile(console);
//            }
//            else{
//                System.out.print("\nError: Input command number");
//            }
//        }
//        
//        
//        
//        String command = console.nextLine();
//    }
//    
//    public static String accessExistingFile(Scanner console){
//        boolean confirm = false;
//        File tempFile = null;
//        String input = "";
//        
//        File folder = new File("Files");
//        File[] listOfFiles = folder.listFiles();
//        
//        System.out.print("\nSelect existing file:");
//        {
//            int j = 0;
//            for (int i = 0; i < listOfFiles.length; i++) {
//                if (listOfFiles[i].isFile()) {
//                    j++;
//                    System.out.print("\n"+ j +". "+ listOfFiles[i].getName());
//                }
//            }
//            j++;
//            System.out.print("\n"+j+". Return to menu");
//        }
//        
//        while (!confirm){
//            input = console.nextLine();
//            
//            try{
//                if (listOfFiles[Integer.parseInt(input)].exists()){
//                    confirm = true;
//                    input = listOfFiles[Integer.parseInt(input)].getName();
//                }
//                else {
//                    System.out.print("\nError: Input file "+input+" does not exist.");
//                }
//            }
//            catch (NumberFormatException ex){
//                tempFile = new File(input);
//                if (tempFile.exists()){
//                    confirm = true;
//                }
//            }
//            
//        }
//        return input;
//    }
//    
//    public static String getInputFile(Scanner console)
//    {
//        boolean confirm = false;
//        File tempFile = null;
//        String input = "";
//        
//        while (!confirm){
//            System.out.print("\nEnter input file name: ");
//            input = console.nextLine();
//            
//            tempFile = new File(input);
//            if (tempFile.exists()){
//                confirm = true;
//            }
//            else {
//                System.out.print("\nError: Input file "+input+" does not exist. Please enter a new file name.");
//            }
//        }
//        return input;
//    }
//    
//    public static String getOutputFile(Scanner console)
//    {
//        boolean confirm = false;
//        File tempFile = null;
//        String input = "";
//        
//        while (!confirm){
//            System.out.print("\nEnter output file name: ");
//            input = console.nextLine();
//            
//            tempFile = new File(input);
//            if (tempFile.exists()){
//                System.out.print("\nOutput file "+input+" exists. Do you want to overwrite it? (yes/no) ");
//                confirm = ((console.nextLine()).toLowerCase()).equals("yes");
//            }
//            else {
//                confirm = true;
//            }
//        }
//        return input;
//    }
//}
//
