package Chapter7;
import java.util.*; 
import java.util.concurrent.*; 
import java.util.stream.*; 

public class Chapter7Question {

	public static void main(String[] args) throws InterruptedException, ExecutionException{
		// TODO Auto-generated method stub
		 ExecutorService service = Executors.newSingleThreadExecutor();
		 final List<Future<?>> results = new ArrayList<>();
		 //IntStream.range(0, 10) .forEach(i -> results.add( service.submit(() -> performCount(i))));
		 //results.stream().forEach(f -> printResults(f)); 
		 service.shutdown();
		 
		 testFuture();
	}
	
	public static Integer performCount(int exhibitNumber) {
		System.out.println("performCount executed");
		throw new RuntimeException("XXX");
	}
	
	public static void printResults(Future<?> f) { 
	
			try {
				System.out.println(f.get());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
			
				System.out.println(e.getClass() + " Exception!"); 

			}
	
	}

	
	public static void testFuture() {
		ExecutorService service = Executors.newSingleThreadExecutor();
		Future<?> f = service.submit(() -> ThrowException());
		 service.shutdown();
		try {
			f.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getClass() + " testFuture!"); 
		}
	}
	
	public static int ThrowException() throws Exception {
		throw new Exception("ThrowException");
	}

}
