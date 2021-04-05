package Chapter5;

import java.util.ListResourceBundle;

public class Zoo_en extends ListResourceBundle{
	@Override protected Object[][] getContents() {
		//return  new Object[][]{{"hello", new StringBuilder("hello from java class")}};!!!!!EXCEPTION with getProperty()
		
		return  new Object[][]{{"hello", "hello from java class"}};
		
		
	}
}
