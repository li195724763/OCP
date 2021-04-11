package Chapter6;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Chapter6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try{
			System.out.print(method_1());
			throw new NullPointerException("I manually throw it");
		}catch(ArrayIndexOutOfBoundsException | NullPointerException e) {//DOES NOT COMPILE : catch(ArrayIndexOutOfBoundsException | RuntimeException e)
			e.printStackTrace();
			System.out.println("after the print stack trace");// logic after print stack trace can still be executed.
		}
		
		
		//you can catch an exception which is a subtype of the exception throws from the calling method
		try {
			method_2();
		}catch (FileNotFoundException e) {
			
		}
		catch (IOException e) {
			
		}
		
		//try-with-resource
		try(Turkey t = new Turkey();
				Chicken c = new Chicken()){ // does not compile if not having a reference t. E.g. simply having "new Turkey()" will not compile
			
		} catch(Exception e) {
			System.out.println("catching Exception throwing from close()");
		}
		
		autoCloseable();
		assertionTest(1);
		try {
			Turkey t = new Turkey();{
				System.out.print("a");
			}
		}catch (Exception e) {
			
		}
	}
	
	public static String method_1() throws IllegalStateException{
		try {
			return "in try";
		} 
		catch(Exception e) {
			return "in catch";
		}
		finally {
			return "in finally";
		}
	}
	
	public static void method_2() throws IOException {//DOES NOT COMPILE if missing throws IOException
		throw new IOException();
	}
	
	public static void autoCloseable() {
		
		try(Turkey t = new Turkey()){
			System.out.println("*****************");
			System.out.println("code in try calls before the close() method");
			throw new IllegalStateException("exception1");
		} catch(IllegalStateException e) {
			/*
			 * System.out.println("Try# 4, catching: " + e.getMessage()); for(Throwable t :
			 * e.getSuppressed()) { System.out.println("Suppressed Exception " +
			 * t.getMessage()); }
			 */
			
			//throw new NullPointerException("Exception in catch block");
			
			//throw new java.text.ParseException("test", 1); //ParseException is checked exception
			
		}
	}
	
	public static void assertionTest(int x) {
	
		if(x < 0) { throw new IllegalArgumentException(); }

		
		assert (x > 0) : "X should great than 0";
		
		System.out.println("code after assert");
	}
	
	
	public static void throwSuperClassException() throws IOException{
		throw new FileNotFoundException();
	}


}


