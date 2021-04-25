package Chapter7;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*DOES NOT COMPILE
 * Runnable tc2 = () -> 2;//DOES NOT COMPILE
 * 
 * */

/*Throw Exception
 * e.shutdown();
 * e.execute(() -> System.out.println("Printing zoo inventory"));// Throw exception
 * 
 * */	 

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
	
	public class SheepManager{
		private AtomicInteger count = new AtomicInteger(0); // need constructor
		
		//private Chapter7Util.OBJECT lock = new Chapter7Util.OBJECT();
		private void incrementCount() {
			//lock = new ArrayList<String>();
			synchronized (SheepManager.class) {
				System.out.println("count is : " + count.incrementAndGet());
			}
		}
		
	}
	public static void main(String[] args) throws InterruptedException, InterruptedException, TimeoutException , ExecutionException {				
		testMainThreadAndSeparateThread();
		testRunMethodWithoutStart();
		testPollingWithSleep();
		
		testExecutorService();
		testexecutorServiceUsingCallable();
		testExecutorWithFuture();
		schedulerTest();
		System.out.println("available process is : " + Runtime.getRuntime().availableProcessors());
		testSynchronized();
		reproduceConcurrentException();
		reproduceConcurrentExceptionOnList();
		concurrentCollection();
		testBlockingQueues();
		testCopyOnWrite();
		testObtainingSynchronizedCollections();
		testParallelStream();
		testParallelReduction();
		testParallelCollector();
		testCyclicBarrier();
		System.out.println("Fraction number " + testRecursiveNumFraction(5));
		testDeadLock();
		
	}
	
	public static void testMainThreadAndSeparateThread () {
		System.out.println("main thread");
		Runnable tc1 = () -> System.out.println("my first runnable after 10 years");	
		tc1.run();	//no thread created
			
		new Thread(Chapter7Util.OBJECT.new PrintData()).start();
		new ReadInventoryThread().start();
	}
	
	public static void testRunMethodWithoutStart() {
		System.out.println("under  testRunMethodWithoutStart 1");
		new Thread(Chapter7Util.OBJECT.new PrintData()).run();//no thread created
		new Thread(Chapter7Util.OBJECT.new PrintData()).run();//no thread created
		new Thread(Chapter7Util.OBJECT.new PrintData()).run();//no thread created
		System.out.println("under  testRunMethodWithoutStart 2");
	}
	
	public static void testPollingWithSleep() {
		new Thread(() -> {
			for(int i=0;i<100;i++) {
				Chapter7Util.counter++;
			}
			
		}).start();
		
		while(Chapter7Util.counter < 100) {
			System.out.println("counter is less than 100");
			try {
				Thread.sleep(100);// this avoid possible infinit loop or super costly checking.
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//throw InterruptedException
		}
		
		if(Chapter7Util.counter == 100) {
			System.out.println("counter is now 100");
		}
		
		//assert false : "the program does not hang";
		System.out.println("seems the program won't hang");
	}
	
	public void method_1() { // instance method
		PrintData localInner = new PrintData();// created normally
		localInner.run();
		
		ReadInventoryThread localStaticInner = new ReadInventoryThread();
		localStaticInner.run();
	}
	
	public static void method_2() {// static method
		PrintData localInner = Chapter7Util.OBJECT.new PrintData();//created from the outter class Chapter7Util.OBJECT
		localInner.run();
		
		ReadInventoryThread localStaticInner = new ReadInventoryThread();
		localStaticInner.run();
	}
	
	public static void testExecutorWithFuture() throws TimeoutException, InterruptedException, ExecutionException {
		ExecutorService service = null;// not Executor
		
		try {
			service = Executors.newSingleThreadExecutor();
			
			System.out.println("begin");
			service.submit(() -> System.out.println("Printing zoo inventory"));
			Future<?> tc1 = service.submit(() -> {
				for(int i=0;i<3;i++) {
					System.out.println("Printing Record: " + i);
				}
			});
			service.submit(() -> System.out.println("Printing zoo inventory"));
			System.out.println("end");	
			System.out.println("Future test isCancelled: " + tc1.isCancelled());
			System.out.println("Future test get: " + tc1.get(1, TimeUnit.MILLISECONDS));
			System.out.println("Future test isDone: " + tc1.isDone());
		}  finally {
			if(service != null) {
				service.shutdown();
				//System.out.println("calling shutDownNow() " + e.shutdownNow());
				//e.execute(() -> System.out.println("Printing zoo inventory"));// Throw exception
				
				System.out.println("is shutdown(): " + service.isShutdown());
				System.out.println("is terminated(): " + service.isTerminated());
			}
		}
		
		if(service != null) {
			service.awaitTermination(2, TimeUnit.SECONDS);
			if(service.isTerminated()) {
				System.out.println("all tasks are done");	
			} else {
				System.out.println("not all tasks are done");	
			}
		}
	}
	
	public static void testExecutorService () {
		Chapter7Util.staticExecutor.submit(() -> System.out.println("static executor service"));
		Chapter7Util.staticExecutor.shutdown();
	}
	
	public static void testexecutorServiceUsingCallable() throws InterruptedException, ExecutionException {
		ExecutorService service = null;
		service = Executors.newSingleThreadExecutor();
		try {
			//Future<Integer> tc1 = service.submit(() -> 11+22);
			service.submit(() -> Chapter7Util.UTILITY_INT = 11+22);
			service.submit(()-> System.out.println("calling executorServiceUsingCallable and the result is : " + Chapter7Util.UTILITY_INT));
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
		service.shutdown();
	}
	
	public static void testSynchronized() throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(20);
		SheepManager manager = Chapter7Util.OBJECT.new SheepManager();
		for(int i=0;i<10;i++) {
			synchronized (manager) {
				service.submit(() -> manager.incrementCount());
			}
			//Thread.sleep(100);
		}	
			
		service.shutdown();
	}
	
	private static void staticSynchronized() {
		//final Chapter7Util.OBJECT lock = new Chapter7Util.OBJECT();
		synchronized (Chapter7.class){
			System.out.println("UTILITY_INT is : " + ++Chapter7Util.UTILITY_INT);
		} 
		
	}
	
	private static void reproduceConcurrentException() {
		Map<String,String> map = new HashMap<>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.put("key3", "value3");
		
		for(String key : map.keySet()) {
			//map.remove(key); //java.util.ConcurrentModificationException
		}
	}
	
	private static void reproduceConcurrentExceptionOnList() {
		List<Integer> list = new ArrayList<>(Arrays.asList(1,2,3)); 
		synchronized (Chapter7.class) {
			for(int i : list) {
				//list.add(1); //un-synced iterator, even under synchronized block
			}			
		}

	}
	
	private static void concurrentCollection() {
		Map<String, String> tc1 = new ConcurrentHashMap<>();
		tc1.put("key1", "value1");
		tc1.put("key2", "value2");
		tc1.put("key3", "value3");
		System.out.println("Concurrent Keys: " + tc1.keySet());
		System.out.println("Concurrent Values: " + tc1.values());
		
		Queue<Integer> tc2 = new ConcurrentLinkedQueue<>();
		tc2.offer(1);

		System.out.println("Concurrent Linked Queue: calling peek: " + tc2.peek());
		System.out.println("Concurrent Linked Queue: calling poll: " + tc2.poll());
		
		Deque<Integer> tc3 = new ConcurrentLinkedDeque<>();
		tc3.push(2);
		tc3.offer(3);
		System.out.println("Concurrent Linked Dequeue, double-ended, calling peek: " + tc3.peek());
		System.out.println("Concurrent Linked Dequeue, double-ended, calling pop: " + tc3.pop());
		
		
	}
	
	private static void testBlockingQueues() {

		BlockingQueue<Integer> tc1 = new LinkedBlockingQueue<>();
		BlockingDeque<Integer> tc2 = new LinkedBlockingDeque<>();
		try {
			tc1.offer(1, 10, TimeUnit.MILLISECONDS);
			System.out.println("Testing LinkedBlockingQueue with calling peek: " + tc1.peek());
			System.out.println("Testing LinkedBlockingQueue with calling poll: " + tc1.poll(10, TimeUnit.MILLISECONDS));
			
			tc2.offer(1);
			tc2.offerFirst(2, 1, TimeUnit.MICROSECONDS);
			tc2.offerLast(3); //213
			tc2.offer(4);//2134
			tc2.push(5);//52134   Front ---- Back
			System.out.println("BlockingDeque offer method test : " + tc2);
			
			System.out.println("BlockingDeque peek method test : " + tc2.peek()); //5
			System.out.println("BlockingDeque pollFirst method test : " + tc2.pollFirst(1,TimeUnit.MILLISECONDS)); //5
			System.out.println("BlockingDeque now is : " + tc2); //2134
			System.out.println("BlockingDeque pollLast test : " + tc2.pollLast(1, TimeUnit.MICROSECONDS)); //4
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private static void testCopyOnWrite( ) {
		List<Integer> tc1 = new CopyOnWriteArrayList<Integer>(Arrays.asList(1,2,3,4,5));
		for(int i : tc1) {
			tc1.add(i+1);
			System.out.println("value is + " + i + " CopyOnWriteArrayList size after change : " + tc1.size());
		}
		
		System.out.println("CopyOnWriteArrayList test: " + tc1);
	}
	
	private static void testObtainingSynchronizedCollections() {
		List<Integer> tc1 = Collections.synchronizedList(Arrays.asList(1,2,3));
		//synchronized (tc1) {
			for(int i : tc1) {
				System.out.println("testObtainingSynchronizedCollections List : " + i);
				//tc1.remove(0);// throw java.lang.UnsupportedOperationException
			}
		//}	
			
		Map<String, String> tc2 = Collections.synchronizedMap(new HashMap<>());
		tc2.put("key1", "value1");
		tc2.put("key2", "value2");
		tc2.put("key3", "value3");
		synchronized (tc2) {
			for(String i : tc2.keySet()) {
				System.out.println("testObtainingSynchronizedCollections Map: " + tc2.get(i));
				//tc2.remove(i);//throw java.util.ConcurrentModificationException
			}
		}
	}
	
	public static void testParallelStream() {
		Stream<Integer> tc1 = Arrays.asList(1,2,3,4,6).parallelStream();
		//tc1.forEach(System.out::println);
		//tc1.forEachOrdered(System.out::println);
		
		List<Integer> tc2 = new ArrayList<>();// if both thread trigger the resize of ArrayList at the same time, one result could be lost. 
		System.out.print("testParallelStream: ");
		tc1.map(a -> {tc2.add(a); return a;}).forEachOrdered(System.out::print);	
		System.out.println(" " + tc2);
	}
	
	public static void testParallelReduction() {
		System.out.println(Arrays.asList(1,2,3,4,5).stream().findAny());
		System.out.println(Arrays.asList(1,2,3,4,5).parallelStream().findAny()); //result is not predicatable.
		
		Stream<Character> tc1 = Stream.of('w', 'o', 'l', 'f').parallel(); 
		System.out.println(tc1.reduce("" , (c , s1) -> c+s1, (s2,s3) -> s2 + s3)); //follow rule number 1: identity is not affecting the result
		
		Stream<Character> tc2 = Stream.of('w', 'o', 'l', 'f').parallel(); 
		System.out.println(tc2.reduce("X" , (c , s1) -> c+s1, (s2,s3) -> s2 + s3)); //breaking rule number 1 , E.g. : XwXoXlXf
		
		Stream<Integer> tc3 = Stream.of(1,2,3,4,5,6,7,8,9,10).parallel();
		System.out.println(tc3.reduce(0, (num1, num2) -> num1+num2));// follow the second rule: the operator is associate.
		
		Stream<Integer> tc4 = Stream.of(1,2,3,4,5,6).parallel();
		System.out.println(tc4.reduce(0, (num1, num2) -> num1 - num2));// breaking the second rule
	}
	
	public static void testParallelCollector() {
		Stream<String> tc1 = Stream.of("w", "o", "l", "f").parallel();
		System.out.println(tc1.collect(ConcurrentSkipListSet::new, Set::add, Set::addAll));
		
		Stream<String> tc2 = Stream.of("lion", "tiger", "bear").parallel();
		ConcurrentMap<Integer,String> tc3 = tc2.collect(Collectors.toConcurrentMap(s->s.length(), v->v, (s1,s2) -> s1 + ", " + s2));
		System.out.println(tc3);
		
		Stream<String> tc4 = Stream.of("lion", "tiger", "bear").parallel();
		ConcurrentMap<Integer,List<String>> tc5 = tc4.collect(Collectors.groupingByConcurrent(s -> s.length()));
		System.out.println(tc5);
	}
		
	public static long testRecursiveNumFraction(int num) {
		if(num <= 1) return 1;
		else return num * testRecursiveNumFraction(num-1);
	}
	
	/*****************CyclicBarrierProces Testing***************/
	public static void testCyclicBarrier() {
		ExecutorService service = Chapter7Util.getExecutorServicce(4);
		CyclicBarrier c1 = new CyclicBarrier(4);//4 means I need 4 thread in total and then I can release the barrier, hanged if the number of barrier is greater than the totoal number of thread.
		CyclicBarrier c2 = new CyclicBarrier(4, () -> System.out.println("pen cleaned!!!"));
		
		for(int i=0;i<4;i++) {
			service.submit(() ->  testCyclicBarrierProcess(c1, c2));
		}
		service.shutdown();
	}
	
	public static void testCyclicBarrierProcess(CyclicBarrier c1, CyclicBarrier c2) {		
		try {
			testCyclicBarrierRemoveAnimal();
			c1.await();
			testCyclicBarrierCleanPen();
			c2.await();
			testCyclicBarrierAddAnial();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		} 
	}
	
	public static void testCyclicBarrierRemoveAnimal() {
		System.out.println("Removing Animal");
	}
	
	public static void testCyclicBarrierCleanPen() {
		System.out.println("Cleanning Pen");
	}
	
	public static void testCyclicBarrierAddAnial() {
		System.out.println("Add Aniaml");
	}
	
	
	/*****************DeadLock Testing***************/
	
	public static void move() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public static void testDeadLockEatAndDrink(Food food, Water water) {
		synchronized(food) {	
			System.out.println("Got food1");
			move();
			synchronized(water) {
				System.out.println("Got water2");
			}
		}
		
	}
	
	public static void testDeadLockDrinkAndEat(Food food, Water water) {
		synchronized(water) {
			System.out.println("Got Water1");
			move();
			synchronized(food) {
				System.out.println("Got food2");
			}
		}
		
	}
	
	public static void testDeadLock() {
		ExecutorService service = null;
		service = Executors.newFixedThreadPool(2);
		Water water = new Water();
		Food food = new Food();
		
		try {
			service.submit(() -> testDeadLockEatAndDrink(food, water));
			//service.submit(() -> testDeadLockDrinkAndEat(food, water)); //Dead Lock
		}finally {
			service.shutdown();
		}
		
	}

}

class Food{
	
}

class Water{
	
}
