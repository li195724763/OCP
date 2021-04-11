package Chapter7;

public class Chapter7 {
	
	class PrintData implements Runnable{
		@Override 
		public void run() {
			System.out.println("PrintData implements Runnable ");	
		}
	}
	
	static class ReadInventoryThread extends Thread{
		@Override 
		public void run() {
			System.out.println("ReadInventoryThread extends Thread");		
		}
	}
	
	public static final Chapter7 OBJECT = new Chapter7();
	public static void main(String[] args) {
		/*DOES NOT COMPILE
		 * Runnable tc2 = () -> 2;//DOES NOT COMPILE
		 * 
		 * */
		
		System.out.println("main thread");
		Runnable tc1 = () -> System.out.println("my first runnable after 10 years");	
		tc1.run();	
			
		new Thread(OBJECT.new PrintData()).start();
		new ReadInventoryThread().start();
		
	}
	
	public void method_1() { // instance method
		PrintData localInner = new PrintData();// created normally
		localInner.run();
		
		ReadInventoryThread localStaticInner = new ReadInventoryThread();
		localStaticInner.run();
	}
	
	public static void method_2() {// static method
		PrintData localInner = OBJECT.new PrintData();//created from the outter class object
		localInner.run();
		
		ReadInventoryThread localStaticInner = new ReadInventoryThread();
		localStaticInner.run();
	}

}
