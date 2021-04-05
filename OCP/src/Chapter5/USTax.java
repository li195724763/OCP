package Chapter5;

public class USTax {
	public String Country = "US";
	
	public String Charge = "1 billion";
	
	@Override public String toString() {
		return Country + " charges you " + Charge;
	}

}
