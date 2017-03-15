import java.io.*;
import java.util.*;

 class Program {

     public static void main(String[] args){
         Scanner scanner = new Scanner(new BufferedInputStream( System.in));
         String sqlQuery="";
         System.out.println("Enter .sql file. Press enter or enter 0 to enter sql statement directly");
         String filename = scanner.next();

         if (filename.equals("") || filename.isEmpty() || filename.equals("0")) {
             System.out.println("Enter SQL statements, press Enter twice to finish: ");
             scanner.useDelimiter("\n\n"); // statement ends with an
             sqlQuery = scanner.next();
             scanner.reset();
         }

        else { //TODO: import .sql file
                sqlQuery=readFile(filename);
         }

         System.out.println("Enter 1 for lower case, 2 for upper case, 3 for uppercase Keywords only, " +
                 "4 for capitalization: ");
         int option = 0;
         try {
             option = scanner.nextInt();

             if (option == 1)
                 sqlQuery = sqlQuery.toLowerCase();

             else if (option == 2)
                 sqlQuery = sqlQuery.toUpperCase();

             else if (option == 3){

                 sqlQuery= upperCaseKeywords(sqlQuery);
             }
             else if (option==4){
                 sqlQuery= toCapitalizedCase(sqlQuery);
             }

             else System.out.println("No such option.");
         } catch (InputMismatchException e) {
             System.err.print("Cannot recognize option: ");
             System.err.println(option);
             e.printStackTrace();
         }




         System.out.println(sqlQuery);

         System.out.println("Export statement(s) to file? Press 1 to export, press anything else to exit: ");

         option = scanner.nextInt();
         switch (option){
             case 1: try {
                 toFile(sqlQuery);
             } catch (IOException e) {
                 e.printStackTrace();
             }
             break;

             case 2: scanner.close();
                break;
         }

         scanner.close();

     }

     // capitalization for each beginning letter of words
     private static String toCapitalizedCase(String sqlQuery) {
         StringBuilder stringBuilder = new StringBuilder();
         String[] splitted = sqlQuery.split("\\s+");
         for(String s: splitted){
             char[] array = s.toCharArray();
             for (int i = 0; i<array.length;i++) {
                 if (i==0)
                    array[i] = Character.toUpperCase(array[i]);
                 else array[i] = Character.toLowerCase(array[i]);
             }
             stringBuilder.append(array).append(" ");
         }
         return  stringBuilder.toString();
     }

     // break inot list of strings, go through each string, see if they match the keywords set
     // TODO: if keyword ends with = or ; ???
     private static String upperCaseKeywords(String sqlQuery) {
         String[] splited = sqlQuery.split("\\s+");
         StringBuilder stringBuilder = new StringBuilder();
         for (String s:splited){
             if (SQLKeywords.getInstance().getKeywords().contains(s.toUpperCase())){
                 s = s.toUpperCase();
             }
             else {
                 s = s.toLowerCase();
             }
             stringBuilder.append(s).append(" ");
         }
         System.out.println();
         return  stringBuilder.toString();
     }

     // read from .sql file
     private static String readFile(String filename) {

         try {
             InputStream inputStream = new FileInputStream(filename);
             BufferedReader buf = new BufferedReader(new InputStreamReader(inputStream));
             String line = buf.readLine();
             StringBuilder sqlBuilder = new StringBuilder();
             while (line!=null){
                 sqlBuilder.append(line).append("\n");
                 line = buf.readLine();
             }
             return sqlBuilder.toString();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
             return null;
         } catch (IOException e) {
             e.printStackTrace();
             return null;
         }
     }

     // Output to .sql file
     private static int toFile(String sqlQuery) throws IOException {
         FileWriter fileWriter=null;
         try {
             fileWriter = new FileWriter(new Date().toString()+".sql");
             fileWriter.write(sqlQuery);
             return 1;
         } catch (IOException e) {
             e.printStackTrace();
             return -1;
         }
         finally {
             if (fileWriter!=null){
                 fileWriter.close();
             }
         }
     }
 }
