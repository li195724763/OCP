package Chapter6;

public class MyUncheckedException extends RuntimeException{
	public MyUncheckedException() {
		super();
	}
	
	public MyUncheckedException(Exception e) {
		super(e);
	}
	
	public MyUncheckedException(String message) {
		super(message);
	}
}
