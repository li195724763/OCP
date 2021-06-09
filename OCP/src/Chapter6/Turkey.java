 package Chapter6;



public class Turkey implements AutoCloseable{
	@Override
	public void close() {
		System.out.println("3 calling close() method from Turkey class");
		//throw new IllegalStateException("unchecked IllegalStateException");
		throw new NullPointerException("unchecked NullPointerException");
	}
}
