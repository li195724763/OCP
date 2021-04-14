package Chapter7;
import java.util.concurrent.*;

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
	private static int counter = 0;
	public static final Chapter7 OBJECT = new Chapter7();
	static ExecutorService staticExecutor = Executors.newSingleThreadExecutor();
	static int UTILITY_INT = 0;
	public static void main(String[] args) throws InterruptedException, InterruptedException, TimeoutException , ExecutionException {
		/*DOES NOT COMPILE
		 * Runnable tc2 = () -> 2;//DOES NOT COMPILE
		 * 
		 * */
		
		/*Throw Exception
		 * e.shutdown();
		 * e.execute(() -> System.out.println("Printing zoo inventory"));// Throw exception
		 * 
		 * */
		System.out.println("main thread");
		Runnable tc1 = () -> System.out.println("my first runnable after 10 years");	
		tc1.run();	//no thread created
			
		new Thread(OBJECT.new PrintData()).start();
		new ReadInventoryThread().start();
		
		new Thread(OBJECT.new PrintData()).run();//no thread created
		
		new Thread(() -> {
			for(int i=0;i<100;i++) {
				counter++;
			}
			
		}).start();
		
		while(counter < 100) {
			System.out.println("counter is less than 100");
			Thread.sleep(1000);//throw InterruptedException
		}
		
		if(counter == 100) {
			System.out.println("counter is now 100");
		}
		
		executorTest();
		//assert false : "the program does not hang";
		System.out.println("seems the program won't hang");
		
		OBJECT.method_3();
		OBJECT.executorServiceUsingCallable();
		schedulerTest();
		
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
	
	public static void executorTest() throws TimeoutException, InterruptedException, ExecutionException {
		ExecutorService e = null;// not Executor
		
		try {
			e = Executors.newSingleThreadExecutor();
			
			System.out.println("begin");
			e.submit(() -> System.out.println("Printing zoo inventory"));
			Future<?> tc1 = e.submit(() -> {
				for(int i=0;i<3;i++) {
					System.out.println("Printing Record: " + i);
				}
			});
			e.submit(() -> System.out.println("Printing zoo inventory"));
			System.out.println("end");	
			System.out.println("Future test isCancelled: " + tc1.isCancelled());
			System.out.println("Future test get: " + tc1.get(1, TimeUnit.MILLISECONDS));
			System.out.println("Future test isDone: " + tc1.isDone());
		}  finally {
			if(e != null) {
				e.shutdown();
				//System.out.println("calling shutDownNow() " + e.shutdownNow());
				//e.execute(() -> System.out.println("Printing zoo inventory"));// Throw exception
				
				System.out.println("is shutdown(): " + e.isShutdown());
				System.out.println("is terminated(): " + e.isTerminated());
			}
		}
		
		if(e != null) {
			e.awaitTermination(2, TimeUnit.SECONDS);
			if(e.isTerminated()) {
				System.out.println("all tasks are done");	
			} else {
				System.out.println("not all tasks are done");	
			}
		}
		

	}
	
	public void method_3() {
		staticExecutor.submit(() -> System.out.println("static executor service"));
		staticExecutor.shutdown();
	}
	
	public void executorServiceUsingCallable() throws InterruptedException, ExecutionException {
		ExecutorService service = null;
		service = Executors.newSingleThreadExecutor();
		try {
			//Future<Integer> tc1 = service.submit(() -> 11+22);
			service.submit(() -> UTILITY_INT = 11+22);
			service.submit(()-> System.out.println("calling executorServiceUsingCallable and the result is : " + UTILITY_INT));
			//WHY use a separate runnable result out in UTILITY_INT IS 0?????
		}finally {
			service.shutdown();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void schedulerTest() {
		ScheduledExecutorService service = null;
		service = Executors.newSingleThreadScheduledExecutor();
		
		Callable<?> call = () -> {System.out.println("this is a callable"); return null;};
		
		Runnable run = () -> System.out.println("this is a runnable");
		//Runnable scheduledRun = () -> System.out.println("this is a schduled runnable");
		
		service.schedule(call, 1, TimeUnit.SECONDS);
		service.schedule(run, 1, TimeUnit.SECONDS);
		
		service.scheduleAtFixedRate(() -> System.out.println("I love you April"), 1, 3,  TimeUnit.SECONDS);
		
		service.scheduleWithFixedDelay(() -> System.out.println("MUAAAA"), 1, 5, TimeUnit.SECONDS);
		
		
		service.scheduleWithFixedDelay(() -> System.out.println("尿妈妈， 尿妈妈， 尿妈妈！！！！"), 1, 10, TimeUnit.SECONDS);
		
	}

}
