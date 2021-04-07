package Chapter6;

public class MyCheckedException extends Exception{
	
	public MyCheckedException() {
		super();
	}
	
	public MyCheckedException(Exception e) {
		super(e);
	}
	
	public MyCheckedException(String message) {
		super(message);
	}

}
