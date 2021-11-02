package Chapter6;


public class DataIntegrity { 
	private int score; 
	public DataIntegrity() { 
		super(); 
		DataIntegrity.this.score = 5; 
	} 
	public static void main(String[] books) { 
		final DataIntegrity johnny5 = new DataIntegrity(); 
		assert(johnny5.score>2) : johnny5.score++; 
		//assert johnny5.score>=5 : System.out.print("No input"); // does not compile, optional part in assertion, the expression must able to return a value. 
		assert(johnny5.score>2) : new Exception("ffff"); 
		System.out.print("Made it!"); 
	} 
}