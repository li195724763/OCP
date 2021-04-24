package Chapter7;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Chapter7Util {
	protected static int counter = 0;
	protected static final Chapter7 OBJECT = new Chapter7();
	protected static ExecutorService staticExecutor = Executors.newSingleThreadExecutor();
	protected static int UTILITY_INT = 0;
	protected static int _count = 0;
	
	protected static ExecutorService getExecutorServicce(int numberOfThread) {
		return Executors.newScheduledThreadPool(numberOfThread);
	}
	
	protected static CyclicBarrier getCyclicBarrier(int CyclicBarrierLimit) {
		return new CyclicBarrier(CyclicBarrierLimit); 
	}
}
