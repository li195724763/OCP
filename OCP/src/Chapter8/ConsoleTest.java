package Chapter8;
import java.io.*;
import java.util.*;

public class ConsoleTest{
 	
  public static void main(String args[]) throws IOException {
 	Console c = System.console();
	int age = 0;
	if(c == null){
        	throw new RuntimeException("Console instance is null");
        } else {
        	System.out.println("The Console instance is not null");
		c.writer().print("How are you feeling today?");
		c.writer().println();
		//c.flush();
		String excite = c.readLine();
		String name = c.readLine("Please enter your name. \n");
		c.writer().println("What is your age?");
		c.flush();
		BufferedReader read = new BufferedReader(c.reader());
		String value = read.readLine();
		age = Integer.valueOf(value);
		c.writer().println();
		c.format("Your name is: " + name);
		c.writer().println();
		c.format("Your age is: " + age);
		c.writer().println();
		c.printf("Your excitement level is: " + excite);
		c.writer().println();
		//c.writer().println("read an input by using Console class's reader() method ");
		//c.flush();
		//int fromReader = c.reader().read();
                //c.writer().println("this is your input for reader() method: " + (char)fromReader);
		c.writer().println("start wrapping the console.read(), please enter an input for BufferedReader");
		c.flush();
		BufferedReader bfReader = new BufferedReader(c.reader());
                String bfReaderString = bfReader.readLine();
		c.writer().println("The String you have entered is: " + bfReaderString);
		c.printf("please enter your password \n");
		c.flush();
		char[] cArray = c.readPassword("I said enter your password!!!!! \n");
		c.format("your password is \n" + String.valueOf(cArray));
        }
  }
   
}

