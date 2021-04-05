package Chapter5;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

public class Tax_en_US extends ListResourceBundle{
	
	@Override
	protected Object[][] getContents() {
		// TODO Auto-generated method stub
		return new Object[][] {{"Tax", new USTax()}};
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ResourceBundle rb = ResourceBundle.getBundle("MyPackage.Tax", Locale.US);
		
		System.out.println(rb.getObject("Tax").toString());
	}



}
