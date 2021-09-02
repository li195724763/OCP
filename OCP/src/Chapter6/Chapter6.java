package Chapter6;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Chapter6 {

	public static void main(String[] args) throws FileNotFoundException, IOException  {
		testMultiCatch();
		testRegularTryCatch();
		testTryWithResource();
		testAutoCloseable();
		testAssertionTest(-1);
		testThrowSuperClassException();
		try {
			Turkey t = new Turkey();
			//t.close();
			{
				System.out.print("a");
			}
		}catch (Exception e) {
			
		}
		System.out.println("End of Chapter6");
	}
	
	public static void testMultiCatch() {
		try{
			System.out.println(testReturnStatementInTryCatchFinally());
			//throw new NullPointerException("I manually throw it");
		}catch(ArrayIndexOutOfBoundsException | NullPointerException e) {//DOES NOT COMPILE : catch(ArrayIndexOutOfBoundsException | RuntimeException e)
			e.printStackTrace();
			//e = new NullPointerException();//does not compile due to reassignment.
			System.out.println("after the print stack trace");// logic after print stack trace can still be executed.
		}
		
		
		try {
			throw new FileNotFoundException();
		}catch(FileNotFoundException e) {
			
		}catch(IOException e2) {
			
		}
		
		/*try {
			throw new FileNotFoundException();
		}catch(FileNotFoundException | IOException e) {// does not compile
			
		}*/
	}
	
	public static void testRegularTryCatch() {
		try {
			throwAnException();
		}catch (FileNotFoundException e) {
			
		}
		catch (IOException e) {
			//RuntimeException ee = e;does not compile
			Exception ee = e;
				
		}
		
		try {}
		//{}//does not compile
		catch(Exception e) {
			
		}
	}
	
	public static void testTryWithResource() {
		try(Turkey t = new Turkey(); Chicken c = new Chicken();){ 
			// does not compile if not having a reference t. E.g. simply having "new Turkey()" will not compile.
			//resource close on backwards order.
			System.out.println("1 testTryWithResource: under try block");
		} catch(Exception e) {
			System.out.println("4 testTryWithResource under catch block, catching Exception throwing from close()");
		} finally {
			System.out.println("5 testTryWithResource under finally block");
		}
		
		
		//try(Turkey t = new Turkey()){ }//this is legal and compiles, comment out since the close() method in Turkey throws NPE.
	}
	

	public static void testAutoCloseable() {	
		try(Turkey t = new Turkey()){
			System.out.println("testAutoCloseable*****************");
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
	
	public static void testAssertionTest(int x) {
		//if(x < 0) { throw new IllegalArgumentException(); }	
		//assert 1; does not compile, has to be boolean
		
		assert (x > 0) : ("1: X should great than 0");
		assert x > 0 : ("2: X should great than 0");
		assert(x > 0);
		
		System.out.println("code after assert");
	}
	
	
	public static void testThrowSuperClassException() throws IOException{
		//throw new FileNotFoundException();//it's legal to declare a superclass exception
	}
	
	public static String testReturnStatementInTryCatchFinally() throws IllegalStateException {
		try {
			return "in try";
		} 
		catch(Exception e) {
			return "in catch";
		}
		finally {
			System.out.println("this is under finally block where a return statement in try/catch");
		}
	}
	
	public static void throwAnException() throws IOException {//DOES NOT COMPILE if missing throws IOException
		throw new IOException();
	}

}


