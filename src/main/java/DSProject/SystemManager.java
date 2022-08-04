package DSProject;
import com.google.gson.Gson;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import DSProject.*;
import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;

public class SystemManager {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Database d=initializeDatabase();
        //check if information properly deserialized without exception thrown
        if(d==null) {
            System.out.println("We apologize, but we are currently experiencing technical difficulties and can't manage to load the information from our servers");
            System.out.println("Please try again soon.  Have a nice day.");
            return;
        }
        Scanner myScanner=new Scanner(System.in);
        User u=d.accessUsersAccount(myScanner);
        //check if user failed to login
        if(u==null) {
            System.out.println("Exiting the program as you have failed to login. Please try again in one minute.");
            return;
        }
        runProgram(d,u,myScanner);
        closeProgram(d);
    }
    /**
     * intializes the database from the serialized JSON after the last use, or a new one if this is the first time the program is used
     * @return the Database object
     * @throws IOException, ClassNotFoundException
     */
    public static Database initializeDatabase() throws IOException, ClassNotFoundException {
        Database d;
        try {
            //Files.createDirectories(Path.of(System.getProperty("user.dir")));
            //File file=new File("Database.json");
            FileInputStream fileIn = new FileInputStream("Database.json");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Gson gson = new Gson();
            d = gson.fromJson((String) in.readObject(), Database.class);
            return d;
        } catch (FileNotFoundException i) {
            d=new Database();
            return d;
        } catch (IOException i) {
            System.out.println("Database information not found");
            System.out.println("io exception");
            d=new Database();
            return d;
        } catch (ClassNotFoundException c) {
            System.out.println("Database information not found");
            d = new Database();
            return d;
        }
    }
    /**
     * @param d
     * @param u
     * @param myScanner
     * runs the program
     */
    public static void runProgram(Database d,User u,Scanner myScanner) {
        while(true) {
            System.out.println("Would you like to continue to use the program?  Enter \"yes\" to continue, or \"exit\" to log out.");
            String input=myScanner.next().toUpperCase();
            if(input.equals("YES")) {
                d.discoverAndHandleNextAction(u,myScanner);
            }
            else if(input.equals("EXIT")) {
                break;
            }
            else {
                System.out.println("You have failed to choose what you want to do.   Please enter \"yes\" to continue, or \"exit\" to log out.");
            }
        }
    }
    /**
     * closes the program and serializes the information within as a JSON
     */
    public static void closeProgram(Database d) {
        try {
            FileOutputStream fileOut = new FileOutputStream("Database.json");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            Gson gson=new Gson();
            String json1=gson.toJson(d);
            out.writeObject(json1);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
