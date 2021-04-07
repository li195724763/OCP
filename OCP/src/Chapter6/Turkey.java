 package Chapter6;



public class Turkey implements AutoCloseable{
	@Override
	public void close() {
		System.out.println("calling close() method from Turkey class");
		//throw new IllegalStateException("unchecked IllegalStateException");
		throw new NullPointerException("unchecked NullPointerException");
	}
}
