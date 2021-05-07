package Chapter7;
import java.util.*;

public class WhaleCalculator {
	public int processRecord(int input) {
		try {
			Thread.sleep(10);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println("executed");
		return input + 1;
	}
	
	public void processAllData(List<Integer> data) {
		//data.parallelStream().map(a -> processRecord(a)).forEach(System.out::println);
		data.parallelStream().map(a -> processRecord(a)).count();//This completed immediatedly 
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WhaleCalculator calculator = new WhaleCalculator();
		
		List<Integer> data = new ArrayList<>();
		for(int i=0;i<4000;i++) data.add(i);
		
		long start = System.currentTimeMillis();
		
		calculator.processAllData(data);
		
		double time = (System.currentTimeMillis()-start)/1000.0;
		
		System.out.println("\nTask completed in: " + time + "seconds");
	}

}
