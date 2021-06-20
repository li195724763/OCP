package Chapter7;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoin extends RecursiveAction{
	
	/******member inner class*/
	public class ForkJoinRecursiveTask extends RecursiveTask<Double>{
		double[] weights;
		int start;
		int end;
		
		public ForkJoinRecursiveTask (double[] weights, int start, int end) {
			this.weights = weights;
			this.end = end;
			this.start = start;
		}
		@Override
		protected Double compute() {
			double sum = 0;
			if(end-start <=3 ) {
				for(int i=start;i<end;i++) {
					sum = sum + new Random().nextInt(100);
				}
				System.out.println("*****idenpendent sum is " + sum);
			} else {
				int middle = start + (end - start)/2;
				RecursiveTask<Double> otherTask = new ForkJoinRecursiveTask(weights, start, middle);
				otherTask.fork();//start a new thread that execute "otherTask"
				System.out.println("start is: " + start + " middle is :" + middle + " end is : " + end);
				return new ForkJoinRecursiveTask(weights, middle, end).compute() + otherTask.join();//start a calculation test in current thread and wait for otherTask to complete(otherTask.join())
			}
			
			return sum;
		}
	}
	
	
	
	private Double[] weight;
	private int start = 0;
	private int end = 0;
	
	public ForkJoin(Double[] weight, int start, int end) {
		this.weight = weight;
		this.start = start;
		this.end = end;
	}

	@Override
	protected void compute(){
		if(end - start <= 3) {
			for(int i=start; i<end; i++) {
				this.weight[i] = (double) new Random().nextInt(100);
				System.out.print("weight is: " + this.weight[i] );
				System.out.print(" " );
			}
			System.out.println();
		} else {
			int middle = start + (end-start)/2;
			System.out.println("start is: " + start + " middle is :" + middle + " end is : " + end);
			invokeAll(new ForkJoin(this.weight, start, middle), 
					  new ForkJoin(this.weight, middle, end)
					 );// invokeAll can take different numbers of ForkJoin instant.
		}
	}
	
	public static void main(String[] args) {
		Double[] weights = new Double[10];
		
		//*****************RecursiveAction*********************
		ForkJoinTask<?> recursiveAction = new ForkJoin(weights, 0, weights.length);
		ForkJoinPool recursiveActionPool_1 = new ForkJoinPool();
		recursiveActionPool_1.invoke(recursiveAction);
		
		//*****************RecursiveTask*********************
		long start = System.currentTimeMillis();
		double[] weights2 = new double[10000];
		ForkJoin tc4 = new ForkJoin(weights, 0, weights.length);
		ForkJoinRecursiveTask tc5 = tc4.new ForkJoinRecursiveTask(weights2, 0, 10);//DOES NOT COMPILE if ForkJoinTask is a super class of ForkJoin,
		                //since ForkJoinRecursiveTask is an inner class of ForkJoin, and has no nested relationship with ForkJoinTask(provided by java.util.concurrent)
	    ForkJoinRecursiveTask task_2 = tc4.new ForkJoinRecursiveTask(weights2, 0, weights2.length);
		  
		ForkJoinPool pool_2 = new ForkJoinPool();
		  
		Double sum = pool_2.invoke(task_2);
		
		  System.out.println("******the totoal sum is: " + sum); long end =
		  (System.currentTimeMillis()-start); System.out.println("\n" + end +
		  " milliseconds" );
		 

	}

}
