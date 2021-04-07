package Chapter6;

public class MockQuestion_12_13_14 {
	static class SneezeException extends Exception{}
	static class SniffleException extends SneezeException {}

	public static void main(String[] args) throws SneezeException {
		// TODO Auto-generated method stub
		try {
			throw new SneezeException();
		}catch(RuntimeException | SneezeException e) {
			//e = new RuntimeException(); // DOES NOT COMPILE, you can't reassign in multiple catch 
			// if there is you multiple catch, "e = new RuntimeException" still does not compile, since you can't assign a SneezeException type Exception is not a RuntimeException.
			throw e;
		}
	}

}
